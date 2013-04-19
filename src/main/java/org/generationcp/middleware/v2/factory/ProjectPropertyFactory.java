package org.generationcp.middleware.v2.factory;

import java.util.ArrayList;
import java.util.List;

import org.generationcp.middleware.pojos.Study;
import org.generationcp.middleware.v2.pojos.CVTermId;
import org.generationcp.middleware.v2.pojos.DmsProject;
import org.generationcp.middleware.v2.pojos.ProjectProperty;

public class ProjectPropertyFactory {
	
	private static final ProjectPropertyFactory instance = new ProjectPropertyFactory();
	
	public static ProjectPropertyFactory getInstance() {
		return instance;
	}
	
	public List<ProjectProperty> createProjectProperties(Study study) {
		ArrayList<ProjectProperty> properties = null;
		if (study != null) {
			properties = mapStudyToProperties(study);
		}
		return properties;
	}
	
	private ArrayList<ProjectProperty>  mapStudyToProperties(Study study) {
		ArrayList<ProjectProperty> properties = new ArrayList<ProjectProperty>();
		
		DmsProject project = ProjectFactory.getInstance().createProject(study);
		
		properties.add(new ProjectProperty(0, project, CVTermId.PM_KEY.getId(), study.getProjectKey().toString(), 0));
		properties.add(new ProjectProperty(0, project, CVTermId.STUDY_OBJECTIVE.getId(), study.getObjective(), 1));
		properties.add(new ProjectProperty(0, project, CVTermId.PI_ID.getId(), study.getPrimaryInvestigator().toString(), 2));
		properties.add(new ProjectProperty(0, project, CVTermId.STUDY_TYPE.getId(), study.getType(), 3));
		properties.add(new ProjectProperty(0, project, CVTermId.START_DATE.getId(), study.getStartDate().toString(), 4));
		properties.add(new ProjectProperty(0, project, CVTermId.END_DATE.getId(), study.getEndDate().toString(), 5));
		properties.add(new ProjectProperty(0, project, CVTermId.STUDY_UID.getId(), study.getUser().toString(), 6));
		properties.add(new ProjectProperty(0, project, CVTermId.STUDY_IP.getId(), study.getStatus().toString(), 7));
		properties.add(new ProjectProperty(0, project, CVTermId.RELEASE_DATE.getId(), study.getCreationDate().toString(), 8));
		
		return properties;
		
	}

	
}