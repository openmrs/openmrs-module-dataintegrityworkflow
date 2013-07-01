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
import org.openmrs.module.dataintegrity.IntegrityCheck;
import org.openmrs.module.dataintegrity.IntegrityCheckColumn;
import org.openmrs.module.dataintegrity.IntegrityCheckResult;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author: harsz89
 */
public class IntegrityWorkflowRecord extends BaseOpenmrsObject{
    private int integrityRecordWorkflowDetailId;
    private RecordAssignee currentAssignee;
    private Set<RecordAssignee> previousRecordAssignees;
    private Set<IntegrityRecordComment> integrityRecordComments;
    private IntegrityCheckResult integrityCheckResult;
    private int integrityCheckId;
    private Date lastUpdated;

    public Integer getId() {
        return getIntegrityRecordWorkflowDetailId();
    }

    public void setId(Integer integrityRecordWorkflowDetailId) {
        this.setIntegrityRecordWorkflowDetailId(integrityRecordWorkflowDetailId);
    }


    public int getIntegrityRecordWorkflowDetailId() {
        return integrityRecordWorkflowDetailId;
    }

    public void setIntegrityRecordWorkflowDetailId(int integrityRecordWorkflowDetailId) {
        this.integrityRecordWorkflowDetailId = integrityRecordWorkflowDetailId;
    }

    public RecordAssignee getCurrentAssignee() {
        return currentAssignee;
    }

    public void setCurrentAssignee(RecordAssignee currentAssignee) {
        this.currentAssignee = currentAssignee;
    }

    public Set<RecordAssignee> getPreviousRecordAssignees() {
        if (previousRecordAssignees == null) {
            previousRecordAssignees = new LinkedHashSet<RecordAssignee>();
        }
        return previousRecordAssignees;
    }

    public void setPreviousRecordAssignees(Set<RecordAssignee> previousRecordAssignees) {
        this.previousRecordAssignees = previousRecordAssignees;
    }

    public Set<IntegrityRecordComment> getIntegrityRecordComments() {
        if (integrityRecordComments == null) {
            integrityRecordComments = new LinkedHashSet<IntegrityRecordComment>();
        }
        return integrityRecordComments;
    }

    public void setIntegrityRecordComments(Set<IntegrityRecordComment> integrityRecordComments) {
        this.integrityRecordComments = integrityRecordComments;
    }

    public IntegrityCheckResult getIntegrityCheckResult() {
        return integrityCheckResult;
    }

    public void setIntegrityCheckResult(IntegrityCheckResult integrityCheckResult) {
        this.integrityCheckResult = integrityCheckResult;
    }

    public int getIntegrityCheckId() {
        return integrityCheckId;
    }

    public void setIntegrityCheckId(int integrityCheckId) {
        this.integrityCheckId = integrityCheckId;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
