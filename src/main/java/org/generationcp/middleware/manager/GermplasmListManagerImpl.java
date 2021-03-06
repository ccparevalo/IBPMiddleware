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
package org.generationcp.middleware.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.generationcp.middleware.dao.GermplasmListDataDAO;
import org.generationcp.middleware.exceptions.MiddlewareQueryException;
import org.generationcp.middleware.hibernate.HibernateSessionProvider;
import org.generationcp.middleware.manager.api.GermplasmListManager;
import org.generationcp.middleware.pojos.GermplasmList;
import org.generationcp.middleware.pojos.GermplasmListData;
import org.generationcp.middleware.pojos.User;
import org.generationcp.middleware.pojos.UserDefinedField;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the GermplasmListManager interface. To instantiate this
 * class, a Hibernate Session must be passed to its constructor.
 */ 
@SuppressWarnings("unchecked")
public class GermplasmListManagerImpl extends DataManager implements GermplasmListManager{

    private static final Logger LOG = LoggerFactory.getLogger(GermplasmListManagerImpl.class);

    public GermplasmListManagerImpl() {
    }

    public GermplasmListManagerImpl(HibernateSessionProvider sessionProviderForLocal, HibernateSessionProvider sessionProviderForCentral) {
        super(sessionProviderForLocal, sessionProviderForCentral);
    }

    public GermplasmListManagerImpl(Session sessionForLocal, Session sessionForCentral) {
        super(sessionForLocal, sessionForCentral);
    }

    @Override
    public GermplasmList getGermplasmListById(Integer id) throws MiddlewareQueryException {
        if (setWorkingDatabase(id)) {
            return getGermplasmListDAO().getById(id, false);
        }
        
        return null;
    }

    @Override
    public List<GermplasmList> getAllGermplasmLists(int start, int numOfRows, Database instance) throws MiddlewareQueryException {
    	
    	return getFromInstanceByMethod(getGermplasmListDAO(), instance, "getAllExceptDeleted", 
    				new Object[] {start,  numOfRows}, new Class[] {Integer.TYPE, Integer.TYPE});
    }

    @Override
    public long countAllGermplasmLists() throws MiddlewareQueryException {
    	
    	return countAllFromCentralAndLocalByMethod(getGermplasmListDAO(), "countAllExceptDeleted", new Object[] {}, new Class[]{});
    }

    @Override
    public List<GermplasmList> getGermplasmListByName(String name, int start, int numOfRows, Operation operation, Database instance)
            throws MiddlewareQueryException {
        
    	return getFromInstanceByMethod(getGermplasmListDAO(), instance, "getByName", 
    				new Object[] {name, operation, start, numOfRows},
    				new Class[] {String.class, Operation.class, Integer.TYPE, Integer.TYPE});
    }

    @Override
    public long countGermplasmListByName(String name, Operation operation, Database instance) throws MiddlewareQueryException {

        return countFromInstanceByMethod(getGermplasmListDAO(), instance, "countByName", new Object[] { name, operation },
                new Class[] { String.class, Operation.class });
    }

    @Override
    public List<GermplasmList> getGermplasmListByStatus(Integer status, int start, int numOfRows, Database instance)
            throws MiddlewareQueryException {
        
    	return getFromInstanceByMethod(getGermplasmListDAO(), instance, "getByStatus", 
    				new Object[] {status, start, numOfRows}, 
    				new Class[] {Integer.class, Integer.TYPE, Integer.TYPE});
    }

    @Override
    public long countGermplasmListByStatus(Integer status, Database instance) throws MiddlewareQueryException {
    	
    	return countFromInstanceByMethod(getGermplasmListDAO(), instance, "countByStatus", new Object[] {status},
    	        new Class[]{Integer.class});
    }
    
    @Override
    public List<GermplasmList> getGermplasmListByGID(Integer gid, int start, int numOfRows) throws MiddlewareQueryException {
    	
    	List<String> methodNames = Arrays.asList("countByGID", "getByGID");
    	return getFromCentralAndLocalByMethod(getGermplasmListDAO(), methodNames, start, numOfRows, new Object[] {gid}, 
    	        new Class[]{Integer.class});
    }

    @Override
    public long countGermplasmListByGID(Integer gid) throws MiddlewareQueryException {
    	
    	return countAllFromCentralAndLocalByMethod(getGermplasmListDAO(), "countByGID", new Object[] {gid}, 
    	        new Class[]{Integer.class});
    }

    @Override
    public List<GermplasmListData> getGermplasmListDataByListId(Integer id, int start, int numOfRows) throws MiddlewareQueryException {

    	return getFromInstanceByIdAndMethod(getGermplasmListDataDAO(), id, "getByListId", 
    				new Object[] {id, start, numOfRows},
    				new Class[] {Integer.class, Integer.TYPE, Integer.TYPE});
    }

    @Override
    public long countGermplasmListDataByListId(Integer id) throws MiddlewareQueryException {
        
    	return countFromInstanceByIdAndMethod(getGermplasmListDataDAO(), id, "countByListId", 
    				new Object[] {id}, new Class[] {Integer.class});
    }

    @Override
    public List<GermplasmListData> getGermplasmListDataByListIdAndGID(Integer listId, Integer gid) throws MiddlewareQueryException {
        
    	return getFromInstanceByIdAndMethod(getGermplasmListDataDAO(), listId, "getByListIdAndGID", 
				new Object[] {listId, gid},
				new Class[] {Integer.class, Integer.class});
    }

    @Override
    public GermplasmListData getGermplasmListDataByListIdAndEntryId(Integer listId, Integer entryId) throws MiddlewareQueryException {
    	
        if (setWorkingDatabase(listId)) {
            return getGermplasmListDataDAO().getByListIdAndEntryId(listId, entryId);
        }
        
        return null;
    }

    @Override
    public List<GermplasmListData> getGermplasmListDataByGID(Integer gid, int start, int numOfRows) throws MiddlewareQueryException {
    	
    	List<String> methodNames = Arrays.asList("countByGID", "getByGID");
    	return getFromCentralAndLocalByMethod(getGermplasmListDataDAO(), methodNames, start, numOfRows, new Object[] {gid}, 
    	        new Class[]{Integer.class});
    }

    @Override
    public long countGermplasmListDataByGID(Integer gid) throws MiddlewareQueryException {
    	
    	return countAllFromCentralAndLocalByMethod(getGermplasmListDataDAO(), "countByGID", new Object[] {gid}, 
    	        new Class[]{Integer.class});
    }

    @Override
    public List<GermplasmList> getAllTopLevelLists(int start, int numOfRows, Database instance) throws MiddlewareQueryException {
        
    	return getFromInstanceByMethod(getGermplasmListDAO(), instance, "getAllTopLevelLists", 
    				new Object[] {start, numOfRows}, new Class[] {Integer.TYPE, Integer.TYPE});
    }

    @Override
    public List<GermplasmList> getAllTopLevelListsBatched(int batchSize, Database instance) throws MiddlewareQueryException {
        List<GermplasmList> topLevelFolders = new ArrayList<GermplasmList>();

        if (setWorkingDatabase(instance)) {
        	long topLevelCount = getGermplasmListDAO().countAllTopLevelLists();
        	int start = 0;
            while (start < topLevelCount) {
                topLevelFolders.addAll(getGermplasmListDAO().getAllTopLevelLists(start, batchSize));
                start += batchSize;
            }
        }
        
        return topLevelFolders;
    }

    @Override
    public long countAllTopLevelLists(Database instance) throws MiddlewareQueryException {
    	return countFromInstanceByMethod(getGermplasmListDAO(), instance, "countAllTopLevelLists", null, null);
    }

    @Override
    public Integer addGermplasmList(GermplasmList germplasmList) throws MiddlewareQueryException {
        List<GermplasmList> list = new ArrayList<GermplasmList>();
        list.add(germplasmList);
        List<Integer> idList = addGermplasmList(list);
        return idList.size() > 0 ? idList.get(0) : null;
    }

    @Override
    public List<Integer> addGermplasmList(List<GermplasmList> germplasmLists) throws MiddlewareQueryException {
        return addOrUpdateGermplasmList(germplasmLists, Operation.ADD);
    }

    @Override
    public Integer updateGermplasmList(GermplasmList germplasmList) throws MiddlewareQueryException {
        List<GermplasmList> list = new ArrayList<GermplasmList>();
        list.add(germplasmList);
        List<Integer> idList = updateGermplasmList(list);
        return idList.size() > 0 ? idList.get(0) : null;
    }

    @Override
    public List<Integer> updateGermplasmList(List<GermplasmList> germplasmLists) throws MiddlewareQueryException {
        return addOrUpdateGermplasmList(germplasmLists, Operation.UPDATE);
    }

    private List<Integer> addOrUpdateGermplasmList(List<GermplasmList> germplasmLists, Operation operation) throws MiddlewareQueryException {
    	requireLocalDatabaseInstance();
    	Session sessionForLocal = getCurrentSessionForLocal();

        // initialize session & transaction
        Session session = sessionForLocal;
        Transaction trans = null;

        int germplasmListsSaved = 0;
        List<Integer> germplasmListIds = new ArrayList<Integer>();
        try {
            // begin save transaction
            trans = session.beginTransaction();

            for (GermplasmList germplasmList : germplasmLists) {
                if (operation == Operation.ADD) {
                    // Auto-assign negative IDs for new local DB records
                    Integer negativeId = getGermplasmListDAO().getNegativeId("id");
                    germplasmListIds.add(negativeId);
                    germplasmList.setId(negativeId);
                    getGermplasmListDAO().saveOrUpdate(germplasmList);
                } else if (operation == Operation.UPDATE) {
                    // Check if GermplasmList is a local DB record. Throws
                    // exception if GermplasmList is a central DB record.
                    getGermplasmListDAO().validateId(germplasmList);
                    germplasmListIds.add(germplasmList.getId());
                    getGermplasmListDAO().merge(germplasmList);
                }
                
                germplasmListsSaved++;
                if (germplasmListsSaved % JDBC_BATCH_SIZE == 0) {
                    // flush a batch of inserts and release memory
                    getGermplasmListDAO().flush();
                    getGermplasmListDAO().clear();
                }
            }
            // end transaction, commit to database
            trans.commit();
        } catch (Exception e) {
        	rollbackTransaction(trans);
        	logAndThrowException(
                    "Error encountered while saving Germplasm List: GermplasmListManager.addOrUpdateGermplasmList(germplasmLists="
                            + germplasmLists + ", operation-" + operation + "): " + e.getMessage(), e, LOG);
        } finally {
            sessionForLocal.flush();
        }

        return germplasmListIds;
    }

    @Override
    public int deleteGermplasmListByListId(Integer listId) throws MiddlewareQueryException {
        GermplasmList germplasmList = getGermplasmListById(listId);
        return deleteGermplasmList(germplasmList);
    }

    @Override
    public int deleteGermplasmList(GermplasmList germplasmList) throws MiddlewareQueryException {
        List<GermplasmList> list = new ArrayList<GermplasmList>();
        list.add(germplasmList);
        return deleteGermplasmList(list);
    }

    @Override
    public int deleteGermplasmList(List<GermplasmList> germplasmLists) throws MiddlewareQueryException {
    	requireLocalDatabaseInstance();
        Session sessionForLocal = getCurrentSessionForLocal();

        // initialize session & transaction
        Session session = sessionForLocal;
        Transaction trans = null;

        int germplasmListsDeleted = 0;
        try {
            // begin delete transaction
            trans = session.beginTransaction();

            List<GermplasmListData> listDataList;
            for (GermplasmList germplasmList : germplasmLists) {
            	
            	//fetch list data for cascade delete
            	int count = (int) getGermplasmListDataDAO().countByListId(germplasmList.getId());
            	if (count > 0) {
	            	listDataList = getGermplasmListDataDAO().getByListId(germplasmList.getId(), 0, count);
	            	germplasmList.setListData(listDataList);
            	}
            	
            	//delete GermplasmList
            	
            	//getting a hibernate NonUniqueObjectException when the one below is used GCP-880
                //getGermplasmListDAO().makeTransient(germplasmList);
            	
            	germplasmList.setStatus(9);
            	updateGermplasmList(germplasmList);
            	
                germplasmListsDeleted++;
            }
            
            // end transaction, commit to database
            if(!trans.wasCommitted())
              trans.commit();
            
        } catch (Exception e) {
            rollbackTransaction(trans);
            logAndThrowException(
                    "Error encountered while deleting Germplasm List: GermplasmListManager.deleteGermplasmList(germplasmLists="
                            + germplasmLists + "): " + e.getMessage(), e, LOG);
        } finally {
            sessionForLocal.flush();
        }

        return germplasmListsDeleted;
    }

    @Override
    public Integer addGermplasmListData(GermplasmListData germplasmListData) throws MiddlewareQueryException {
        List<GermplasmListData> list = new ArrayList<GermplasmListData>();
        list.add(germplasmListData);
        List<Integer> ids = addGermplasmListData(list);
        return ids.size() > 0 ? ids.get(0) : null;
    }

    @Override
    public List<Integer> addGermplasmListData(List<GermplasmListData> germplasmListDatas) throws MiddlewareQueryException {
        return addOrUpdateGermplasmListData(germplasmListDatas, Operation.ADD);
    }

    @Override
    public Integer updateGermplasmListData(GermplasmListData germplasmListData) throws MiddlewareQueryException {
        List<GermplasmListData> list = new ArrayList<GermplasmListData>();
        list.add(germplasmListData);
        List<Integer> ids = updateGermplasmListData(list);
        return ids.size() > 0 ? ids.get(0) : null;
    }

    @Override
    public List<Integer> updateGermplasmListData(List<GermplasmListData> germplasmListDatas) throws MiddlewareQueryException {
        return addOrUpdateGermplasmListData(germplasmListDatas, Operation.UPDATE);
    }

    private List<Integer> addOrUpdateGermplasmListData(List<GermplasmListData> germplasmListDatas, Operation operation)
            throws MiddlewareQueryException {
        
    	requireLocalDatabaseInstance();
        Session sessionForLocal = getCurrentSessionForLocal();

        // initialize session & transaction
        Session session = sessionForLocal;
        Transaction trans = null;

        int germplasmListDataSaved = 0;
        List<Integer> idGermplasmListDataSaved = new ArrayList<Integer>();
        try {
            // begin save transaction
            trans = session.beginTransaction();

            GermplasmListDataDAO dao = new GermplasmListDataDAO();
            dao.setSession(session);

            for (GermplasmListData germplasmListData : germplasmListDatas) {
                if (operation == Operation.ADD) {
                    // Auto-assign negative IDs for new local DB records
                    Integer negativeListId = getGermplasmListDataDAO().getNegativeId("id");
                    germplasmListData.setId(negativeListId);
                } else if (operation == Operation.UPDATE) {
                    // Check if GermplasmList is a local DB record. Throws
                    // exception if GermplasmList is a central DB record.
                    getGermplasmListDataDAO().validateId(germplasmListData);
                }
                GermplasmListData recordSaved = getGermplasmListDataDAO().saveOrUpdate(germplasmListData);
                idGermplasmListDataSaved.add(recordSaved.getId());
                germplasmListDataSaved++;
                if (germplasmListDataSaved % JDBC_BATCH_SIZE == 0) {
                    // flush a batch of inserts and release memory
                	getGermplasmListDataDAO().flush();
                	getGermplasmListDataDAO().clear();
                }
            }
            // end transaction, commit to database
            trans.commit();
        } catch (Exception e) {
            rollbackTransaction(trans);
            logAndThrowException(
                    "Error encountered while saving Germplasm List Data: GermplasmListManager.addOrUpdateGermplasmListData(germplasmListDatas="
                            + germplasmListDatas + ", operation=" + operation + "): " + e.getMessage(), e, LOG);
        } finally {
            sessionForLocal.flush();
        }

        return idGermplasmListDataSaved;
    }

    @Override
    public int deleteGermplasmListDataByListId(Integer listId) throws MiddlewareQueryException {
    	requireLocalDatabaseInstance();
        Session sessionForLocal = getCurrentSessionForLocal();

        // initialize session & transaction
        Session session = sessionForLocal;
        Transaction trans = null;

        int germplasmListDataDeleted = 0;
        try {
            // begin delete transaction
            trans = session.beginTransaction();

            germplasmListDataDeleted = getGermplasmListDataDAO().deleteByListId(listId);
            // end transaction, commit to database
            trans.commit();
        } catch (Exception e) {
            rollbackTransaction(trans);
            logAndThrowException(
                    "Error encountered while deleting Germplasm List Data: GermplasmListManager.deleteGermplasmListDataByListId(listId="
                            + listId + "): " + e.getMessage(), e, LOG);
        } finally {
            sessionForLocal.flush();
        }

        return germplasmListDataDeleted;
    }

    @Override
    public int deleteGermplasmListDataByListIdEntryId(Integer listId, Integer entryId) throws MiddlewareQueryException {
        GermplasmListData germplasmListData = getGermplasmListDataByListIdAndEntryId(listId, entryId);
        return deleteGermplasmListData(germplasmListData);
    }

    @Override
    public int deleteGermplasmListData(GermplasmListData germplasmListData) throws MiddlewareQueryException {
        List<GermplasmListData> list = new ArrayList<GermplasmListData>();
        list.add(germplasmListData);
        return deleteGermplasmListData(list);
    }

    @Override
    public int deleteGermplasmListData(List<GermplasmListData> germplasmListDatas) throws MiddlewareQueryException {
    	requireLocalDatabaseInstance();
        Session sessionForLocal = getCurrentSessionForLocal();

        // initialize session & transaction
        Session session = sessionForLocal;
        Transaction trans = null;

        int germplasmListDataDeleted = 0;
        try {
            // begin delete transaction
            trans = session.beginTransaction();

            for (GermplasmListData germplasmListData : germplasmListDatas) {
                getGermplasmListDataDAO().makeTransient(germplasmListData);
                germplasmListDataDeleted++;
            }
            // end transaction, commit to database
            trans.commit();
        } catch (Exception e) {
            rollbackTransaction(trans);
            logAndThrowException(
                    "Error encountered while deleting Germplasm List Data: GermplasmListManager.deleteGermplasmListData(germplasmListDatas="
                            + germplasmListDatas + "): " + e.getMessage(), e, LOG);
        } finally {
            sessionForLocal.flush();
        }

        return germplasmListDataDeleted;
    }

    @Override
    public List<GermplasmList> getGermplasmListByParentFolderId(Integer parentId, int start, int numOfRows) throws MiddlewareQueryException {

    	Database instance = parentId >= 0 ? Database.CENTRAL : Database.LOCAL;
    	return getFromInstanceByMethod(getGermplasmListDAO(), instance, "getByParentFolderId", 
    				new Object[] {parentId, start, numOfRows},
    				new Class[] {Integer.class, Integer.TYPE, Integer.TYPE});
    }

    @Override
    public List<GermplasmList> getGermplasmListByParentFolderIdBatched(Integer parentId, int batchSize) throws MiddlewareQueryException {
    	List<GermplasmList> childLists = new ArrayList<GermplasmList>();
    	if (setWorkingDatabase(parentId)) {
            int start = 0;
            long childListCount = getGermplasmListDAO().countByParentFolderId(parentId);
            while (start < childListCount) {
                childLists.addAll(getGermplasmListDAO().getByParentFolderId(parentId, start, batchSize));
                start += batchSize;
            }
        }

        return childLists;
    }

    @Override
    public long countGermplasmListByParentFolderId(Integer parentId) throws MiddlewareQueryException {
    	
    	Database instance = parentId >= 0 ? Database.CENTRAL : Database.LOCAL;
    	return countFromInstanceByMethod(getGermplasmListDAO(), instance, "countByParentFolderId", 
    				new Object[] {parentId}, new Class[] {Integer.class});
    }

    @SuppressWarnings("rawtypes")
	@Override
    public List<UserDefinedField> getGermplasmListTypes() throws MiddlewareQueryException {
    	List<UserDefinedField> toReturn = new ArrayList<UserDefinedField>();
    	
    	Database instance = Database.CENTRAL;    	
    	List results = getFromInstanceByMethod(getGermplasmListDAO(), instance, "getGermplasmListTypes", 
				new Object[] {}, new Class[] {});
    	
        for (Object o : results) {
            Object[] result = (Object[]) o;
            if (result != null) {
        		Integer fldno = (Integer) result[0];
                String ftable = (String) result[1];
                String ftype = (String) result[2];
                String fcode = (String) result[3];
                String fname = (String) result[4];
                String ffmt = (String) result[5];
                String fdesc = (String) result[6];
                Integer lfldno = (Integer) result[7];
                User user = getUserDao().getById((Integer) result[8], false);
                Integer fdate = (Integer) result[9];
                Integer scaleid = (Integer) result[10];
                
                UserDefinedField userDefinedField = new UserDefinedField(fldno, ftable, ftype, fcode, fname, ffmt, fdesc, lfldno, user, fdate, scaleid);
                toReturn.add(userDefinedField);
            }
        }
    	return toReturn;
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    public List<UserDefinedField> getGermplasmNameTypes() throws MiddlewareQueryException {
    	List<UserDefinedField> toReturn = new ArrayList<UserDefinedField>();
    	
    	Database instance = Database.CENTRAL;    	
		List results = getFromInstanceByMethod(getGermplasmListDAO(), instance, "getGermplasmNameTypes", 
				new Object[] {}, new Class[] {});
    	
        for (Object o : results) {
            Object[] result = (Object[]) o;
            if (result != null) {
        		Integer fldno = (Integer) result[0];
                String ftable = (String) result[1];
                String ftype = (String) result[2];
                String fcode = (String) result[3];
                String fname = (String) result[4];
                String ffmt = (String) result[5];
                String fdesc = (String) result[6];
                Integer lfldno = (Integer) result[7];
                User user = getUserDao().getById((Integer) result[8], false);
                Integer fdate = (Integer) result[9];
                Integer scaleid = (Integer) result[10];
                
                UserDefinedField userDefinedField = new UserDefinedField(fldno, ftable, ftype, fcode, fname, ffmt, fdesc, lfldno, user, fdate, scaleid);
                toReturn.add(userDefinedField);
            }
        }
    	return toReturn;
    }
    
 
    
}
