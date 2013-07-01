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

/**
 * @author: harsz89
 */
public class IntegrityRecordStageChange extends BaseOpenmrsObject{
    private int stageChangeId;
    private IntegrityRecordAssignment integrityRecordAssignment;
    private User changeBy;
    private Date changeDate;
    private WorkflowStage fromWorkflowStage;
    private WorkflowStage toWorkflowStage;

    public Integer getId() {
        return getStageChangeId();
    }

    public void setId(Integer stagenChangeId) {
        this.setStageChangeId(stagenChangeId);
    }

    public int getStageChangeId() {
        return stageChangeId;
    }

    public void setStageChangeId(int stageChangeId) {
        this.stageChangeId = stageChangeId;
    }

    public User getChangeBy() {
        return changeBy;
    }

    public void setChangeBy(User changeBy) {
        this.changeBy = changeBy;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public WorkflowStage getFromWorkflowStage() {
        return fromWorkflowStage;
    }

    public void setFromWorkflowStage(WorkflowStage fromWorkflowStage) {
        this.fromWorkflowStage = fromWorkflowStage;
    }

    public WorkflowStage getToWorkflowStage() {
        return toWorkflowStage;
    }

    public void setToWorkflowStage(WorkflowStage toWorkflowStage) {
        this.toWorkflowStage = toWorkflowStage;
    }

    public IntegrityRecordAssignment getIntegrityRecordAssignment() {
        return integrityRecordAssignment;
    }

    public void setIntegrityRecordAssignment(IntegrityRecordAssignment integrityRecordAssignment) {
        this.integrityRecordAssignment = integrityRecordAssignment;
    }
}
