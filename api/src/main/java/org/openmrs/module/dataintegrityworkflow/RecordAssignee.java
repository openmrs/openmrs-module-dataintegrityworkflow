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

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author: harsz89
 */
public class RecordAssignee extends BaseOpenmrsObject {
    private Integer recordAssigneeId;
    private User assignee;
    private IntegrityWorkflowRecord integrityWorkflowRecord;
    private Set<IntegrityRecordAssignment> integrityRecordAssignmentList;
    private IntegrityRecordAssignment currentIntegrityRecordAssignment;

    public Integer getId() {
        return this.getRecordAssigneeId();
    }

    public void setId(Integer recordAssigneeId) {
        this.setRecordAssigneeId(recordAssigneeId);
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public Integer getRecordAssigneeId() {
        return recordAssigneeId;
    }

    public void setRecordAssigneeId(Integer recordAssigneeId) {
        this.recordAssigneeId = recordAssigneeId;
    }

    public IntegrityWorkflowRecord getIntegrityWorkflowRecord() {
        return integrityWorkflowRecord;
    }

    public void setIntegrityWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord) {
        this.integrityWorkflowRecord = integrityWorkflowRecord;
    }

    public Set<IntegrityRecordAssignment> getIntegrityRecordAssignmentList() {
        if(integrityRecordAssignmentList==null) {
            integrityRecordAssignmentList=new LinkedHashSet<IntegrityRecordAssignment>();
        }
        return integrityRecordAssignmentList;
    }

    public void setIntegrityRecordAssignmentList(Set<IntegrityRecordAssignment> integrityRecordAssignmentList) {
        this.integrityRecordAssignmentList = integrityRecordAssignmentList;
    }

    public IntegrityRecordAssignment getCurrentIntegrityRecordAssignment() {
        return currentIntegrityRecordAssignment;
    }

    public void setCurrentIntegrityRecordAssignment(IntegrityRecordAssignment currentIntegrityRecordAssignment) {
        this.currentIntegrityRecordAssignment = currentIntegrityRecordAssignment;
    }
}
