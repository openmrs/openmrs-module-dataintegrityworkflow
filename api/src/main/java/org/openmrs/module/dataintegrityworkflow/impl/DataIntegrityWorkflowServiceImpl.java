/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.dataintegrityworkflow.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.dataintegrity.DataIntegrityService;
import org.openmrs.module.dataintegrity.IntegrityCheck;
import org.openmrs.module.dataintegrity.IntegrityCheckResult;
import org.openmrs.module.dataintegrityworkflow.*;
import org.openmrs.module.dataintegrityworkflow.db.DataIntegrityWorkflowDAO;

import java.util.*;

/**
 * @author: harsz89
 */

/**
 * Data Integrity Workflow Service Implementation
 */
public class DataIntegrityWorkflowServiceImpl implements DataIntegrityWorkflowService {

    /**
     * dao for use with this service implementation
     */
    private DataIntegrityWorkflowDAO dao;
    protected final Log log = LogFactory.getLog(getClass());

    /**
     * @see DataIntegrityWorkflowService#setDataIntegrityWorkflowDAO(org.openmrs.module.dataintegrityworkflow.db.DataIntegrityWorkflowDAO)
     */
    public void setDataIntegrityWorkflowDAO(DataIntegrityWorkflowDAO dao) {
        this.dao = dao;
    }

    /**
     * @see DataIntegrityWorkflowService#getDataIntegrityDAO()
     */
    public DataIntegrityWorkflowDAO getDataIntegrityDAO() {
        return this.dao;
    }

    /**
     * @see DataIntegrityWorkflowService#getAllIntegrityChecks()
     */
    public List<IntegrityCheck> getAllIntegrityChecks()
    {
        return Context.getService(DataIntegrityService.class).getAllIntegrityChecks();
    }

    /**
     * @see DataIntegrityWorkflowService#getIntegrityCheck(Integer)
     */
    public IntegrityCheck getIntegrityCheck(Integer checkId) {
        return Context.getService(DataIntegrityService.class).getIntegrityCheck(checkId);
    }

    /**
     * @see DataIntegrityWorkflowService#getRecordAssigneeById(int)
     */
    public RecordAssignee getRecordAssigneeById(int assigneeId) {
        return dao.getRecordAssigneeById(assigneeId);
    }

    /**
     * @see DataIntegrityWorkflowService#getIntegrityWorkflowRecordByResult(org.openmrs.module.dataintegrity.IntegrityCheckResult)
     */
    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByResult(IntegrityCheckResult integrityCheckResult) {
        return dao.getIntegrityWorkflowRecordByResult(integrityCheckResult);
    }

    /**
     * @see DataIntegrityWorkflowService#getAllIntegrityWorkflowRecords()
     */
    public IntegrityWorkflowRecord getAllIntegrityWorkflowRecords() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @see DataIntegrityWorkflowService#getAssignedIntegrityWorkflowRecordsOfCurrentUser(org.openmrs.User)
     */
    public List<IntegrityWorkflowRecord> getAssignedIntegrityWorkflowRecordsOfCurrentUser(User assignedUser) {
        List<RecordAssignee> assignees=dao.getAllAssignmentsOfUser(assignedUser);
        List<IntegrityWorkflowRecord> integrityWorkflowRecords=new ArrayList<IntegrityWorkflowRecord>();
        IntegrityWorkflowRecord temp;
        if(assignees!=null) {
            for(RecordAssignee recordAssignee:assignees) {
                temp=dao.getAssignedIntegrityWorkflowRecordByAssignee(recordAssignee);
                if(temp!=null) {
                    integrityWorkflowRecords.add(temp);
                }
            }
        }
        return integrityWorkflowRecords;
    }

    /**
     * @see DataIntegrityWorkflowService#getAssignedIntegrityWorkflowRecordsOfSpecifiedCheckAndCurrentUser(org.openmrs.User, int) (org.openmrs.User)
     */
    public List<IntegrityWorkflowRecord> getAssignedIntegrityWorkflowRecordsOfSpecifiedCheckAndCurrentUser(User assignedUser, int checkId) {
        List<RecordAssignee> assignees=dao.getAllAssignmentsOfUser(assignedUser);
        List<IntegrityWorkflowRecord> integrityWorkflowRecords=new ArrayList<IntegrityWorkflowRecord>();
        IntegrityWorkflowRecord temp;
        if(assignees!=null) {
            for(RecordAssignee recordAssignee:assignees) {
                temp=dao.getAssignedIntegrityWorkflowRecordByAssigneeAndCheck(recordAssignee, checkId);
                if(temp!=null) {
                    integrityWorkflowRecords.add(temp);
                }
            }
        }
        return integrityWorkflowRecords;
    }

    /**
     * @see DataIntegrityWorkflowService#getWorkflowStage(int)
     */
    public WorkflowStage getWorkflowStage(int stageId) {
        return dao.getWorkflowStage(stageId);
    }

    /**
     * @see DataIntegrityWorkflowService#getWorkflowStages()
     */
    public List<WorkflowStage> getWorkflowStages() {
        return dao.getWorkflowStages();
    }

    /**
     * @see DataIntegrityWorkflowService#getIntegrityCheckUpdate(int)
     */
    public IntegrityCheckAssignment getIntegrityCheckUpdate(int checkId) {
        return dao.getIntegrityCheckUpdate(checkId);
    }


    public IntegrityCheckResult getIntegrityCheckResultByUuid(IntegrityCheck integrityCheck,String uuid) {
        return Context.getService(DataIntegrityService.class).findResultForIntegrityCheckByUid(integrityCheck, uuid);
    }

    /**
     * @see DataIntegrityWorkflowService#getAllIntegrityWorkflowRecordWithCheckResult(int)
     */
    public List<IntegrityWorkflowRecordWithCheckResult> getAllIntegrityWorkflowRecordWithCheckResult(int checkId) {
        IntegrityCheck integrityCheck=Context.getService(DataIntegrityService.class).getIntegrityCheck(checkId);
        Set<IntegrityCheckResult> results=integrityCheck.getIntegrityCheckResults();
        IntegrityWorkflowRecord integrityWorkflowRecord;
        List<IntegrityWorkflowRecordWithCheckResult> integrityWorkflowRecordWithCheckResultList=new ArrayList<IntegrityWorkflowRecordWithCheckResult>();
        IntegrityWorkflowRecordWithCheckResult  integrityWorkflowRecordWithCheckResult;
        for(IntegrityCheckResult integrityCheckResult:results)
        {
            integrityWorkflowRecordWithCheckResult=new IntegrityWorkflowRecordWithCheckResult();
            integrityWorkflowRecord=dao.getIntegrityWorkflowRecordByResult(integrityCheckResult);
            if(integrityWorkflowRecord!=null){
                integrityWorkflowRecordWithCheckResult.setIntegrityCheckResult(integrityCheckResult);
                integrityWorkflowRecordWithCheckResult.setIntegrityWorkflowRecord(integrityWorkflowRecord);
                integrityWorkflowRecordWithCheckResultList.add(integrityWorkflowRecordWithCheckResult);
            } else {
                integrityWorkflowRecordWithCheckResult.setIntegrityCheckResult(integrityCheckResult);
                integrityWorkflowRecordWithCheckResultList.add(integrityWorkflowRecordWithCheckResult);
            }
        }
        return integrityWorkflowRecordWithCheckResultList;
    }

    /**
     * @see DataIntegrityWorkflowService#getIntegrityRecordComments(org.openmrs.module.dataintegrityworkflow.IntegrityWorkflowRecord)
     */
    public List<IntegrityRecordComment> getIntegrityRecordComments(IntegrityWorkflowRecord integrityWorkflowRecord) {
        return dao.getIntegrityRecordComments(integrityWorkflowRecord);
    }

    /**
     * @see DataIntegrityWorkflowService#getAllIntegrityWorkflowRecordsForCheck(int)
     */
    public List<IntegrityWorkflowRecord> getAllIntegrityWorkflowRecordsForCheck(int checkId) {
        return dao.getAllIntegrityWorkflowRecordsForCheck(checkId);
    }

    public IntegrityRecordAssignment getIntegrityCheckAssignmentById(int assignmentId) {
        return dao.getIngrityRecordAssignmentById(assignmentId);
    }

    /**
     * @see DataIntegrityWorkflowService#getIntegrityRecordAssignmentByAssigneeAndId(org.openmrs.module.dataintegrityworkflow.RecordAssignee, int)
     */
    public IntegrityRecordAssignment getIntegrityRecordAssignmentByAssigneeAndId(RecordAssignee recordAssignee,int assginmentId) {
        return dao.getIntegrityRecordAssignmentByAssigneeAndId(recordAssignee, assginmentId);
    }

    /**
     * @see DataIntegrityWorkflowService#saveIntegrityWorkflowRecord(org.openmrs.module.dataintegrityworkflow.IntegrityWorkflowRecord)
     */
    public void saveIntegrityWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord) {
        dao.saveIntegrityWorkflowRecord(integrityWorkflowRecord);
    }

    /**
     * @see DataIntegrityWorkflowService#saveWorkflowStage(org.openmrs.module.dataintegrityworkflow.WorkflowStage)
     */
    public void saveWorkflowStage(WorkflowStage workflowStage) {
        dao.saveWorkflowStage(workflowStage);
    }

    /**
     * @see DataIntegrityWorkflowService#saveWorkflowAssignee(org.openmrs.module.dataintegrityworkflow.RecordAssignee)
     */
    public int saveWorkflowAssignee(RecordAssignee recordAssignee) {
        return dao.saveWorkflowAssignee(recordAssignee);
    }

    /**
     * @see DataIntegrityWorkflowService#saveIntegrityCheckUpdate(org.openmrs.module.dataintegrityworkflow.IntegrityCheckAssignment)
     */
    public void saveIntegrityCheckUpdate(IntegrityCheckAssignment integrityCheckUpdate) throws DAOException {
        dao.saveIntegrityCheckUpdate(integrityCheckUpdate);
    }

    /**
     * @see DataIntegrityWorkflowService#saveIntegrityRecordStageChange(org.openmrs.module.dataintegrityworkflow.IntegrityRecordStageChange)
     */
    public void saveIntegrityRecordStageChange(IntegrityRecordStageChange integrityRecordStageChange) {
        dao.saveIntegrityRecordStageChange(integrityRecordStageChange);
    }

    /**
     * @see DataIntegrityWorkflowService#saveIntegrityRecordComment(org.openmrs.module.dataintegrityworkflow.IntegrityRecordComment)
     */
    public void saveIntegrityRecordComment(IntegrityRecordComment integrityRecordComment) {
        dao.saveIntegrityRecordComment(integrityRecordComment);
    }

    /**
     * @see DataIntegrityWorkflowService#saveIntegrityRecordAssignment(org.openmrs.module.dataintegrityworkflow.IntegrityRecordAssignment)
     */
    public int saveIntegrityRecordAssignment(IntegrityRecordAssignment integrityRecordAssignment) {
        return dao.saveIntegrityRecordAssignment(integrityRecordAssignment);
    }

    public void saveIntegrityCheck(IntegrityCheck integrityCheck) {
        Context.getService(DataIntegrityService.class).saveIntegrityCheck(integrityCheck);
    }


    /**
     * @see DataIntegrityWorkflowService#saveIntegrityRecordStatusChange(org.openmrs.module.dataintegrityworkflow.RecordStatusChange)
     */
    public void saveIntegrityRecordStatusChange(RecordStatusChange recordStatusChange) {
        dao.saveIntegrityRecordStatusChange(recordStatusChange);
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#updateIntegrityRecordComment(org.openmrs.module.dataintegrityworkflow.IntegrityRecordComment)
     */
    public void updateIntegrityRecordComment(IntegrityRecordComment integrityRecordComment) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @see DataIntegrityWorkflowService#updateIntegrityWorkflowRecord(org.openmrs.module.dataintegrityworkflow.IntegrityWorkflowRecord)
     */
    public void updateIntegrityWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord) {
        dao.updateIntegrityWorkflowRecord(integrityWorkflowRecord);
    }

    /**
     * @see DataIntegrityWorkflowService#updateWorkflowAssignee(org.openmrs.module.dataintegrityworkflow.RecordAssignee)
     */
    public void updateWorkflowAssignee(RecordAssignee recordAssignee) {
        dao.updateWorkflowAssignee(recordAssignee);
    }

    /**
     * @see DataIntegrityWorkflowService#updateIntegrityRecordAssignment(org.openmrs.module.dataintegrityworkflow.IntegrityRecordAssignment)
     */
    public void updateIntegrityRecordAssignment(IntegrityRecordAssignment integrityRecordAssignment) {
        dao.saveIntegrityRecordAssignment(integrityRecordAssignment);
    }

    /**
     * @see DataIntegrityWorkflowService#getIntegrityWorkflowRecordByRecordId(int)
     */
    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByRecordId(int recordId) {
        return dao.getIntegrityWorkflowRecordByRecordId(recordId);
    }

    /**
     * @see DataIntegrityWorkflowService#getIntegrityWorkflowRecordByResultId(int)
     */
    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByResultId(int resultId) {
        return dao.getIntegrityWorkflowRecordByResultId(resultId);
    }

    /**
     * @see DataIntegrityWorkflowService#deleteIntegrityRecordComment(org.openmrs.module.dataintegrityworkflow.IntegrityRecordComment)
     */
    public void deleteIntegrityRecordComment(IntegrityRecordComment integrityRecordComment) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @see DataIntegrityWorkflowService#assignRecords(String[], int, String)
     */
    public void assignRecords(String[] resultList,int checkId,String user) {
        DataIntegrityWorkflowService integrityWorkflowService=Context.getService(DataIntegrityWorkflowService.class);
        User assignUser=Context.getUserService().getUserByUsername(user);
        IntegrityCheckResult integrityCheckResult;
        IntegrityWorkflowRecord integrityWorkflowRecord;
        IntegrityCheck integrityCheck=integrityWorkflowService.getIntegrityCheck(checkId);
        for(String resultId: resultList) {
            integrityCheckResult=integrityWorkflowService.findResultForIntegrityCheckById(integrityCheck,Integer.parseInt(resultId));
            integrityWorkflowRecord=integrityWorkflowService.getIntegrityWorkflowRecordByResult(integrityCheckResult);
            if(integrityWorkflowRecord!=null)  {
                if(integrityWorkflowRecord.getPreviousRecordAssignees().size()==0) {
                    int assigneeId;
                    int assginmentId;
                    RecordAssignee recordAssignee=new RecordAssignee();
                    recordAssignee.setAssignee(assignUser);
                    recordAssignee.setIntegrityWorkflowRecord(integrityWorkflowRecord);
                    assigneeId=integrityWorkflowService.saveWorkflowAssignee(recordAssignee);
                    recordAssignee=integrityWorkflowService.getRecordAssigneeById(assigneeId);
                    IntegrityRecordAssignment integrityRecordAssignment=new IntegrityRecordAssignment();
                    integrityRecordAssignment.setAssignBy(Context.getUserContext().getAuthenticatedUser());
                    integrityRecordAssignment.setAssignedDate(new Date());
                    integrityRecordAssignment.setCurrentStage(integrityWorkflowService.getWorkflowStage(1));
                    integrityRecordAssignment.setRecordAssignee(recordAssignee);
                    assginmentId=integrityWorkflowService.saveIntegrityRecordAssignment(integrityRecordAssignment);
                    integrityRecordAssignment=integrityWorkflowService.getIntegrityRecordAssignmentByAssigneeAndId(recordAssignee, assginmentId);
                    recordAssignee.setCurrentIntegrityRecordAssignment(integrityRecordAssignment);
                    integrityWorkflowService.updateWorkflowAssignee(recordAssignee);
                    integrityWorkflowRecord.setCurrentAssignee(recordAssignee);
                    integrityWorkflowRecord.setLastUpdated(new Date());
                    integrityWorkflowService.updateIntegrityWorkflowRecord(integrityWorkflowRecord);
                } else {
                    boolean isAssigneeFound=false;
                    for(RecordAssignee assignee:integrityWorkflowRecord.getPreviousRecordAssignees())
                    {
                        if(assignee.getAssignee().equals(assignUser))
                        {
                            isAssigneeFound=true;
                            if(!assignee.equals(integrityWorkflowRecord.getCurrentAssignee()))
                            {
                                int assginmentId;
                                IntegrityRecordAssignment integrityRecordAssignment=new IntegrityRecordAssignment();
                                integrityRecordAssignment.setAssignBy(Context.getUserContext().getAuthenticatedUser());
                                integrityRecordAssignment.setAssignedDate(new Date());
                                integrityRecordAssignment.setCurrentStage(integrityWorkflowService.getWorkflowStage(1));
                                integrityRecordAssignment.setRecordAssignee(assignee);
                                assginmentId=integrityWorkflowService.saveIntegrityRecordAssignment(integrityRecordAssignment);
                                integrityRecordAssignment=integrityWorkflowService.getIntegrityRecordAssignmentByAssigneeAndId(assignee, assginmentId);
                                assignee.setCurrentIntegrityRecordAssignment(integrityRecordAssignment);
                                integrityWorkflowService.updateWorkflowAssignee(assignee);
                                RecordAssignee previousRecordAssignee=integrityWorkflowRecord.getCurrentAssignee();
                                if(previousRecordAssignee!=null){
                                    IntegrityRecordAssignment previousAssigneeAssignment=previousRecordAssignee.getCurrentIntegrityRecordAssignment();
                                    previousAssigneeAssignment.setUnassignBy(Context.getUserContext().getAuthenticatedUser());
                                    previousAssigneeAssignment.setUnassignDate(new Date());
                                    integrityWorkflowService.updateIntegrityRecordAssignment(previousAssigneeAssignment);
                                    previousRecordAssignee.setCurrentIntegrityRecordAssignment(previousAssigneeAssignment);
                                    integrityWorkflowService.updateWorkflowAssignee(previousRecordAssignee);
                                }
                                integrityWorkflowRecord.setCurrentAssignee(assignee);
                                integrityWorkflowRecord.setLastUpdated(new Date());
                                integrityWorkflowService.saveIntegrityWorkflowRecord(integrityWorkflowRecord);
                            }
                            break;
                        }
                    }
                    if(!isAssigneeFound) {
                        int assigneeId;
                        int assginmentId;
                        RecordAssignee recordAssignee=new RecordAssignee();
                        recordAssignee.setAssignee(assignUser);
                        recordAssignee.setIntegrityWorkflowRecord(integrityWorkflowRecord);
                        assigneeId=integrityWorkflowService.saveWorkflowAssignee(recordAssignee);
                        recordAssignee=integrityWorkflowService.getRecordAssigneeById(assigneeId);
                        IntegrityRecordAssignment integrityRecordAssignment=new IntegrityRecordAssignment();
                        integrityRecordAssignment.setAssignBy(Context.getUserContext().getAuthenticatedUser());
                        integrityRecordAssignment.setAssignedDate(new Date());
                        integrityRecordAssignment.setCurrentStage(integrityWorkflowService.getWorkflowStage(1));
                        integrityRecordAssignment.setRecordAssignee(recordAssignee);
                        assginmentId=integrityWorkflowService.saveIntegrityRecordAssignment(integrityRecordAssignment);
                        integrityRecordAssignment=integrityWorkflowService.getIntegrityRecordAssignmentByAssigneeAndId(recordAssignee, assginmentId);
                        recordAssignee.setCurrentIntegrityRecordAssignment(integrityRecordAssignment);
                        integrityWorkflowService.updateWorkflowAssignee(recordAssignee);
                        RecordAssignee previousRecordAssignee=integrityWorkflowRecord.getCurrentAssignee();
                        if(previousRecordAssignee!=null){
                            IntegrityRecordAssignment previousAssigneeAssignment=previousRecordAssignee.getCurrentIntegrityRecordAssignment();
                            previousAssigneeAssignment.setUnassignBy(Context.getUserContext().getAuthenticatedUser());
                            previousAssigneeAssignment.setUnassignDate(new Date());
                            integrityWorkflowService.updateIntegrityRecordAssignment(previousAssigneeAssignment);
                            previousRecordAssignee.setCurrentIntegrityRecordAssignment(previousAssigneeAssignment);
                            integrityWorkflowService.updateWorkflowAssignee(previousRecordAssignee);
                        }
                        integrityWorkflowRecord.setCurrentAssignee(recordAssignee);
                        integrityWorkflowRecord.setLastUpdated(new Date());
                        integrityWorkflowService.updateIntegrityWorkflowRecord(integrityWorkflowRecord);
                    }
                }
            }
        }

    }

    /**
     * @see DataIntegrityWorkflowService#removeRecordsAssignees(String[], int)
     */
    public void removeRecordsAssignees(String[] resultList, int checkId) {
        DataIntegrityWorkflowService integrityWorkflowService=Context.getService(DataIntegrityWorkflowService.class);
        List<IntegrityWorkflowRecord> integrityWorkflowRecords=integrityWorkflowService.getAllIntegrityWorkflowRecordsForCheck(checkId);
        IntegrityCheckResult integrityCheckResult;
        IntegrityWorkflowRecord integrityWorkflowRecord;
        IntegrityCheck integrityCheck=integrityWorkflowService.getIntegrityCheck(checkId);
        for(String resultId: resultList)
        {
            integrityCheckResult=integrityWorkflowService.findResultForIntegrityCheckById(integrityCheck,Integer.parseInt(resultId));
            integrityWorkflowRecord=integrityWorkflowService.getIntegrityWorkflowRecordByResult(integrityCheckResult);
            if(integrityWorkflowRecord!=null)  {
                RecordAssignee  recordAssignee=integrityWorkflowRecord.getCurrentAssignee();
                IntegrityRecordAssignment integrityRecordAssignment=recordAssignee.getCurrentIntegrityRecordAssignment();
                integrityRecordAssignment.setUnassignDate(new Date());
                integrityRecordAssignment.setUnassignBy(Context.getUserContext().getAuthenticatedUser());
                integrityWorkflowService.updateIntegrityRecordAssignment(integrityRecordAssignment);
                recordAssignee.setCurrentIntegrityRecordAssignment(integrityRecordAssignment);
                integrityWorkflowService.updateWorkflowAssignee(recordAssignee);
                integrityWorkflowRecord.setCurrentAssignee(null);
                integrityWorkflowRecord.setLastUpdated(new Date());
                integrityWorkflowService.updateIntegrityWorkflowRecord(integrityWorkflowRecord);
            }
        }
    }

    /**
     * @see DataIntegrityWorkflowService#createWorkflowRecordsIfNotExists(String[], int)
     */
    public void createWorkflowRecordsIfNotExists(String[] resultIdList,int checkId) {
        DataIntegrityWorkflowService integrityWorkflowService=Context.getService(DataIntegrityWorkflowService.class);
        int checkResultId;
        IntegrityCheck integrityCheck=integrityWorkflowService.getIntegrityCheck(checkId);
        for(IntegrityCheckResult integrityCheckResult:integrityCheck.getIntegrityCheckResults())
        {
            for(String recordId:resultIdList)
            {
                checkResultId=Integer.parseInt(recordId);
                if(checkResultId==integrityCheckResult.getIntegrityCheckResultId())
                {
                    if(integrityWorkflowService.getIntegrityWorkflowRecordByResult(integrityCheckResult)==null)
                    {
                        IntegrityWorkflowRecord integrityWorkflowRecord=new IntegrityWorkflowRecord();
                        integrityWorkflowRecord.setIntegrityCheckResult(integrityCheckResult);
                        integrityWorkflowRecord.setIntegrityCheckId(checkId);
                        integrityWorkflowRecord.setLastUpdated(new Date());
                        integrityWorkflowService.saveIntegrityWorkflowRecord(integrityWorkflowRecord);
                        //integrityWorkflowRecord.setRecordStatus(integrityWorkflowService.getRecordStatus(integrityCheckResult.getStatus()+1));
                    }
                }
            }
        }
    }

    /**
     * @see DataIntegrityWorkflowService#updateCheckUpdate(org.openmrs.module.dataintegrityworkflow.IntegrityCheckAssignment)
     */
    public void updateCheckUpdate(IntegrityCheckAssignment integrityCheckUpdate)
    {
        dao.updateCheckUpdate(integrityCheckUpdate);
    }

    public IntegrityCheckResult findResultForIntegrityCheckById(IntegrityCheck integrityCheck, int id) {
        return dao.findResultForIntegrityCheckById(integrityCheck,id);
    }

    public Map<User, Integer> getCheckRecordAssigneeCounts(IntegrityCheck integrityCheck) {
        Map<Object,Integer> counts=displayObjectsMap(dao.getCheckRecordAssigneeCounts(integrityCheck));
        Map<User,Integer> out=new HashMap<User, Integer>();
        Iterator it = counts.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            out.put(Context.getUserService().getUserByUsername(pairs.getKey().toString()),(Integer)pairs.getValue());
        }
        return out;
    }

    public Map<WorkflowStage, Integer> getCheckRecordStagesCounts(IntegrityCheck integrityCheck) {
        Map<Object,Integer> counts=displayObjectsMap(dao.getCheckRecordStagesCounts(integrityCheck));
        Map<WorkflowStage,Integer> out=new HashMap<WorkflowStage, Integer>();
        Iterator it = counts.entrySet().iterator();
        int count=0;
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            out.put(dao.getWorkflowStage((Integer)pairs.getKey()),(Integer)pairs.getValue());
            count+=(Integer)pairs.getValue();
        }
        out.put(dao.getWorkflowStageByStatus("Unassigned"),integrityCheck.getIntegrityCheckResults().size()-count);
        return out;
    }

    public Map<Integer, Integer> getCheckRecordStatusCounts(IntegrityCheck integrityCheck) {
        Map<Object,Integer> counts=displayObjectsMap(dao.getCheckRecordStatusCounts(integrityCheck));
        Map<Integer,Integer> out=new HashMap<Integer, Integer>();
        Iterator it = counts.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            out.put((Integer)pairs.getKey(),(Integer)pairs.getValue());
        }
        return out;
    }

    private Map<Object,Integer> displayObjectsMap(List list)
    {
        Map<Object,Integer> temp=new HashMap<Object, Integer>();
        Iterator iter = list.iterator();
        if (!iter.hasNext())
        {
            return null;
        }
        while (iter.hasNext())
        {
            Object[] obj = (Object[]) iter.next();
            temp.put(obj[0],Integer.parseInt(obj[1].toString()));
        }
        return temp;
    }
}