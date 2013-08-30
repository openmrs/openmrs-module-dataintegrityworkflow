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


import org.openmrs.BaseOpenmrsMetadata;


/**
 * @author: harsz89
 */

/**
 * Pojo class for keep comment data associate with a workflow record
 */
public class IntegrityRecordComment extends BaseOpenmrsMetadata{

    private Integer integrityRecordCommentId;
    private IntegrityWorkflowRecord integrityWorkflowRecord;
    private String comment;

    public Integer getId() {
        return this.getIntegrityRecordCommentId();
    }

    public void setId(Integer integrityRecordCommentId) {
        this.setIntegrityRecordCommentId(integrityRecordCommentId);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getIntegrityRecordCommentId() {
        return integrityRecordCommentId;
    }

    public void setIntegrityRecordCommentId(Integer integrityRecordCommentId) {
        this.integrityRecordCommentId = integrityRecordCommentId;
    }

    public IntegrityWorkflowRecord getIntegrityWorkflowRecord() {
        return integrityWorkflowRecord;
    }

    public void setIntegrityWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord) {
        this.integrityWorkflowRecord = integrityWorkflowRecord;
    }
}
