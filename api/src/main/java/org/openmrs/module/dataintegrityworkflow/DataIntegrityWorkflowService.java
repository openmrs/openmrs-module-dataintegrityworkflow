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
package org.openmrs.module.dataintegrityworkflow;

import org.openmrs.User;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.dataintegrity.IntegrityCheck;
import org.openmrs.module.dataintegrity.IntegrityCheckResult;
import org.openmrs.module.dataintegrityworkflow.db.DataIntegrityWorkflowDAO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: harsz89
 */

/**
 * Data integrity service for persistence and manipulation
 */
@Transactional
public interface DataIntegrityWorkflowService {
    /**
     * sets the data integrity workflow DAO
     *
     * @param dao
     */
    public void setDataIntegrityWorkflowDAO(DataIntegrityWorkflowDAO dao);

    /**
     * returns the data integrity workflow DAO
     *
     * @return
     */
    public DataIntegrityWorkflowDAO getDataIntegrityDAO();

    /**
     * Get all integrity checks available in the system by using service of data integrity module
     * @return list of available integrity checks
     */
    public List<IntegrityCheck> getAllIntegrityChecks();

    /**
     * Get integrity check by id
     * @param checkId
     * @return  integrity check of given checkId
     */
    public IntegrityCheck getIntegrityCheck(Integer checkId);

    /**
     * Get record assignee by assignee id
     * @param assigneeId
     * @return record assignee object assigned with the given assigneeId
     */
    public RecordAssignee getRecordAssigneeById(int assigneeId);

    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByResult(IntegrityCheckResult integrityCheckResult);

    public IntegrityWorkflowRecord getAllIntegrityWorkflowRecords();

    public List<IntegrityWorkflowRecord> getAssignedIntegrityWorkflowRecordsOfCurrentUser(User assignedUser);

    public WorkflowStage getWorkflowStage(int stageId);

    public List<WorkflowStage> getWorkflowStages();

    public IntegrityCheckAssignment getIntegrityCheckUpdate(int checkId);

    public List<IntegrityWorkflowRecordWithCheckResult> getAllIntegrityWorkflowRecordWithCheckResult(int checkId);

    public List<IntegrityWorkflowRecord> getAllIntegrityWorkflowRecordsForCheck(int checkId);

    public IntegrityRecordAssignment getIntegrityCheckAssignmentById(int assignmentId);

    public IntegrityCheckResult getIntegrityCheckResultByUuid(IntegrityCheck integrityCheck,String uuid);

    public void saveIntegrityWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord);

    public void saveWorkflowStage(WorkflowStage workflowStage);

    public int saveWorkflowAssignee(RecordAssignee recordAssignee);

    public void saveIntegrityCheckUpdate(IntegrityCheckAssignment integrityCheckUpdate) throws DAOException;

    public void saveIntegrityRecordStageChange(IntegrityRecordStageChange integrityRecordStageChange);

    public void saveIntegrityRecordComment(IntegrityRecordComment integrityRecordComment);

    public List<IntegrityRecordComment> getIntegrityRecordComments(IntegrityWorkflowRecord integrityWorkflowRecord);

    public void updateIntegrityRecordComment(IntegrityRecordComment integrityRecordComment);

    public void updateIntegrityWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord);

    public void updateWorkflowAssignee(RecordAssignee recordAssignee);

    public void deleteIntegrityRecordComment(IntegrityRecordComment integrityRecordComment);

    public void assignRecords(String[] resultList, int checkId, String user);

    public void removeRecordsAssignees(String[] resultList, int checkId);

    public int saveIntegrityRecordAssignment(IntegrityRecordAssignment integrityRecordAssignment);

    public void saveIntegrityRecordStatusChange(RecordStatusChange recordStatusChange);

    public void saveIntegrityCheck(IntegrityCheck integrityCheck);

    public void createWorkflowRecordsIfNotExists(String[] resultIdList, int checkId);

    public IntegrityRecordAssignment getIntegrityRecordAssignmentByAssigneeAndId(RecordAssignee recordAssignee, int assignmentId);

    public void updateIntegrityRecordAssignment(IntegrityRecordAssignment integrityRecordAssignment);

    /**
     * Get integrity workflow record object by integrity record id
     * @param recordId
     * @return integrity workflow record assigned with given id
     */
    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByRecordId(int recordId);

    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByResultId(int resultId);

    //public void updateWorkflowRecords(int checkId);

    //public void updateWorkflowRecordsOfAllChecks();

    public void updateCheckUpdate(IntegrityCheckAssignment integrityCheckUpdate);

    public IntegrityCheckResult findResultForIntegrityCheckById(IntegrityCheck integrityCheck,int id);
}
