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

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.User;

import java.util.Date;
import java.util.Set;

/**
 * @author: harsz89
 */
public class IntegrityRecordAssignment extends BaseOpenmrsObject {
    private RecordAssignee recordAssignee;
    private int assignmentId;
    private User assignBy;
    private Date assignedDate;
    private User unassignBy;
    private Date unassignDate;
    private Set<IntegrityRecordStageChange> integrityRecordStageChanges;
    private WorkflowStage currentStage;

    public Integer getId() {
        return this.assignmentId;
    }

    public void setId(Integer id) {
        this.assignmentId=id;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public User getAssignBy() {
        return assignBy;
    }

    public void setAssignBy(User assignBy) {
        this.assignBy = assignBy;
    }

    public Date getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }

    public User getUnassignBy() {
        return unassignBy;
    }

    public void setUnassignBy(User unassignBy) {
        this.unassignBy = unassignBy;
    }

    public Date getUnassignDate() {
        return unassignDate;
    }

    public void setUnassignDate(Date unassignDate) {
        this.unassignDate = unassignDate;
    }

    public Set<IntegrityRecordStageChange> getIntegrityRecordStageChanges() {
        return integrityRecordStageChanges;
    }

    public void setIntegrityRecordStageChanges(Set<IntegrityRecordStageChange> integrityRecordStageChanges) {
        this.integrityRecordStageChanges = integrityRecordStageChanges;
    }

    public WorkflowStage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(WorkflowStage currentStage) {
        this.currentStage = currentStage;
    }

    public RecordAssignee getRecordAssignee() {
        return recordAssignee;
    }

    public void setRecordAssignee(RecordAssignee recordAssignee) {
        this.recordAssignee = recordAssignee;
    }
}
