/*******************************************************************************
 * Copyright (c) 2012, All Rights Reserved.
 * 
 * Generation Challenge Programme (GCP)
 * 
 * 
 * This software is licensed for use under the terms of the GNU General Public
 * License (http://bit.ly/8Ztv8M) and the provisions of Part F of the Generation
 * Challenge Programme Amended Consortium Agreement (http://bit.ly/KQX1nL)
 * 
 *******************************************************************************/
package org.generationcp.middleware.dao.dms;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.generationcp.middleware.dao.GenericDAO;
import org.generationcp.middleware.domain.dms.LocationDto;
import org.generationcp.middleware.domain.dms.StudyReference;
import org.generationcp.middleware.domain.dms.TrialEnvironment;
import org.generationcp.middleware.domain.dms.TrialEnvironmentProperty;
import org.generationcp.middleware.domain.dms.TrialEnvironments;
import org.generationcp.middleware.domain.oms.TermId;
import org.generationcp.middleware.exceptions.MiddlewareQueryException;
import org.generationcp.middleware.pojos.dms.Geolocation;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 * DAO class for {@link Geolocation}.
 * 
 */
public class GeolocationDao extends GenericDAO<Geolocation, Integer> {

	public Geolocation getParentGeolocation(int projectId) throws MiddlewareQueryException {
		try {
			String sql = "SELECT DISTINCT g.*"
					+ " FROM nd_geolocation g"
					+ " INNER JOIN nd_experiment se ON se.nd_geolocation_id = g.nd_geolocation_id"
					+ " INNER JOIN nd_experiment_project sep ON sep.nd_experiment_id = se.nd_experiment_id"
					+ " INNER JOIN project_relationship pr ON pr.type_id = " + TermId.BELONGS_TO_STUDY.getId()
					+ " AND pr.object_project_id = sep.project_id AND pr.subject_project_id = :projectId";
			Query query = getSession().createSQLQuery(sql)
								.addEntity(getPersistentClass())
								.setParameter("projectId", projectId);
			return (Geolocation) query.uniqueResult();
						
		} catch(HibernateException e) {
			logAndThrowException("Error at getParentGeolocation=" + projectId + " at GeolocationDao: " + e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Set<Geolocation> findInDataSet(int datasetId) throws MiddlewareQueryException {
		Set<Geolocation> locations = new LinkedHashSet<Geolocation>();
		try {
			
			String sql = "SELECT DISTINCT e.nd_geolocation_id"
					+ " FROM nd_experiment e"
					+ " INNER JOIN nd_experiment_project ep ON ep.nd_experiment_id = e.nd_experiment_id"
					+ " WHERE ep.project_id = :projectId ORDER BY e.nd_geolocation_id";
			Query query = getSession().createSQLQuery(sql)
								.setParameter("projectId", datasetId);
			List<Integer> ids = query.list();
			for (Integer id : ids) {
				locations.add(getById(id));
			}
						
		} catch(HibernateException e) {
			logAndThrowException("Error at findInDataSet=" + datasetId + " at GeolocationDao: " + e.getMessage(), e);
		}
		return locations;
	}

	@SuppressWarnings("unchecked")
	public Geolocation findByDescription(String description) throws MiddlewareQueryException {
		try {
			
			String sql = "SELECT DISTINCT loc.nd_geolocation_id"
					+ " FROM nd_geolocation loc"
					+ " WHERE loc.description = :description";
			Query query = getSession().createSQLQuery(sql)
								.setParameter("description", description);
			List<Integer> ids = query.list();
			if (ids.size() >= 1) {
				return getById(ids.get(0));
			}
						
		} catch(HibernateException e) {
			logAndThrowException("Error at findByDescription=" + description + " at GeolocationDao: " + e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Set<Integer> getLocationIds(Integer projectId) throws MiddlewareQueryException {
		Set<Integer> locationIds = new HashSet<Integer>();
		try {
			String sql = "SELECT DISTINCT e.nd_geolocation_id"
					+ " FROM nd_experiment e, nd_experiment_project ep "
					+ " WHERE e.nd_experiment_id = ep.nd_experiment_id "
					+ "   and ep.project_id = " + projectId;
			Query query = getSession().createSQLQuery(sql);
			locationIds.addAll((List<Integer>) query.list());
						
		} catch(HibernateException e) {
			logAndThrowException("Error at getLocationIds=" + projectId + " at GeolocationDao: " + e.getMessage(), e);
		}
		return locationIds;
	}
	
	@SuppressWarnings("unchecked")
	public TrialEnvironments getAllTrialEnvironments() throws MiddlewareQueryException {
		TrialEnvironments environments = new TrialEnvironments();
		try {
			String sql = "SELECT DISTINCT gp.nd_geolocation_id, l.lname, prov.lname, c.isoabbr, p.project_id, p.name"
						+ " FROM project p"
						+ " INNER JOIN project_relationship pr ON pr.object_project_id = p.project_id AND pr.type_id = " + TermId.BELONGS_TO_STUDY.getId()
						+ " INNER JOIN nd_experiment_project ep ON (ep.project_id = p.project_id OR ep.project_id = pr.subject_project_id)"
						+ " INNER JOIN nd_experiment e ON e.nd_experiment_id = ep.nd_experiment_id"
						+ " INNER JOIN nd_geolocationprop gp ON gp.nd_geolocation_id = e.nd_geolocation_id AND gp.type_id = " + TermId.LOCATION_ID.getId()
						+ " INNER JOIN location l ON l.locid = gp.value"
						+ " INNER JOIN location prov ON prov.locid = l.snl1id"
						+ " INNER JOIN cntry c ON c.cntryid = l.cntryid";
			Query query = getSession().createSQLQuery(sql);
			List<Object[]> list = query.list();
			for (Object[] row : list) {
				environments.add(new TrialEnvironment((Integer) row[0], 
										new LocationDto((String) row[1], (String) row[2], (String) row[3]), 
										new StudyReference((Integer) row[4], (String) row[5])));
			}
			
		} catch(HibernateException e) {
			logAndThrowException("Error at getAllTrialEnvironments at GeolocationDao: " + e.getMessage(), e);
		}
		return environments;
	}
	
	@SuppressWarnings("unchecked")
	public List<TrialEnvironmentProperty> getPropertiesForTrialEnvironments(List<Integer> environmentIds) throws MiddlewareQueryException {
		List<TrialEnvironmentProperty> properties = new ArrayList<TrialEnvironmentProperty>();
		try {
			String sql = "SELECT cvt.name, cvt.definition, COUNT(DISTINCT gp.nd_geolocation_id)"
							+ " FROM cvterm cvt"
							+ " INNER JOIN nd_geolocationprop gp ON gp.type_id = cvt.cvterm_id"
							+ " WHERE gp.nd_geolocation_id IN (:environmentIds)"
							+ " GROUP BY gp.type_id, cvt.name, cvt.definition";
			Query query = getSession().createSQLQuery(sql)
							.setParameterList("environmentIds", environmentIds);
			List<Object[]> result = query.list();
			for (Object[] row : result) {
				properties.add(new TrialEnvironmentProperty((String) row[0], (String) row[1], ((BigInteger) row[2]).intValue()));
			}
			
		} catch(HibernateException e) {
			logAndThrowException("Error at getPropertiesForTrialEnvironments=" + environmentIds + " at GeolocationDao: " + e.getMessage(), e);
		}
		return properties;
	}
}
