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
package org.openmrs.module.dataintegrityworkflow.db;

import org.hibernate.SessionFactory;
import org.openmrs.User;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.dataintegrity.IntegrityCheck;
import org.openmrs.module.dataintegrity.IntegrityCheckResult;
import org.openmrs.module.dataintegrityworkflow.*;

import java.util.List;

/**
 * @author: harsz89
 */
public interface DataIntegrityWorkflowDAO {
    /**
     * set the session factory
     *
     * @param sessionFactory
     */
    public void setSessionFactory(SessionFactory sessionFactory);

    /**
     * get the session factory
     *
     * @return the session factory
     */
    public SessionFactory getSessionFactory();

    public void saveIntegrityWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord) throws DAOException;

    public void saveWorkflowStage(WorkflowStage workflowStage) throws DAOException;

    public void saveIntegrityCheckUpdate(IntegrityCheckUpdate integrityCheckUpdate) throws DAOException;

    public int saveWorkflowAssignee(RecordAssignee recordAssignee) throws DAOException;

    public void saveIntegrityRecordStageChange(IntegrityRecordStageChange integrityRecordStageChange) throws DAOException;

    public void saveIntegrityRecordComment(IntegrityRecordComment integrityRecordComment) throws DAOException;

    public int saveIntegrityRecordAssignment(IntegrityRecordAssignment integrityRecordAssignment)throws DAOException;

    public void saveIntegrityRecordStatusChange(RecordStatusChange recordStatusChange);

    public RecordAssignee getRecordAssigneeById(int assigneeId);

    public IntegrityCheckUpdate getIntegrityCheckUpdate(int checkId);

    public IntegrityRecordAssignment getIntegrityRecordAssignmentByAssigneeAndId(RecordAssignee recordAssignee, int assignmentId);

    public IntegrityWorkflowRecord getIntegrityWorkflowRecord(int integrityRecordWorkflowDetailId) throws DAOException;

    public List<IntegrityWorkflowRecord> getAllIntegrityWorkflowRecordsForCheck(int checkId) throws DAOException;

    public List<RecordAssignee> getAllAssignmentsOfUser(User user) throws DAOException;

    public RecordAssignee getCurrentAssignmentOfUser(User user) throws DAOException;

    public WorkflowStage getWorkflowStage(int stageId) throws DAOException;

    public List<WorkflowStage> getWorkflowStages();

    public RecordStatus getRecordStatus(int stageId);

    public List<RecordStatus> getAllRecordStatus();

    public RecordAssignee getWorkflowRecordAssigneeByUserAndWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord, User assignUser) throws DAOException;

    public List<IntegrityRecordComment> getIntegrityRecordComments(IntegrityWorkflowRecord integrityWorkflowRecord) throws DAOException;

    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByResult(IntegrityCheckResult integrityCheckResult) throws DAOException;

    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByResultId(int resultId) throws DAOException;

    public IntegrityWorkflowRecord getAssignedIntegrityWorkflowRecordByAssignee(RecordAssignee recordAssignee);

    public void updateIntegrityRecordAssignment(IntegrityRecordAssignment integrityRecordAssignment);

    public void updateIntegrityRecordComment(IntegrityRecordComment integrityRecordComment) throws DAOException;

    public void updateIntegrityWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord) throws DAOException;

    public void updateWorkflowAssignee(RecordAssignee recordAssignee) throws DAOException;

    public void updateCheckUpdate(IntegrityCheckUpdate integrityCheckUpdate);

    public void deleteIntegrityRecordComment(IntegrityRecordComment integrityRecordComment) throws DAOException;

    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByRecordId(int recordId);
}
