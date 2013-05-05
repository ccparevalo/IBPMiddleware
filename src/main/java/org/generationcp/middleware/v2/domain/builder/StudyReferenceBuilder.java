package org.generationcp.middleware.v2.domain.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.generationcp.middleware.hibernate.HibernateSessionProvider;
import org.generationcp.middleware.v2.domain.StudyReference;
import org.generationcp.middleware.v2.pojos.DmsProject;

public class StudyReferenceBuilder extends Builder {

	public StudyReferenceBuilder(
			HibernateSessionProvider sessionProviderForLocal,
			HibernateSessionProvider sessionProviderForCentral) {
		super(sessionProviderForLocal, sessionProviderForCentral);
	}

	public List<StudyReference> build(Collection<DmsProject> projects) {
		List<StudyReference> studyReferences = new ArrayList<StudyReference>();
		if (projects != null && projects.size() > 0) {
			for (DmsProject project : projects) {
				studyReferences.add(new StudyReference(project.getProjectId(), project.getName(), project.getDescription()));
			}
		}
		return studyReferences;
	}
}