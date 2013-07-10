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

import org.openmrs.BaseOpenmrsData;
import org.openmrs.User;

import java.util.Date;

/**
 * @author: harsz89
 */
public class RecordStatusChange extends BaseOpenmrsData {
    private int recordStageChangeId;
    private IntegrityWorkflowRecord integrityWorkflowRecord;
    private User changeBy;
    private Date changeDate;
    private RecordStatus fromStatus;
    private RecordStatus toStatus;
    public Integer getId() {
        return this.recordStageChangeId;
    }

    public void setId(Integer id) {
        recordStageChangeId=id;
    }

    public int getRecordStageChangeId() {
        return recordStageChangeId;
    }

    public void setRecordStageChangeId(int recordStageChangeId) {
        this.recordStageChangeId = recordStageChangeId;
    }

    public IntegrityWorkflowRecord getIntegrityWorkflowRecord() {
        return integrityWorkflowRecord;
    }

    public void setIntegrityWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord) {
        this.integrityWorkflowRecord = integrityWorkflowRecord;
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

    public RecordStatus getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(RecordStatus fromStatus) {
        this.fromStatus = fromStatus;
    }

    public RecordStatus getToStatus() {
        return toStatus;
    }

    public void setToStatus(RecordStatus toStatus) {
        this.toStatus = toStatus;
    }
}
