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

/**
 * @author: harsz89
 */
public class IntegrityCheckKey extends BaseOpenmrsObject {
    private int keyId;
    private IntegrityCheck integrityCheck;
    private String keyVal;

    public IntegrityCheckKey() {
    }

    public IntegrityCheck getIntegrityCheck() {
        return integrityCheck;
    }

    public void setIntegrityCheck(IntegrityCheck integrityCheck) {
        this.integrityCheck = integrityCheck;
    }

    public Integer getId() {
        return this.keyId;
    }

    public void setId(Integer integer) {
        this.keyId=integer;
    }

    public int getKeyId() {
        return keyId;
    }

    public void setKeyId(int keyId) {
        this.keyId = keyId;
    }

    public String getKeyVal() {
        return keyVal;
    }

    public void setKeyVal(String keyVal) {
        this.keyVal = keyVal;
    }
}
