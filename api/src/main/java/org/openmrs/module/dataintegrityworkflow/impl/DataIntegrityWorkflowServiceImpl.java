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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#setDataIntegrityWorkflowDAO(org.openmrs.module.dataintegrityworkflow.db.DataIntegrityWorkflowDAO)
     */
    public void setDataIntegrityWorkflowDAO(DataIntegrityWorkflowDAO dao) {
        this.dao = dao;
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#getDataIntegrityDAO()
     */
    public DataIntegrityWorkflowDAO getDataIntegrityDAO() {
        return this.dao;
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#getAllIntegrityChecks()
     */
    public List<IntegrityCheck> getAllIntegrityChecks()
    {
        return Context.getService(DataIntegrityService.class).getAllIntegrityChecks();
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#getIntegrityCheck(Integer)
     */
    public IntegrityCheck getIntegrityCheck(Integer checkId) {
        return Context.getService(DataIntegrityService.class).getIntegrityCheck(checkId);
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#getRecordAssigneeById(int)
     */
    public RecordAssignee getRecordAssigneeById(int assigneeId) {
        return dao.getRecordAssigneeById(assigneeId);
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#getIntegrityWorkflowRecordByResult(org.openmrs.module.dataintegrity.IntegrityCheckResult)
     */
    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByResult(IntegrityCheckResult integrityCheckResult) {
        return dao.getIntegrityWorkflowRecordByResult(integrityCheckResult);
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#getAllIntegrityWorkflowRecords()
     */
    public IntegrityWorkflowRecord getAllIntegrityWorkflowRecords() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#getAssignedIntegrityWorkflowRecordsOfCurrentUser(org.openmrs.User)
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
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#getWorkflowStage(int)
     */
    public WorkflowStage getWorkflowStage(int stageId) {
        return dao.getWorkflowStage(stageId);
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#getWorkflowStages()
     */
    public List<WorkflowStage> getWorkflowStages() {
        return dao.getWorkflowStages();
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#getIntegrityCheckUpdate(int)
     */
    public IntegrityCheckUpdate getIntegrityCheckUpdate(int checkId) {
        return dao.getIntegrityCheckUpdate(checkId);
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#getRecordStatus(int)
     */
    public RecordStatus getRecordStatus(int stageId) {
        return dao.getRecordStatus(stageId);
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#getAllRecordStatus()
     */
    public List<RecordStatus> getAllRecordStatus() {
        return dao.getAllRecordStatus();
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#getAllIntegrityWorkflowRecordWithCheckResult(int)
     */
    public List<IntegrityWorkflowRecordWithCheckResult> getAllIntegrityWorkflowRecordWithCheckResult(int checkId) {
        IntegrityCheck integrityCheck=Context.getService(DataIntegrityService.class).getIntegrityCheck(checkId);
        Set<IntegrityCheckResult> results=integrityCheck.getIntegrityCheckResults();
        List<IntegrityWorkflowRecord> integrityWorkflowRecords=dao.getAllIntegrityWorkflowRecordsForCheck(checkId);
        List<IntegrityWorkflowRecordWithCheckResult> integrityWorkflowRecordWithCheckResultList=new ArrayList<IntegrityWorkflowRecordWithCheckResult>();
        IntegrityWorkflowRecordWithCheckResult  integrityWorkflowRecordWithCheckResult;
        for(IntegrityCheckResult integrityCheckResult:results)
        {
            boolean found=false;
            integrityWorkflowRecordWithCheckResult=new IntegrityWorkflowRecordWithCheckResult();
            if(integrityWorkflowRecords!=null){
                for(IntegrityWorkflowRecord integrityWorkflowRecord:integrityWorkflowRecords)
                {
                    if(integrityCheckResult.getIntegrityCheckResultId().equals(integrityWorkflowRecord.getIntegrityCheckResult().getIntegrityCheckResultId()))
                    {
                        integrityWorkflowRecordWithCheckResult.setIntegrityCheckResult(integrityCheckResult);
                        integrityWorkflowRecordWithCheckResult.setIntegrityWorkflowRecord(integrityWorkflowRecord);
                        integrityWorkflowRecordWithCheckResultList.add(integrityWorkflowRecordWithCheckResult);
                        found=true;
                        break;
                    }
                }
            }
            if(!found)
            {
                integrityWorkflowRecordWithCheckResult.setIntegrityCheckResult(integrityCheckResult);
                integrityWorkflowRecordWithCheckResultList.add(integrityWorkflowRecordWithCheckResult);
            }
        }
        return integrityWorkflowRecordWithCheckResultList;
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#getIntegrityRecordComments(org.openmrs.module.dataintegrityworkflow.IntegrityWorkflowRecord)
     */
    public List<IntegrityRecordComment> getIntegrityRecordComments(IntegrityWorkflowRecord integrityWorkflowRecord) {
        return dao.getIntegrityRecordComments(integrityWorkflowRecord);
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#getAllIntegrityWorkflowRecordsForCheck(int)
     */
    public List<IntegrityWorkflowRecord> getAllIntegrityWorkflowRecordsForCheck(int checkId) {
        return dao.getAllIntegrityWorkflowRecordsForCheck(checkId);
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#getIntegrityRecordAssignmentByAssigneeAndId(org.openmrs.module.dataintegrityworkflow.RecordAssignee, int)
     */
    public IntegrityRecordAssignment getIntegrityRecordAssignmentByAssigneeAndId(RecordAssignee recordAssignee,int assginmentId) {
        return dao.getIntegrityRecordAssignmentByAssigneeAndId(recordAssignee, assginmentId);
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#saveIntegrityWorkflowRecord(org.openmrs.module.dataintegrityworkflow.IntegrityWorkflowRecord)
     */
    public void saveIntegrityWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord) {
        dao.saveIntegrityWorkflowRecord(integrityWorkflowRecord);
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#saveWorkflowStage(org.openmrs.module.dataintegrityworkflow.WorkflowStage)
     */
    public void saveWorkflowStage(WorkflowStage workflowStage) {
        dao.saveWorkflowStage(workflowStage);
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#saveWorkflowAssignee(org.openmrs.module.dataintegrityworkflow.RecordAssignee)
     */
    public int saveWorkflowAssignee(RecordAssignee recordAssignee) {
        return dao.saveWorkflowAssignee(recordAssignee);
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#saveIntegrityCheckUpdate(org.openmrs.module.dataintegrityworkflow.IntegrityCheckUpdate)
     */
    public void saveIntegrityCheckUpdate(IntegrityCheckUpdate integrityCheckUpdate) throws DAOException {
        dao.saveIntegrityCheckUpdate(integrityCheckUpdate);
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#saveIntegrityRecordStageChange(org.openmrs.module.dataintegrityworkflow.IntegrityRecordStageChange)
     */
    public void saveIntegrityRecordStageChange(IntegrityRecordStageChange integrityRecordStageChange) {
        dao.saveIntegrityRecordStageChange(integrityRecordStageChange);
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#saveIntegrityRecordComment(org.openmrs.module.dataintegrityworkflow.IntegrityRecordComment)
     */
    public void saveIntegrityRecordComment(IntegrityRecordComment integrityRecordComment) {
        dao.saveIntegrityRecordComment(integrityRecordComment);
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#saveIntegrityRecordAssignment(org.openmrs.module.dataintegrityworkflow.IntegrityRecordAssignment)
     */
    public int saveIntegrityRecordAssignment(IntegrityRecordAssignment integrityRecordAssignment) {
        return dao.saveIntegrityRecordAssignment(integrityRecordAssignment);
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#saveIntegrityRecordStatusChange(org.openmrs.module.dataintegrityworkflow.RecordStatusChange)
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
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#updateIntegrityWorkflowRecord(org.openmrs.module.dataintegrityworkflow.IntegrityWorkflowRecord)
     */
    public void updateIntegrityWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord) {
        dao.updateIntegrityWorkflowRecord(integrityWorkflowRecord);
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#updateWorkflowAssignee(org.openmrs.module.dataintegrityworkflow.RecordAssignee)
     */
    public void updateWorkflowAssignee(RecordAssignee recordAssignee) {
        dao.updateWorkflowAssignee(recordAssignee);
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#updateIntegrityRecordAssignment(org.openmrs.module.dataintegrityworkflow.IntegrityRecordAssignment)
     */
    public void updateIntegrityRecordAssignment(IntegrityRecordAssignment integrityRecordAssignment) {
        dao.saveIntegrityRecordAssignment(integrityRecordAssignment);
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#getIntegrityWorkflowRecordByRecordId(int)
     */
    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByRecordId(int recordId) {
        return dao.getIntegrityWorkflowRecordByRecordId(recordId);
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#getIntegrityWorkflowRecordByResultId(int)
     */
    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByResultId(int resultId) {
        return dao.getIntegrityWorkflowRecordByResultId(resultId);
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#updateWorkflowRecords(int)
     */
    public void updateWorkflowRecords(int checkId) {
        DataIntegrityWorkflowService integrityWorkflowService=Context.getService(DataIntegrityWorkflowService.class);
        IntegrityCheck integrityCheck=integrityWorkflowService.getIntegrityCheck(checkId);
        IntegrityCheckUpdate integrityCheckUpdate=integrityWorkflowService.getIntegrityCheckUpdate(checkId);
        if(!integrityCheckUpdate.getLastRun().equals(integrityCheck.getMostRecentRun().getDateCreated()))
        {
            for(IntegrityCheckResult integrityCheckResult:integrityCheck.getIntegrityCheckResults())
            {
                IntegrityWorkflowRecord integrityWorkflowRecord=integrityWorkflowService.getIntegrityWorkflowRecordByResult(integrityCheckResult);
                if(integrityWorkflowRecord!=null)
                {
                    if(integrityCheckResult.getStatus()!=(integrityWorkflowRecord.getRecordStatus().getStatusId()-1))
                    {
                        integrityWorkflowRecord.setRecordStatus(integrityWorkflowService.getRecordStatus(integrityCheckResult.getStatus()+1));
                        integrityWorkflowRecord.setLastUpdated(new Date());
                        integrityWorkflowService.saveIntegrityWorkflowRecord(integrityWorkflowRecord);
                    }
                }
            }
            integrityCheckUpdate.setLastRun(integrityCheck.getMostRecentRun().getDateCreated());
            integrityWorkflowService.updateCheckUpdate(integrityCheckUpdate);
        }
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#updateWorkflowRecordsOfAllChecks()
     */
    public void updateWorkflowRecordsOfAllChecks() {
        DataIntegrityWorkflowService integrityWorkflowService=Context.getService(DataIntegrityWorkflowService.class);
        List<IntegrityCheck> integrityChecks=integrityWorkflowService.getAllIntegrityChecks();
        for(IntegrityCheck integrityCheck:integrityChecks) {
            IntegrityCheckUpdate integrityCheckUpdate=integrityWorkflowService.getIntegrityCheckUpdate(integrityCheck.getIntegrityCheckId());
            if(integrityCheckUpdate!=null){
                if(!integrityCheckUpdate.getLastRun().equals(integrityCheck.getMostRecentRun().getDateCreated()))
                {
                    for(IntegrityCheckResult integrityCheckResult:integrityCheck.getIntegrityCheckResults())
                    {
                        IntegrityWorkflowRecord integrityWorkflowRecord=integrityWorkflowService.getIntegrityWorkflowRecordByResult(integrityCheckResult);
                        if(integrityWorkflowRecord!=null)
                        {
                            if(integrityCheckResult.getStatus()!=(integrityWorkflowRecord.getRecordStatus().getStatusId()-1))
                            {
                                integrityWorkflowRecord.setRecordStatus(integrityWorkflowService.getRecordStatus(integrityCheckResult.getStatus()+1));
                                integrityWorkflowRecord.setLastUpdated(new Date());
                                integrityWorkflowService.saveIntegrityWorkflowRecord(integrityWorkflowRecord);
                            }
                        }
                    }
                    integrityCheckUpdate.setLastRun(integrityCheck.getMostRecentRun().getDateCreated());
                    integrityWorkflowService.updateCheckUpdate(integrityCheckUpdate);
                }
            }
        }
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#deleteIntegrityRecordComment(org.openmrs.module.dataintegrityworkflow.IntegrityRecordComment)
     */
    public void deleteIntegrityRecordComment(IntegrityRecordComment integrityRecordComment) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#assignRecords(String[], int, String)
     */
    public void assignRecords(String[] resultList,int checkId,String user) {
        DataIntegrityWorkflowService integrityWorkflowService=Context.getService(DataIntegrityWorkflowService.class);
        User assignUser=Context.getUserService().getUserByUsername(user);
        List<IntegrityWorkflowRecord> integrityWorkflowRecords=integrityWorkflowService.getAllIntegrityWorkflowRecordsForCheck(checkId);
        for(IntegrityWorkflowRecord integrityWorkflowRecord:integrityWorkflowRecords) {
            for(String resultId: resultList) {
                if(integrityWorkflowRecord.getIntegrityCheckResult().getIntegrityCheckResultId()==Integer.parseInt(resultId)) {
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

    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#removeRecordsAssignees(String[], int)
     */
    public void removeRecordsAssignees(String[] resultList, int checkId) {
        DataIntegrityWorkflowService integrityWorkflowService=Context.getService(DataIntegrityWorkflowService.class);
        List<IntegrityWorkflowRecord> integrityWorkflowRecords=integrityWorkflowService.getAllIntegrityWorkflowRecordsForCheck(checkId);
        for(String resultId: resultList)
        {
            for(IntegrityWorkflowRecord integrityWorkflowRecord:integrityWorkflowRecords)
            {
                if(integrityWorkflowRecord.getIntegrityCheckResult().getIntegrityCheckResultId()==Integer.parseInt(resultId)) {
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
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#createWorkflowRecordsIfNotExists(String[], int)
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
                        integrityWorkflowRecord.setRecordStatus(integrityWorkflowService.getRecordStatus(integrityCheckResult.getStatus()+1));
                    }
                }
            }
        }
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#createIntegrityCheckupdateIfNotExists(int)
     */
    public void createIntegrityCheckupdateIfNotExists(int checkId)
    {
        DataIntegrityWorkflowService integrityWorkflowService=Context.getService(DataIntegrityWorkflowService.class);
        IntegrityCheckUpdate integrityCheckUpdate=integrityWorkflowService.getIntegrityCheckUpdate(checkId);
        IntegrityCheck integrityCheck=integrityWorkflowService.getIntegrityCheck(checkId);
        if(integrityCheckUpdate==null) {
            integrityCheckUpdate=new IntegrityCheckUpdate();
            integrityCheckUpdate.setIntegrityCheckId(checkId);
            integrityCheckUpdate.setLastRun(integrityCheck.getMostRecentRun().getDateCreated());
            integrityWorkflowService.saveIntegrityCheckUpdate(integrityCheckUpdate);
        }
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService#updateCheckUpdate(org.openmrs.module.dataintegrityworkflow.IntegrityCheckUpdate)
     */
    public void updateCheckUpdate(IntegrityCheckUpdate integrityCheckUpdate)
    {
        dao.updateCheckUpdate(integrityCheckUpdate);
    }
}