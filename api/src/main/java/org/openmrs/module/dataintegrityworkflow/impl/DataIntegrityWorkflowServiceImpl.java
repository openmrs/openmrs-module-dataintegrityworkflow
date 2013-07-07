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

    public List<IntegrityCheck> getAllIntegrityChecks()
    {
        return Context.getService(DataIntegrityService.class).getAllIntegrityChecks();
    }

    public IntegrityCheck getIntegrityCheck(Integer checkId) {
        return Context.getService(DataIntegrityService.class).getIntegrityCheck(checkId);
    }

    public RecordAssignee getRecordAssigneeById(int assigneeId) {
        return dao.getRecordAssigneeById(assigneeId);
    }

    public IntegrityWorkflowRecord getIntegrityWorkflowRecord(int integrityCheckResultId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByResult(IntegrityCheckResult integrityCheckResult) {
        return dao.getIntegrityWorkflowRecordByResult(integrityCheckResult);
    }

    public IntegrityWorkflowRecord getAllIntegrityWorkflowRecords() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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

    public WorkflowStage getWorkflowStage(int stageId) {
        return dao.getWorkflowStage(stageId);
    }

    public List<WorkflowStage> getWorkflowStages() {
        return dao.getWorkflowStages();
    }

    public RecordAssignee getWorkflowRecordAssigneeByUserAndWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord, User assignUser) {
        return dao.getWorkflowRecordAssigneeByUserAndWorkflowRecord(integrityWorkflowRecord,assignUser);
    }

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

    public List<IntegrityRecordComment> getIntegrityRecordComments(IntegrityWorkflowRecord integrityWorkflowRecord) {
        return dao.getIntegrityRecordComments(integrityWorkflowRecord);
    }

    public List<IntegrityWorkflowRecord> getAllIntegrityWorkflowRecordsForCheck(int checkId) {
        return dao.getAllIntegrityWorkflowRecordsForCheck(checkId);
    }

    public IntegrityRecordAssignment getIntegrityRecordAssignmentByAssigneeAndId(RecordAssignee recordAssignee,int assginmentId) {
        return dao.getIntegrityRecordAssignmentByAssigneeAndId(recordAssignee, assginmentId);
    }

    public void saveIntegrityWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord) {
        dao.saveIntegrityWorkflowRecord(integrityWorkflowRecord);
    }

    public void saveWorkflowStage(WorkflowStage workflowStage) {
        dao.saveWorkflowStage(workflowStage);
    }

    public int saveWorkflowAssignee(RecordAssignee recordAssignee) {
        return dao.saveWorkflowAssignee(recordAssignee);
    }

    public void saveIntegrityRecordStageChange(IntegrityRecordStageChange integrityRecordStageChange) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void saveIntegrityRecordComment(IntegrityRecordComment integrityRecordComment) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int saveIntegrityRecordAssignment(IntegrityRecordAssignment integrityRecordAssignment) {
        return dao.saveIntegrityRecordAssignment(integrityRecordAssignment);
    }

    public void updateIntegrityRecordComment(IntegrityRecordComment integrityRecordComment) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateIntegrityWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord) {
        dao.updateIntegrityWorkflowRecord(integrityWorkflowRecord);
    }

    public void updateWorkflowAssignee(RecordAssignee recordAssignee) {
        dao.updateWorkflowAssignee(recordAssignee);
    }

    public void updateIntegrityRecordAssignment(IntegrityRecordAssignment integrityRecordAssignment) {
        dao.saveIntegrityRecordAssignment(integrityRecordAssignment);
    }

    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByRecordId(int recordId) {
        return dao.getIntegrityWorkflowRecordByRecordId(recordId);
    }

    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByResultId(int resultId) {
        return dao.getIntegrityWorkflowRecordByResultId(resultId);
    }

    public void createRecordIfNotExistis(int resultId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteIntegrityRecordComment(IntegrityRecordComment integrityRecordComment) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void assignRecords(String[] recordIdList,int checkId,String user) {

        DataIntegrityWorkflowService integrityWorkflowService=Context.getService(DataIntegrityWorkflowService.class);
        User assignUser=Context.getUserService().getUserByUsername(user);
        List<IntegrityWorkflowRecord> integrityWorkflowRecords=integrityWorkflowService.getAllIntegrityWorkflowRecordsForCheck(checkId);
        for(IntegrityWorkflowRecord integrityWorkflowRecord:integrityWorkflowRecords) {
            for(String recordId:recordIdList) {
                if(integrityWorkflowRecord.getIntegrityCheckResult().getIntegrityCheckResultId()==Integer.parseInt(recordId)) {
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

    public void removeRecords(String[] recordIdList, int checkId) {
        DataIntegrityWorkflowService integrityWorkflowService=Context.getService(DataIntegrityWorkflowService.class);
        List<IntegrityWorkflowRecord> integrityWorkflowRecords=integrityWorkflowService.getAllIntegrityWorkflowRecordsForCheck(checkId);
        for(String recordId:recordIdList)
        {
            for(IntegrityWorkflowRecord integrityWorkflowRecord:integrityWorkflowRecords)
            {
                if(integrityWorkflowRecord.getIntegrityCheckResult().getIntegrityCheckResultId()==Integer.parseInt(recordId)) {
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
                    }
                }
            }
        }
    }

}