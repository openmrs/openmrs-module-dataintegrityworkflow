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

import java.util.Date;

/**
 * @author: harsz89
 */
public class IntegrityCheckUpdate {
    private int integrityCheckUpdateId;
    private int integrityCheckId;
    private Date lastRun;

    public int getIntegrityCheckUpdateId() {
        return integrityCheckUpdateId;
    }

    public void setIntegrityCheckUpdateId(int integrityCheckUpdateId) {
        this.integrityCheckUpdateId = integrityCheckUpdateId;
    }

    public int getIntegrityCheckId() {
        return integrityCheckId;
    }

    public void setIntegrityCheckId(int integrityCheckId) {
        this.integrityCheckId = integrityCheckId;
    }

    public Date getLastRun() {
        return lastRun;
    }

    public void setLastRun(Date lastRun) {
        this.lastRun = lastRun;
    }
}
