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
import org.openmrs.module.dataintegrity.IntegrityCheck;

import java.util.Date;

/**
 * @author: harsz89
 */

/**
 * Class for keep integrity check assignment data
 */
public class IntegrityCheckAssignment {
    private int integrityCheckAssignmentId;
    private User assignee;
    private User assignBy;
    private IntegrityCheck integrityCheck;
    private Date assignDate;

    public int getIntegrityCheckAssignmentId() {
        return integrityCheckAssignmentId;
    }

    public void setIntegrityCheckAssignmentId(int integrityCheckAssignmentId) {
        this.integrityCheckAssignmentId = integrityCheckAssignmentId;
    }

    public Date getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public IntegrityCheck getIntegrityCheck() {
        return integrityCheck;
    }

    public void setIntegrityCheck(IntegrityCheck integrityCheck) {
        this.integrityCheck = integrityCheck;
    }

    public User getAssignBy() {
        return assignBy;
    }

    public void setAssignBy(User assignBy) {
        this.assignBy = assignBy;
    }
}
