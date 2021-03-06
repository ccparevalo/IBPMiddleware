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
package org.generationcp.middleware.dao.oms;

import java.util.ArrayList;
import java.util.List;

import org.generationcp.middleware.dao.GenericDAO;
import org.generationcp.middleware.exceptions.MiddlewareQueryException;
import org.generationcp.middleware.pojos.oms.CVTermRelationship;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * DAO class for {@link CVTermRelationship}.
 * 
 */
public class CVTermRelationshipDao extends GenericDAO<CVTermRelationship, Long> {

	@SuppressWarnings("unchecked")
	public List<Integer> getSubjectIdsByTypeAndObject(Integer typeId, Integer objectId) throws MiddlewareQueryException {
		try {
			Criteria criteria = getSession().createCriteria(getPersistentClass());
			criteria.add(Restrictions.eq("typeId", typeId));
			criteria.add(Restrictions.eq("objectId", objectId));
			criteria.setProjection(Projections.property("subjectId"));
			
			return criteria.list();
			
		} catch (HibernateException e) {
			logAndThrowException("Error with getSubjectIdsByTypeAndObject=" + typeId + ", " + objectId 
					+ ") query from CVTermRelationship: " + e.getMessage(), e);
		}
		return new ArrayList<Integer>();
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getObjectIdByTypeAndSubject(Integer typeId, Integer subjectId) throws MiddlewareQueryException {
		try {
			Criteria criteria = getSession().createCriteria(getPersistentClass());
			criteria.add(Restrictions.eq("typeId", typeId));
			criteria.add(Restrictions.eq("subjectId", subjectId));
			criteria.setProjection(Projections.property("objectId"));
			
			return criteria.list();
			
		} catch (HibernateException e) {
			logAndThrowException("Error with getSubjectIdsByTypeAndObject=" + typeId + ", " + subjectId 
					+ ") query from CVTermRelationship: " + e.getMessage(), e);
		}
		return new ArrayList<Integer>();
	}

	@SuppressWarnings("unchecked")
	public List<CVTermRelationship> getBySubject(int subjectId) throws MiddlewareQueryException {
		try {
			Criteria criteria = getSession().createCriteria(getPersistentClass());
			criteria.add(Restrictions.eq("subjectId", subjectId));

			return criteria.list();

		} catch(HibernateException e) {
			logAndThrowException("Error with getBySubject=" + subjectId + ") query from CVTermRelationship: " 
					+ e.getMessage(), e);
			return null;
		}
	}
}
