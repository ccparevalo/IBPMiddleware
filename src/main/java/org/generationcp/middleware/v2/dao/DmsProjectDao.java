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
package org.generationcp.middleware.v2.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.generationcp.middleware.v2.pojos.AbstractNode;
import org.generationcp.middleware.dao.GenericDAO;
import org.generationcp.middleware.exceptions.MiddlewareQueryException;
import org.generationcp.middleware.v2.pojos.CVTermId;
import org.generationcp.middleware.v2.pojos.DatasetNode;
import org.generationcp.middleware.v2.pojos.DmsProject;
import org.generationcp.middleware.v2.pojos.FolderNode;
import org.generationcp.middleware.v2.pojos.StudyNode;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 * 
 * @author Darla Ani, Joyce Avestro
 *
 */

public class DmsProjectDao extends GenericDAO<DmsProject, Integer> {

	public static final String GET_ROOT_FOLDERS = 
			"SELECT DISTINCT p.projectId, p.name "
			+ "FROM DmsProject p "
			+ "		JOIN p.relatedTos r "
			+ "WHERE r.typeId = " + CVTermId.HAS_PARENT_FOLDER.getId() + " "
			+ "		 AND r.objectProject.projectId = " + DmsProject.SYSTEM_FOLDER_ID + " " 
			+ "ORDER BY name "
			;
	
	public static final String GET_CHILDREN_OF_FOLDER =		
			"SELECT  DISTINCT subject.project_id, subject.name, IFNULL(is_study.type_id, 0) AS is_study "
			+ "FROM    project subject "
			+ "        INNER JOIN project_relationship pr ON subject.project_id = pr.subject_project_id "
			+ "        LEFT JOIN project_relationship is_study ON subject.project_id = is_study.subject_project_id "
			+ "        					AND is_study.type_id = " + CVTermId.IS_STUDY.getId() + " "
			+ "WHERE   pr.type_id = " + CVTermId.HAS_PARENT_FOLDER.getId() + " "
			+ "        and pr.object_project_id = :folderId "
			+ "ORDER BY name "
			;


	public static final String GET_DATASET_NODE_BY_STUDY = 
			"SELECT DISTINCT p.projectId, p.name, pr.objectProject.projectId "
			+ "FROM DmsProject p JOIN p.relatedTos pr "
			+ "WHERE pr.typeId = " + CVTermId.BELONGS_TO_STUDY.getId() + " "
			+ "      AND pr.objectProject.projectId = :studyId "
			+ "ORDER BY name "
			;
		
	@SuppressWarnings("unchecked")
	public List<FolderNode> getRootFolders() throws MiddlewareQueryException{
		
		List<FolderNode> folderList = new ArrayList<FolderNode>();
		
		try {
			Query query = getSession().createQuery(GET_ROOT_FOLDERS);
			List<Object[]> list =  query.list();
			for (Object[] row : list){
				Integer id = (Integer)row[0]; //project.id
				String name = (String) row [1]; //project.name
				folderList.add(new FolderNode(id, name));
			}
			
		} catch (HibernateException e) {
			logAndThrowException("Error with getRootFolders query from Project: " + e.getMessage(), e);
		}
		
		return folderList;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<AbstractNode> getChildrenOfFolder(Integer folderId) throws MiddlewareQueryException{
		
		List<AbstractNode> childrenNodes = new ArrayList<AbstractNode>();
		
		try {
			Query query = getSession().createSQLQuery(GET_CHILDREN_OF_FOLDER);
			query.setParameter("folderId", folderId);
			List<Object[]> list =  query.list();
			
			for (Object[] row : list){
				Integer id = (Integer) row[0]; //project.id
				String name = (String) row [1]; //project.name
				Integer isStudy = ((BigInteger) row[2]).intValue(); //non-zero if a study, else a folder
				
				if (isStudy > 0){
					childrenNodes.add(new StudyNode(id, name));
				} else {
					childrenNodes.add(new FolderNode(id, name));
				}
			}
			
		} catch (HibernateException e) {
			logAndThrowException("Error with getChildrenOfFolder query from Project: " + e.getMessage(), e);
		}
		
		return childrenNodes;
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<DatasetNode> getDatasetNodesByStudyId(Integer studyId) throws MiddlewareQueryException{
		
		List<DatasetNode> datasetNodes = new ArrayList<DatasetNode>();
		
		try {
			Query query = getSession().createQuery(GET_DATASET_NODE_BY_STUDY);
			query.setParameter("studyId", studyId);
			List<Object[]> list =  query.list();
			
			for (Object[] row : list){
				Integer id = (Integer) row[0]; //project.id
				String name = (String) row [1]; //project.name
				datasetNodes.add(new DatasetNode(id, name));
			}
			
		} catch (HibernateException e) {
			logAndThrowException("Error with getDatasetNodesByStudyId query from Project: " + e.getMessage(), e);
		}
		
		return datasetNodes;
		
	}

}
