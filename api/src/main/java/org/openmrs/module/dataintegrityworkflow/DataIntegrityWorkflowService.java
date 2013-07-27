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
import java.util.Map;

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

    /**
     * Get the workflow record associate with integrity check result
     * @param integrityCheckResult integrity check result which associate with the workflow record
     * @return will return the matched integrity record
     * @throws DAOException
     */
    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByResult(IntegrityCheckResult integrityCheckResult);

    /**
     * Not used
     * @return
     */
    public IntegrityWorkflowRecord getAllIntegrityWorkflowRecords();

    /**
     * Return the list of all workflow record associated with a given user
     * @param assignedUser record assignee to find the workflow record associate with him
     * @return will return a list of workflow record associated with the given user
     */
    public List<IntegrityWorkflowRecord> getAssignedIntegrityWorkflowRecordsOfCurrentUser(User assignedUser);

    /**
     * Return the list of all workflow record associated with a given user  for given check
     * @param assignedUser record assignee to find the workflow record associate with him
     * @param checkId check which need to find the assignments of the user
     * @return will return a list of workflow record associated with the given user
     */
    public List<IntegrityWorkflowRecord> getAssignedIntegrityWorkflowRecordsOfSpecifiedCheckAndCurrentUser(User assignedUser,int checkId);

    /**
     * Get workflow stage by id
     * @param stageId id of the workflow record to be queried
     * @return matched workflow record will be returned
     * @throws DAOException
     */
    public WorkflowStage getWorkflowStage(int stageId);

    /**
     * Get all the workflow stages
     * @return  will return the list of all available workflow stages
     */
    public List<WorkflowStage> getWorkflowStages();

    /**
     *  Note :This function currently not used
     *  Return the check assignment of a given integrity check
     * @param checkId check id for match with the integrity check assignment
     * @return matched integrity check assignment will be returned
     */
    public IntegrityCheckAssignment getIntegrityCheckUpdate(int checkId);

    /**
     * Get the all integrity check results with associate workflow records for a given integrity check
     * @param checkId check id of the integrity check to find the mapped results
     * @return will return list of integrity records with check results associate with each workflow record
     */
    public List<IntegrityWorkflowRecordWithCheckResult> getAllIntegrityWorkflowRecordWithCheckResult(int checkId);

    /**
     * Return the all integrity record associated with given integrity check
     * @param checkId check id of the integrity check to find the associate integrity workflow records with it
     * @return return a list of integrity workflow records associated with the given check id
     * @throws DAOException
     */
    public List<IntegrityWorkflowRecord> getAllIntegrityWorkflowRecordsForCheck(int checkId);

    /**
     * Get a integrity record assignment by assignment id
     * @param assignmentId  assignment id of the record assignment to be queried
     * @return matched integrity record assignment will be returned
     * @throws DAOException
     */
    public IntegrityRecordAssignment getIntegrityCheckAssignmentById(int assignmentId);

    /**
     * Not used
     * @param integrityCheck
     * @param uuid
     * @return
     */
    public IntegrityCheckResult getIntegrityCheckResultByUuid(IntegrityCheck integrityCheck,String uuid);

    /**
     * Save a single integrity workflow record
     * @param integrityWorkflowRecord   the integrity workflow record to be saved
     * @throws DAOException
     */
    public void saveIntegrityWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord);

    /**
     * Save a new workflow record stage
     * @param workflowStage new workflow stage to be saved
     * @throws DAOException
     */
    public void saveWorkflowStage(WorkflowStage workflowStage);

    /**
     * Save a new workflow record assignee
     * @param recordAssignee record assignee to be saved
     * @return the id of the saved record assignee
     * @throws DAOException
     */
    public int saveWorkflowAssignee(RecordAssignee recordAssignee);

    /**
     * Note: This function is not used yet
     * Save a new integrity check updated object
     * @param integrityCheckUpdate   integrity check updated to be saved
     * @throws DAOException
     */
    public void saveIntegrityCheckUpdate(IntegrityCheckAssignment integrityCheckUpdate) throws DAOException;

    /**
     * Save a integrity record assignee stage change at the time assignee or other user change the stage of a record assignee
     * @param integrityRecordStageChange integrity stage change to be saved
     * @throws DAOException
     */
    public void saveIntegrityRecordStageChange(IntegrityRecordStageChange integrityRecordStageChange);

    /**
     * Save a new record comment
     * @param integrityRecordComment  integrity record comment to be saved
     * @throws DAOException
     */
    public void saveIntegrityRecordComment(IntegrityRecordComment integrityRecordComment);

    /**
     * Get all the integrity record comments associated with give workflow record
     * @param integrityWorkflowRecord integrity workflow record which comments associate
     * @return will return a list of record comments associate with the given workflow record
     * @throws DAOException
     */
    public List<IntegrityRecordComment> getIntegrityRecordComments(IntegrityWorkflowRecord integrityWorkflowRecord);

    /**
     * Update integrity record comment
     * @param integrityRecordComment  integrity record comment to be updated
     * @throws DAOException
     */
    public void updateIntegrityRecordComment(IntegrityRecordComment integrityRecordComment);

    /**
     * update integrity workflow record
     * @param integrityWorkflowRecord  integrity workflow record to be updated
     * @throws DAOException
     */
    public void updateIntegrityWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord);

    /**
     * update given workflow assignee
     * @param recordAssignee workflow record assignee to be updated
     * @throws DAOException
     */
    public void updateWorkflowAssignee(RecordAssignee recordAssignee);

    /**
     * Delete a given record comment
     * @param integrityRecordComment integrity record comment to be deleted
     * @throws DAOException
     */
    public void deleteIntegrityRecordComment(IntegrityRecordComment integrityRecordComment);

    /**
     * Assign set of record for given user
     * @param resultList result id list to be assigned to given user
     * @param checkId check id of the result belongs
     * @param user records will be assign for this user
     */
    public void assignRecords(String[] resultList, int checkId, String user);

    /**
     * Remove assignees from given result list
     * @param resultList result list to remove its assignees
     * @param checkId check id which results belongs
     */
    public void removeRecordsAssignees(String[] resultList, int checkId);

    /**
     * Save a integrity record assignment. At the time record assignee assign for a record, record assignment will ne created
     * @param integrityRecordAssignment  integrity record assignment to be saved
     * @return  id of the saved record assignment to attach with record assignee
     * @throws DAOException
     */
    public int saveIntegrityRecordAssignment(IntegrityRecordAssignment integrityRecordAssignment);

    /**
     * Save a integrity record assignee stage change at the time assignee or other user change the stage of a record assignee
     * @param recordStatusChange integrity stage change to be saved
     * @throws DAOException
     */
    public void saveIntegrityRecordStatusChange(RecordStatusChange recordStatusChange);

    /**
     * Save a integrity check
     * @param integrityCheck integrity check to be saved
     * @see org.openmrs.module.dataintegrity.DataIntegrityService#saveIntegrityCheck(org.openmrs.module.dataintegrity.IntegrityCheck)
     */
    public void saveIntegrityCheck(IntegrityCheck integrityCheck);

    /**
     * Create workflow records if not exists in the database
     * @param resultIdList results id list which need to be created workflow records
     * @param checkId  check id which results list belongs
     */
    public void createWorkflowRecordsIfNotExists(String[] resultIdList, int checkId);

    /**
     * Return the requested integrity record assignment associated with the record assignee
     * @param recordAssignee record assignee of requested assignment
     * @param assignmentId  record assignment if for query the requested integrity record assignment
     * @return matched integrity record assignment will be returned
     */
    public IntegrityRecordAssignment getIntegrityRecordAssignmentByAssigneeAndId(RecordAssignee recordAssignee, int assignmentId);

    /**
     * Update a given integrity record assignment
     * @param integrityRecordAssignment integrity record assignment to be updated
     */
    public void updateIntegrityRecordAssignment(IntegrityRecordAssignment integrityRecordAssignment);

    /**
     * Get integrity workflow record object by integrity record id
     * @param recordId
     * @return integrity workflow record assigned with given id
     */
    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByRecordId(int recordId);

    /**
     * Not used
     * @param resultId
     * @return
     */
    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByResultId(int resultId);

    //public void updateWorkflowRecords(int checkId);

    //public void updateWorkflowRecordsOfAllChecks();

    /**
     * Not used
     * @param integrityCheckUpdate
     */
    public void updateCheckUpdate(IntegrityCheckAssignment integrityCheckUpdate);

    /**
     * Get the integrity check result associate with integrity check and the result id
     * @param integrityCheck integrity check of the result
     * @param id  result id
     * @return will return integrity check associated with given integrity check  and result id
     * @throws DAOException
     */
    public IntegrityCheckResult findResultForIntegrityCheckById(IntegrityCheck integrityCheck,int id);

    public Map<User,Integer> getCheckRecordAssigneeCounts(IntegrityCheck integrityCheck);

    public Map<WorkflowStage,Integer> getCheckRecordStagesCounts(IntegrityCheck integrityCheck);

    public Map<Integer,Integer> getCheckRecordStatusCounts(IntegrityCheck integrityCheck);

}
