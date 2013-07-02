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
import org.openmrs.module.dataintegrity.IntegrityCheck;
import org.openmrs.module.dataintegrity.IntegrityCheckResult;
import org.openmrs.module.dataintegrityworkflow.db.DataIntegrityWorkflowDAO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: harsz89
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

    public List<IntegrityCheck> getAllIntegrityChecks();

    public IntegrityCheck getIntegrityCheck(Integer checkId);

    public RecordAssignee getRecordAssigneeById(int assigneeId);

    public IntegrityWorkflowRecord getIntegrityWorkflowRecord(int integrityRecordId);

    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByResult(IntegrityCheckResult integrityCheckResult);

    public IntegrityWorkflowRecord getAllIntegrityWorkflowRecords();

    public List<IntegrityWorkflowRecord> getAssignedIntegrityWorkflowRecordsOfCurrentUser(User assignedUser);

    public List<IntegrityWorkflowRecord> getAllAssignedIntegrityWorkflowRecordsOfCurrentUser(User assigneduser);

    public List<RecordAssignee> getAllAssignmentsOfUser(User user);

    public RecordAssignee getCurrentAssignmentOfUser(User user);

    public WorkflowStage getWorkflowStage(int stageId);

    public RecordAssignee getWorkflowRecordAssigneeByUserAndWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord, User assignUser);

    public List<IntegrityWorkflowRecordWithCheckResult> getAllIntegrityWorkflowRecordWithCheckResult(int checkId);

    public List<IntegrityWorkflowRecord> getAllIntegrityWorkflowRecordsForCheck(int checkId);

    public void saveIntegrityWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord);

    public void saveWorkflowStage(WorkflowStage workflowStage);

    public int saveWorkflowAssignee(RecordAssignee recordAssignee);

    public void saveIntegrityRecordStageChange(IntegrityRecordStageChange integrityRecordStageChange);

    public void saveIntegrityRecordComment(IntegrityRecordComment integrityRecordComment);

    public List<IntegrityRecordComment> getIntegrityRecordComments(int integrityWorkflowRecordId);

    public void updateIntegrityRecordComment(IntegrityRecordComment integrityRecordComment);

    public void updateIntegrityWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord);

    public void updateWorkflowAssignee(RecordAssignee recordAssignee);

    public void deleteIntegrityRecordComment(IntegrityRecordComment integrityRecordComment);

    public void assignRecords(String[] recordList, int checkId, String user);

    public void removeRecords(String[] recordList, int checkId);

    public int saveIntegrityRecordAssignment(IntegrityRecordAssignment integrityRecordAssignment);

    public void createWorkflowRecordsIfNotExists(String[] recordIdList, int checkId);

    public IntegrityRecordAssignment getIntegrityRecordAssignmentByAssigneeAndId(RecordAssignee recordAssignee, int assignmentId);

    public void updateIntegrityRecordAssignment(IntegrityRecordAssignment integrityRecordAssignment);
}
