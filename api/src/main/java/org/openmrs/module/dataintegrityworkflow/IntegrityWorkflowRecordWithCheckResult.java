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

import org.openmrs.module.dataintegrity.IntegrityCheckResult;

/**
 * @author: harsz89
 */
public class IntegrityWorkflowRecordWithCheckResult {

    private IntegrityCheckResult integrityCheckResult;
    private IntegrityWorkflowRecord integrityWorkflowRecord;

    public IntegrityCheckResult getIntegrityCheckResult() {
        return integrityCheckResult;
    }

    public void setIntegrityCheckResult(IntegrityCheckResult integrityCheckResult) {
        this.integrityCheckResult = integrityCheckResult;
    }

    public IntegrityWorkflowRecord getIntegrityWorkflowRecord() {
        return integrityWorkflowRecord;
    }

    public void setIntegrityWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord) {
        this.integrityWorkflowRecord = integrityWorkflowRecord;
    }
}
