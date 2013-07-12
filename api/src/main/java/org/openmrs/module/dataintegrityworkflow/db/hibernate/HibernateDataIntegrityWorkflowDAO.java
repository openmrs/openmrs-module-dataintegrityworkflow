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
package org.openmrs.module.dataintegrityworkflow.db.hibernate;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.User;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.dataintegrity.IntegrityCheck;
import org.openmrs.module.dataintegrity.IntegrityCheckResult;
import org.openmrs.module.dataintegrityworkflow.*;
import org.openmrs.module.dataintegrityworkflow.db.DataIntegrityWorkflowDAO;

import java.util.List;

/**
 * @author: harsz89
 */

/**
 * Hibernate data access implementation
 */
public class HibernateDataIntegrityWorkflowDAO implements DataIntegrityWorkflowDAO {
    /**
     * the session factory to use in this DAO
     */
    private SessionFactory sessionFactory;

    /**
     * @see DataIntegrityWorkflowDAO#setSessionFactory(org.hibernate.SessionFactory)
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @see DataIntegrityWorkflowDAO#getSessionFactory()
     */
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    /**
     * @see DataIntegrityWorkflowDAO#saveIntegrityWorkflowRecord(org.openmrs.module.dataintegrityworkflow.IntegrityWorkflowRecord)
     */
    public void saveIntegrityWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord) {
        sessionFactory.getCurrentSession().save(integrityWorkflowRecord);
    }

    /**
     * @see DataIntegrityWorkflowDAO#saveWorkflowStage(org.openmrs.module.dataintegrityworkflow.WorkflowStage)
     */
    public void saveWorkflowStage(WorkflowStage workflowStage) {
        sessionFactory.getCurrentSession().save(workflowStage);
    }

    /**
     * @see DataIntegrityWorkflowDAO#saveIntegrityCheckUpdate(org.openmrs.module.dataintegrityworkflow.IntegrityCheckAssignment)
     */
    public void saveIntegrityCheckUpdate(IntegrityCheckAssignment integrityCheckUpdate) throws DAOException {
        sessionFactory.getCurrentSession().save(integrityCheckUpdate);
    }

    /**
     * @see DataIntegrityWorkflowDAO#saveWorkflowAssignee(org.openmrs.module.dataintegrityworkflow.RecordAssignee)
     */
    public int  saveWorkflowAssignee(RecordAssignee recordAssignee) {
        return  (Integer)sessionFactory.getCurrentSession().save(recordAssignee);
    }

    /**
     * @see DataIntegrityWorkflowDAO#saveIntegrityRecordStageChange(org.openmrs.module.dataintegrityworkflow.IntegrityRecordStageChange)
     */
    public void saveIntegrityRecordStageChange(IntegrityRecordStageChange integrityRecordStageChange) {
        sessionFactory.getCurrentSession().save(integrityRecordStageChange);
    }

    /**
     * @see DataIntegrityWorkflowDAO#saveIntegrityRecordComment(IntegrityRecordComment
     */
    public void saveIntegrityRecordComment(IntegrityRecordComment integrityRecordComment) {
        sessionFactory.getCurrentSession().save(integrityRecordComment);
    }

    /**
     * @see DataIntegrityWorkflowDAO#saveIntegrityRecordAssignment(org.openmrs.module.dataintegrityworkflow.IntegrityRecordAssignment)
     */
    public int saveIntegrityRecordAssignment(IntegrityRecordAssignment integrityRecordAssignment){
        return (Integer)sessionFactory.getCurrentSession().save(integrityRecordAssignment);
    }

    /**
     * @see DataIntegrityWorkflowDAO#saveIntegrityRecordStatusChange(org.openmrs.module.dataintegrityworkflow.RecordStatusChange)
     */
    public void saveIntegrityRecordStatusChange(RecordStatusChange recordStatusChange) {
        sessionFactory.getCurrentSession().save(recordStatusChange);
    }

    /**
     * @see DataIntegrityWorkflowDAO#getRecordAssigneeById(int)
     */
    public RecordAssignee getRecordAssigneeById(int assigneeId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RecordAssignee.class);
        criteria.add(Restrictions.eq("recordAssigneeId",assigneeId));
        return (RecordAssignee) criteria.uniqueResult();
    }

    /**
     * @see DataIntegrityWorkflowDAO#getIntegrityCheckUpdate(int)
     */
    public IntegrityCheckAssignment getIntegrityCheckUpdate(int checkId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IntegrityCheckAssignment.class);
        criteria.add(Restrictions.eq("integrityCheckId",checkId));
        return (IntegrityCheckAssignment) criteria.uniqueResult();
    }

    /**
     * @see DataIntegrityWorkflowDAO#getIntegrityRecordAssignmentByAssigneeAndId(org.openmrs.module.dataintegrityworkflow.RecordAssignee, int)
     */
    public IntegrityRecordAssignment getIntegrityRecordAssignmentByAssigneeAndId(RecordAssignee recordAssignee,int assginmentId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IntegrityRecordAssignment.class);
        criteria.add(Restrictions.eq("recordAssignee",recordAssignee)).add(Restrictions.eq("assignmentId",assginmentId));
        return (IntegrityRecordAssignment) criteria.uniqueResult();
    }

    /**
     * @see DataIntegrityWorkflowDAO#getIntegrityWorkflowRecord(int)
     */
    public IntegrityWorkflowRecord getIntegrityWorkflowRecord(int integrityRecordWorkflowDetailId) {
        return (IntegrityWorkflowRecord) sessionFactory.getCurrentSession().get(IntegrityWorkflowRecord.class, integrityRecordWorkflowDetailId);
    }

    /**
     * @see DataIntegrityWorkflowDAO#getAllIntegrityWorkflowRecordsForCheck(int)
     */
    public List<IntegrityWorkflowRecord> getAllIntegrityWorkflowRecordsForCheck(int checkId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IntegrityWorkflowRecord.class);
        criteria.add(Restrictions.eq("integrityCheckId",checkId));
        return (List<IntegrityWorkflowRecord>) criteria.list();
    }

    /**
     * @see DataIntegrityWorkflowDAO#getAssignedIntegrityWorkflowRecordByAssignee(org.openmrs.module.dataintegrityworkflow.RecordAssignee)
     */
    public IntegrityWorkflowRecord getAssignedIntegrityWorkflowRecordByAssignee(RecordAssignee assignedUser) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IntegrityWorkflowRecord.class);
        criteria.add(Restrictions.eq("currentAssignee",assignedUser));
        return (IntegrityWorkflowRecord) criteria.uniqueResult();
    }

    /**
     * @see DataIntegrityWorkflowDAO#getAllAssignmentsOfUser(org.openmrs.User)
     */
    public List<RecordAssignee> getAllAssignmentsOfUser(User user) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RecordAssignee.class);
        criteria.add(Restrictions.eq("assignee",user));
        return (List<RecordAssignee>) criteria.list();
    }

    /**
     * @see DataIntegrityWorkflowDAO#getCurrentAssignmentOfUser(org.openmrs.User)
     */
    public RecordAssignee getCurrentAssignmentOfUser(User user) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @see DataIntegrityWorkflowDAO#getWorkflowStage(int)
     */
    public WorkflowStage getWorkflowStage(int stageId) {
        return (WorkflowStage)sessionFactory.getCurrentSession().get(WorkflowStage.class,stageId);
    }

    /**
     * @see DataIntegrityWorkflowDAO#getWorkflowStages()
     */
    public List<WorkflowStage> getWorkflowStages() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(WorkflowStage.class);
        return (List<WorkflowStage>) criteria.list();
    }

    /**
     * @see DataIntegrityWorkflowDAO#getWorkflowRecordAssigneeByUserAndWorkflowRecord(org.openmrs.module.dataintegrityworkflow.IntegrityWorkflowRecord, org.openmrs.User)
     */
    public RecordAssignee getWorkflowRecordAssigneeByUserAndWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord, User assignUser) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RecordAssignee.class);
        criteria.add(Restrictions.eq("integrityWorkflowRecord",integrityWorkflowRecord)).add(Restrictions.eq("assignee",assignUser));
        return (RecordAssignee) criteria.uniqueResult();
    }

    /**
     * @see DataIntegrityWorkflowDAO#getIntegrityRecordComments(org.openmrs.module.dataintegrityworkflow.IntegrityWorkflowRecord)
     */
    public List<IntegrityRecordComment> getIntegrityRecordComments(IntegrityWorkflowRecord integrityWorkflowRecord) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IntegrityRecordComment.class);
        criteria.add(Restrictions.eq("integrityWorkflowRecord",integrityWorkflowRecord));
        return (List<IntegrityRecordComment>) criteria.list();
    }

    /**
     * @see DataIntegrityWorkflowDAO#getIntegrityWorkflowRecordByResult(org.openmrs.module.dataintegrity.IntegrityCheckResult)
     */
    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByResult(IntegrityCheckResult integrityCheckResult) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IntegrityWorkflowRecord.class);
        criteria.add(Restrictions.eq("integrityCheckResult",integrityCheckResult));
        List records=criteria.list();
        if(records.size()>0) {
            return (IntegrityWorkflowRecord) records.get(0);
        }
        return null;
    }

    /**
     * @see DataIntegrityWorkflowDAO#getIntegrityWorkflowRecordByResultId(int)
     */
    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByResultId(int resultId) {
        return (IntegrityWorkflowRecord) sessionFactory.getCurrentSession().get(IntegrityWorkflowRecord.class, resultId);
    }

    /**
     * @see DataIntegrityWorkflowDAO#updateIntegrityRecordComment(org.openmrs.module.dataintegrityworkflow.IntegrityRecordComment)
     */
    public void updateIntegrityRecordComment(IntegrityRecordComment integrityRecordComment) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @see DataIntegrityWorkflowDAO#updateIntegrityWorkflowRecord(org.openmrs.module.dataintegrityworkflow.IntegrityWorkflowRecord)
     */
    public void updateIntegrityWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord) {
        sessionFactory.getCurrentSession().update(integrityWorkflowRecord);
    }

    /**
     * @see DataIntegrityWorkflowDAO#updateWorkflowAssignee(org.openmrs.module.dataintegrityworkflow.RecordAssignee)
     */
    public void updateWorkflowAssignee(RecordAssignee recordAssignee) {
        sessionFactory.getCurrentSession().update(recordAssignee);
    }

    /**
     * @see DataIntegrityWorkflowDAO#updateCheckUpdate(org.openmrs.module.dataintegrityworkflow.IntegrityCheckAssignment)
     */
    public void updateCheckUpdate(IntegrityCheckAssignment integrityCheckUpdate) {
        sessionFactory.getCurrentSession().update(integrityCheckUpdate);
    }

    /**
     * @see DataIntegrityWorkflowDAO#updateIntegrityRecordAssignment(org.openmrs.module.dataintegrityworkflow.IntegrityRecordAssignment)
     */
    public void updateIntegrityRecordAssignment(IntegrityRecordAssignment integrityRecordAssignment) {
        sessionFactory.getCurrentSession().update(integrityRecordAssignment);
    }

    /**
     * @see DataIntegrityWorkflowDAO#deleteIntegrityRecordComment(org.openmrs.module.dataintegrityworkflow.IntegrityRecordComment)
     */
    public void deleteIntegrityRecordComment(IntegrityRecordComment integrityRecordComment) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @see DataIntegrityWorkflowDAO#getIntegrityWorkflowRecordByRecordId(int)
     */
    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByRecordId(int recordId) {
        return (IntegrityWorkflowRecord) sessionFactory.getCurrentSession().get(IntegrityWorkflowRecord.class, recordId);
    }

    /**
     * @see DataIntegrityWorkflowDAO#findResultForIntegrityCheckById(org.openmrs.module.dataintegrity.IntegrityCheck, int)
     */
    public IntegrityCheckResult findResultForIntegrityCheckById(IntegrityCheck integrityCheck, int id) {
        if (integrityCheck == null)
            return null;
        Criteria crit = sessionFactory.getCurrentSession()
                .createCriteria(IntegrityCheckResult.class)
                .add(Restrictions.eq("integrityCheck", integrityCheck))
                .add(Restrictions.eq("integrityCheckResultId", id));
        return (IntegrityCheckResult) crit.uniqueResult();
    }
}
