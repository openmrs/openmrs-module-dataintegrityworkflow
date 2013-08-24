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
import java.util.Map;

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

    /**
     * Save a single integrity workflow record
     * @param integrityWorkflowRecord   the integrity workflow record to be saved
     * @throws DAOException
     */
    public void saveIntegrityWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord) throws DAOException;

    /**
     * Save a new workflow record stage
     * @param workflowStage new workflow stage to be saved
     * @throws DAOException
     */
    public void saveWorkflowStage(WorkflowStage workflowStage) throws DAOException;

    /**
     * Note: This function is not used yet
     * Save a new integrity check updated object
     * @param integrityCheckUpdate   integrity check updated to be saved
     * @throws DAOException
     */
    public void saveIntegrityCheckUpdate(IntegrityCheckAssignment integrityCheckUpdate) throws DAOException;

    /**
     * Save a new workflow record assignee
     * @param recordAssignee record assignee to be saved
     * @return the id of the saved record assignee
     * @throws DAOException
     */
    public int saveWorkflowAssignee(RecordAssignee recordAssignee) throws DAOException;

    /**
     * Save a integrity record assignee stage change at the time assignee or other user change the stage of a record assignee
     * @param integrityRecordStageChange integrity stage change to be saved
     * @throws DAOException
     */
    public void saveIntegrityRecordStageChange(IntegrityRecordStageChange integrityRecordStageChange) throws DAOException;

    /**
     * Save a new record comment
     * @param integrityRecordComment  integrity record comment to be saved
     * @throws DAOException
     */
    public void saveIntegrityRecordComment(IntegrityRecordComment integrityRecordComment) throws DAOException;

    /**
     * Save a integrity record assignment. At the time record assignee assign for a record, record assignment will ne created
     * @param integrityRecordAssignment  integrity record assignment to be saved
     * @return  id of the saved record assignment to attach with record assignee
     * @throws DAOException
     */
    public int saveIntegrityRecordAssignment(IntegrityRecordAssignment integrityRecordAssignment)throws DAOException;

    /**
     * Save a record status change.Each record has a status where it can be change by a privileged user
     * @param recordStatusChange
     */
    public void saveIntegrityRecordStatusChange(RecordStatusChange recordStatusChange);

    /**
     * Return the record assignee associate with the given id
     * @param assigneeId assignee id for query the record assignee
     * @return matched RecordAssignee object will be returned
     */
    public RecordAssignee getRecordAssigneeById(int assigneeId);

    /**
     *  Note :This function currently not used
     *  Return the check assignment of a given integrity check
     * @param checkId check id for match with the integrity check assignment
     * @return matched integrity check assignment will be returned
     */
    public IntegrityCheckAssignment getIntegrityCheckUpdate(int checkId);

    /**
     * Return the requested integrity record assignment associated with the record assignee
     * @param recordAssignee record assignee of requested assignment
     * @param assignmentId  record assignment if for query the requested integrity record assignment
     * @return matched integrity record assignment will be returned
     */
    public IntegrityRecordAssignment getIntegrityRecordAssignmentByAssigneeAndId(RecordAssignee recordAssignee, int assignmentId);

    /**
     * Query integrity workflow record by record id
     * @param integrityRecordWorkflowDetailId id of the record to be queried
     * @return  matched integrity workflow record will be returned
     * @throws DAOException
     */
    public IntegrityWorkflowRecord getIntegrityWorkflowRecord(int integrityRecordWorkflowDetailId) throws DAOException;

    /**
     * Get a integrity record assignment by assignment id
     * @param assignmentId  assignment id of the record assignment to be queried
     * @return matched integrity record assignment will be returned
     * @throws DAOException
     */
    public IntegrityRecordAssignment getIngrityRecordAssignmentById(int assignmentId) throws DAOException;

    /**
     * Return the all integrity record associated with given integrity check
     * @param checkId check id of the integrity check to find the associate integrity workflow records with it
     * @return return a list of integrity workflow records associated with the given check id
     * @throws DAOException
     */
    public List<IntegrityWorkflowRecord> getAllIntegrityWorkflowRecordsForCheck(int checkId) throws DAOException;

    /**
     * Get the all of the record assignments of a given user
     * @param user user of the record assignments
     * @return  will return a list of record assignees associated with the given user
     * @throws DAOException
     */
    public List<RecordAssignee> getAllAssignmentsOfUser(User user) throws DAOException;

    /**
     * Get the current record assigned assignees of a user
     * @param user  user of the current assignments
     * @return  return a list of record assignees who currently assign with records
     * @throws DAOException
     */
    public RecordAssignee getCurrentAssignmentOfUser(User user) throws DAOException;

    /**
     * Get workflow stage by id
     * @param stageId id of the workflow record to be queried
     * @return matched workflow record will be returned
     * @throws DAOException
     */
    public WorkflowStage getWorkflowStage(int stageId) throws DAOException;

    /**
     * Get workflow stage by status
     * @param status status of the workflow record to be queried
     * @return matched workflow record will be returned
     * @throws DAOException
     */
    public WorkflowStage getWorkflowStageByStatus(String status) throws DAOException;

    /**
     * Get all the workflow stages
     * @return  will return the list of all available workflow stages
     */
    public List<WorkflowStage> getWorkflowStages();

    /**
     * Get the record assignee of a requested record by user. This will return record assignee of requested workflow record
     * and the user
     * @param integrityWorkflowRecord workflow record for query the associate workflow record assignee
     * @param assignUser user of the workflow record assignee
     * @return will return the record assignee of given user and workflow record
     * @throws DAOException
     */
    public RecordAssignee getWorkflowRecordAssigneeByUserAndWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord, User assignUser) throws DAOException;

    /**
     * Get all the integrity record comments associated with give workflow record
     * @param integrityWorkflowRecord integrity workflow record which comments associate
     * @return will return a list of record comments associate with the given workflow record
     * @throws DAOException
     */
    public List<IntegrityRecordComment> getIntegrityRecordComments(IntegrityWorkflowRecord integrityWorkflowRecord) throws DAOException;

    /**
     * Get the workflow record associate with integrity check result
     * @param integrityCheckResult integrity check result which associate with the workflow record
     * @return will return the matched integrity record
     * @throws DAOException
     */
    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByResult(IntegrityCheckResult integrityCheckResult) throws DAOException;

    /**
     * Not used
     * @param resultId
     * @return
     * @throws DAOException
     */
    @Deprecated
    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByResultId(int resultId) throws DAOException;

    /**
     * Get the assigned integrity workflow record of given record assignee
     * @param recordAssignee record assignee to be matched
     * @return will return the matched integrity workflow record
     */
    public IntegrityWorkflowRecord getAssignedIntegrityWorkflowRecordByAssignee(RecordAssignee recordAssignee);

    /**
     * Get the assigned integrity workflow record of given record assignee   and check
     * @param recordAssignee record assignee to be matched
     * @param checkId check id of the check which given user associated
     * @return will return the matched integrity workflow record
     */
    public IntegrityWorkflowRecord getAssignedIntegrityWorkflowRecordByAssigneeAndCheck(RecordAssignee recordAssignee,int checkId);

    /**
     * Update a given integrity record assignment
     * @param integrityRecordAssignment integrity record assignment to be updated
     */
    public void updateIntegrityRecordAssignment(IntegrityRecordAssignment integrityRecordAssignment);

    /**
     * Update integrity record comment
     * @param integrityRecordComment  integrity record comment to be updated
     * @throws DAOException
     */
    public void updateIntegrityRecordComment(IntegrityRecordComment integrityRecordComment) throws DAOException;

    /**
     * update integrity workflow record
     * @param integrityWorkflowRecord  integrity workflow record to be updated
     * @throws DAOException
     */
    public void updateIntegrityWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord) throws DAOException;

    /**
     * update given workflow assignee
     * @param recordAssignee workflow record assignee to be updated
     * @throws DAOException
     */
    public void updateWorkflowAssignee(RecordAssignee recordAssignee) throws DAOException;

    /**
     * Not used
     * @param integrityCheckUpdate
     */
    @Deprecated
    public void updateCheckUpdate(IntegrityCheckAssignment integrityCheckUpdate);

    /**
     * Delete a given record comment
     * @param integrityRecordComment integrity record comment to be deleted
     * @throws DAOException
     */
    public void deleteIntegrityRecordComment(IntegrityRecordComment integrityRecordComment) throws DAOException;

    /**
     * Get integrity workflow record by record id
     * @param recordId  record if of the workflow record
     * @return will return the associated workflow record with given record id
     */
    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByRecordId(int recordId);

    /**
     * Get the integrity check result associate with integrity check and the result id
     * @param integrityCheck integrity check of the result
     * @param id  result id
     * @return will return integrity check associated with given integrity check  and result id
     * @throws DAOException
     */
    public IntegrityCheckResult findResultForIntegrityCheckById(IntegrityCheck integrityCheck,int id) throws DAOException;

    public List getCheckRecordAssigneeCounts(IntegrityCheck integrityCheck);

    public List getCheckRecordStagesCounts(IntegrityCheck integrityCheck);

    public List getCheckRecordStatusCounts(IntegrityCheck integrityCheck);

    public void saveIntegrityCheckKey(IntegrityCheckKey integrityCheckKey);

    public boolean isCheckKeyExists(String key);

    public IntegrityCheckKey getIntegrityCheckKey(IntegrityCheck integrityCheck);

    public boolean isCheckInKeyList(IntegrityCheck integrityCheck);

    public void updateWorkflowStage(WorkflowStage workflowStage);

}
