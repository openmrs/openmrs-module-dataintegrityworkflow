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

import java.io.Serializable;

/**
 * @author: harsz89
 */

/**
 * Pojo file for the workflow stages relation in the Data Integrity Workflow Module
 */
public class WorkflowStage extends BaseOpenmrsData implements Serializable {
    private Integer workflowStageId;
    private String  status;

    /*
     *  Default constructor for the class
     */
    public WorkflowStage() {}

    /*
     * Contructor with status as arguement
     */
    public WorkflowStage(String status) {
        this.status = status;
    }

    public Integer getworkflowStageId() {
        return this.workflowStageId;
    }

    public void setworkflowStageId(Integer workflowStageId) {
        this.workflowStageId = workflowStageId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        if (status.length() > 50) {
            this.status = status.substring(0, 50);
        } else {
            this.status = status;
        }
    }

    public Integer getId() {
        return this.workflowStageId;
    }

    public void setId(Integer workflowStageId) {
        this.workflowStageId = workflowStageId;
    }
}
