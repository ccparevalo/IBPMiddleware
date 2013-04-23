package org.generationcp.middleware.v2.domain.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.generationcp.commons.util.StringUtil;
import org.generationcp.middleware.exceptions.MiddlewareQueryException;
import org.generationcp.middleware.hibernate.HibernateSessionProvider;
import org.generationcp.middleware.manager.Database;
import org.generationcp.middleware.manager.Season;
import org.generationcp.middleware.pojos.Country;
import org.generationcp.middleware.v2.domain.StudyQueryFilter;
import org.generationcp.middleware.v2.pojos.CVTerm;
import org.generationcp.middleware.v2.pojos.CVTermId;
import org.generationcp.middleware.v2.pojos.StudyNode;

public class StudyNodeBuilder extends Builder {

	public StudyNodeBuilder(
			HibernateSessionProvider sessionProviderForLocal,
			HibernateSessionProvider sessionProviderForCentral) {
		super(sessionProviderForLocal, sessionProviderForCentral);
	}

	
	public List<StudyNode> build(StudyQueryFilter filter) throws MiddlewareQueryException {
		Set<StudyNode> studies = new HashSet<StudyNode>();
		
		studies.addAll(buildFromStartDate(filter.getStartDate()));
		studies.addAll(buildFromStudyName(filter.getName()));
		studies.addAll(buildFromCountry(filter.getCountry()));
		studies.addAll(buildFromSeason(filter.getSeason()));
		
		int start = filter.getStart();
		int end = filter.getStart() + filter.getNumOfRows();
		if (end > studies.size()) {
			end = studies.size();
		}
		if (start > studies.size()) {
			return new ArrayList<StudyNode>();
		}
		
		return new ArrayList<StudyNode>(studies).subList(start, end);
	}
	
	private List<StudyNode> buildFromStartDate(Integer startDate) throws MiddlewareQueryException {
		List<StudyNode> studies = new ArrayList<StudyNode>();
		if (startDate != null) {
			setWorkingDatabase(Database.CENTRAL);
			studies.addAll(getDmsProjectDao().getStudiesByStartDate(startDate));
			setWorkingDatabase(Database.LOCAL);
			studies.addAll(getDmsProjectDao().getStudiesByStartDate(startDate));
		}
		return studies;
	}
	
	private List<StudyNode> buildFromStudyName(String name) throws MiddlewareQueryException {
		List<StudyNode> studies = new ArrayList<StudyNode>();
		if (!StringUtil.isEmpty(name)) {
			setWorkingDatabase(Database.CENTRAL);
			studies.addAll(getDmsProjectDao().getStudiesByName(name));
			setWorkingDatabase(Database.LOCAL);
			studies.addAll(getDmsProjectDao().getStudiesByName(name));
		}
		return studies;
	}
	
	private List<StudyNode> buildFromCountry(String countryName) throws MiddlewareQueryException {
		List<StudyNode> studies = new ArrayList<StudyNode>();
		if (!StringUtil.isEmpty(countryName)) {
			setWorkingDatabase(Database.CENTRAL);
			List<Country> countries = getCountryDao().getByIsoFull(countryName);
			setWorkingDatabase(Database.LOCAL);
			countries.addAll(getCountryDao().getByIsoFull(countryName));
			
			if (countries != null && countries.size() > 0) {
				List<Integer> countryIds = new ArrayList<Integer>(); 
				for (Country country : countries) {
					countryIds.add(country.getCntryid());
				}
				
				setWorkingDatabase(Database.CENTRAL);
				List<Integer> userIds = getUserDao().getUserIdsByCountryIds(countryIds);
				setWorkingDatabase(Database.LOCAL);
				userIds.addAll(getUserDao().getUserIdsByCountryIds(countryIds));
				
				setWorkingDatabase(Database.CENTRAL);
				studies = getDmsProjectDao().getStudiesByUserIds(userIds);
				setWorkingDatabase(Database.LOCAL);
				studies.addAll(getDmsProjectDao().getStudiesByUserIds(userIds));
			}
		}
		return studies;
	}
	
	private List<StudyNode> buildFromSeason(Season season) throws MiddlewareQueryException {
		if (season != null && season != Season.GENERAL) {
			List<Integer> factorIds = getSeasonalFactors();
			
			Set<Integer> experimentIds = new HashSet<Integer>();
			if (factorIds != null && factorIds.size() > 0) {
				//for each seasonal factor, get the value that matches the season parameter from its list of possible values
				for (Integer factorId : factorIds) {
					CVTerm value = getDiscreteValueTerm(factorId.toString(), season.getDefinition());
					if (value != null) {
						Integer storedInId = getStoredInId(factorId);
						
						if (CVTermId.TRIAL_ENVIRONMENT_INFO.getId().equals(storedInId)) {
							experimentIds.addAll(findExperimentsByGeolocationFactorValue(factorId, value.getCvTermId().toString()));
							
						} else if (CVTermId.TRIAL_DESIGN_INFO.getId().equals(storedInId)) {
							experimentIds.addAll(findExperimentsByExperimentFactorValue(factorId, value.getCvTermId().toString()));
							
						} else if (CVTermId.GERMPLASM_ENTRY.getId().equals(storedInId)) {
							experimentIds.addAll(findExperimentsByStockFactorValue(factorId, value.getCvTermId().toString()));
						}
					}
				}
				
				List<Integer> projectIds = getProjectIdsByExperiment(experimentIds);
				
				return getStudiesByProjectIds(projectIds);
			}
		}
		return new ArrayList<StudyNode>();
	}
	
	private List<Integer> getSeasonalFactors() throws MiddlewareQueryException {
		setWorkingDatabase(Database.CENTRAL);
		List<Integer> factors = getCvTermRelationshipDao().getSubjectIdsByTypeAndObject(CVTermId.HAS_PROPERTY.getId(), CVTermId.SEASON.getId());
		setWorkingDatabase(Database.LOCAL);
		factors.addAll(getCvTermRelationshipDao().getSubjectIdsByTypeAndObject(CVTermId.HAS_PROPERTY.getId(), CVTermId.SEASON.getId()));
		
		return factors;
	}
	
	private CVTerm getDiscreteValueTerm(String name, String definition) throws MiddlewareQueryException {
		CVTerm term = null;
		Integer cvId = null;
		if (setWorkingDatabase(Database.CENTRAL)) {
			cvId = getCvDao().getIdByName(name);
		}
		if (cvId == null && setWorkingDatabase(Database.LOCAL)) {
			cvId = getCvDao().getIdByName(name);
		}
		
		if (setWorkingDatabase(Database.CENTRAL)) {
			term = getCvTermDao().getByCvIdAndDefinition(cvId, definition);
		}
		if (term == null && setWorkingDatabase(Database.CENTRAL)) {
			term = getCvTermDao().getByCvIdAndDefinition(cvId, definition);
		}
		return term;
	}
	
	private Integer getStoredInId(Integer factorId) throws MiddlewareQueryException {
		setWorkingDatabase(Database.CENTRAL);
		List<Integer> termIds = getCvTermRelationshipDao().getObjectIdByTypeAndSubject(CVTermId.STORED_IN.getId(), factorId);
		if (termIds == null || termIds.size() == 0) {
			setWorkingDatabase(Database.LOCAL);
			termIds = getCvTermRelationshipDao().getObjectIdByTypeAndSubject(CVTermId.STORED_IN.getId(), factorId);
		}

		return (termIds != null && termIds.size() > 0 ? termIds.get(0) : null);
	}
	
	private List<Integer> findExperimentsByGeolocationFactorValue(Integer factorId, String value) throws MiddlewareQueryException {
		Set<Integer> geolocationIds = new HashSet<Integer>();
		setWorkingDatabase(Database.CENTRAL);
		geolocationIds.addAll(getGeolocationPropertyDao().getGeolocationIdsByPropertyTypeAndValue(factorId, value));
		setWorkingDatabase(Database.LOCAL);
		geolocationIds.addAll(getGeolocationPropertyDao().getGeolocationIdsByPropertyTypeAndValue(factorId, value));
		
		Set<Integer> experimentIds = new HashSet<Integer>();
		setWorkingDatabase(Database.CENTRAL);
		experimentIds.addAll(getExperimentDao().getExperimentIdsByGeolocationIds(geolocationIds));
		setWorkingDatabase(Database.LOCAL);
		experimentIds.addAll(getExperimentDao().getExperimentIdsByGeolocationIds(geolocationIds));
		
		return new ArrayList<Integer>(experimentIds);
	}
	
	private List<Integer> findExperimentsByStockFactorValue(Integer factorId, String value) throws MiddlewareQueryException {
		Set<Integer> stockIds = new HashSet<Integer>();
		setWorkingDatabase(Database.CENTRAL);
		stockIds.addAll(getStockPropertyDao().getStockIdsByPropertyTypeAndValue(factorId, value));
		setWorkingDatabase(Database.LOCAL);
		stockIds.addAll(getStockPropertyDao().getStockIdsByPropertyTypeAndValue(factorId, value));
		
		Set<Integer> experimentIds = new HashSet<Integer>();
		setWorkingDatabase(Database.CENTRAL);
		experimentIds.addAll(getExperimentStockDao().getExperimentIdsByStockIds(stockIds));
		setWorkingDatabase(Database.LOCAL);
		experimentIds.addAll(getExperimentStockDao().getExperimentIdsByStockIds(stockIds));
		
		return new ArrayList<Integer>(experimentIds);
	}
	
	private List<Integer> findExperimentsByExperimentFactorValue(Integer factorId, String value) throws MiddlewareQueryException {
		Set<Integer> experimentIds = new HashSet<Integer>();
		setWorkingDatabase(Database.CENTRAL);
		experimentIds.addAll(getExperimentPropertyDao().getExperimentIdsByPropertyTypeAndValue(factorId, value));
		setWorkingDatabase(Database.LOCAL);
		experimentIds.addAll(getExperimentPropertyDao().getExperimentIdsByPropertyTypeAndValue(factorId, value));
		
		return new ArrayList<Integer>(experimentIds);
	}
	
	private List<Integer> getProjectIdsByExperiment(Collection<Integer> experimentIds) throws MiddlewareQueryException {
		Set<Integer> projectIds = new HashSet<Integer>();
		setWorkingDatabase(Database.CENTRAL);
		projectIds.addAll(getExperimentProjectDao().getProjectIdsByExperimentIds(experimentIds));
		setWorkingDatabase(Database.LOCAL);
		projectIds.addAll(getExperimentProjectDao().getProjectIdsByExperimentIds(experimentIds));

		return new ArrayList<Integer>(projectIds);
	}
	
	private List<StudyNode> getStudiesByProjectIds(Collection<Integer> ids) throws MiddlewareQueryException {
		List<StudyNode> studyNodes = new ArrayList<StudyNode>();
		if (ids != null && ids.size() > 0) {
			List<Integer> positiveIds = new ArrayList<Integer>();
			List<Integer> negativeIds = new ArrayList<Integer>();
			
			for (Integer id : ids) {
				if (id > 0) {
					positiveIds.add(id);
				} else {
					negativeIds.add(id);
				}
			}
			
			if (positiveIds.size() > 0) {
				if (setWorkingDatabase(Database.CENTRAL)) {
					studyNodes.addAll(getDmsProjectDao().getStudiesByIds(positiveIds));
				}
			}
			if (negativeIds.size() > 0) {
				if (setWorkingDatabase(Database.LOCAL)) {
					studyNodes.addAll(getDmsProjectDao().getStudiesByIds(negativeIds));
				}
			}
		}
		return studyNodes;
	}
	
}
