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
package org.openmrs.module.dataintegrityworkflow.extension.html;

import org.openmrs.api.context.Context;
import org.openmrs.module.dataintegrityworkflow.IntegrityWorkflowConstants;
import org.openmrs.module.web.extension.AdministrationSectionExt;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: harsz89
 */

/**
 * This class defines the links that will appear on the administration page under the
 * "dataintegrityworkflow.title" heading. This extension is enabled by defining (uncommenting) it in the
 * /metadata/config.xml file.
 */
public class AdminList extends AdministrationSectionExt {
	
	/**
	 * @see org.openmrs.module.web.extension.AdministrationSectionExt#getMediaType()
	 */
	public MEDIA_TYPE getMediaType() {
		return MEDIA_TYPE.html;
	}
	
	/**
	 * @see org.openmrs.module.web.extension.AdministrationSectionExt#getTitle()
	 */
	public String getTitle() {
		return "dataintegrityworkflow.title";
	}

    public String getRequiredPrivilege() {
        return IntegrityWorkflowConstants.VIEW_RECORD_ASSIGNMENTS;
    }

    public Map<String, String> getLinks() {

        Map<String, String> map = new LinkedHashMap<String, String>();

        if (Context.hasPrivilege(IntegrityWorkflowConstants.MANAGE_RECORD_ASSIGNEES)) {
            map.put("/module/dataintegrityworkflow/viewChecks.form", "dataintegrityworkflow.manage.link");
        }
        if (Context.hasPrivilege(IntegrityWorkflowConstants.VIEW_RECORD_ASSIGNMENTS)) {
            map.put("/module/dataintegrityworkflow/viewAssignedRecords.form", "dataintegrityworkflow.view.link");
        }
        if (Context.hasPrivilege(IntegrityWorkflowConstants.VIEW_RECORD_ASSIGNMENTS)) {
            map.put("/module/dataintegrityworkflow/addWorkflowStage.form", "dataintegrityworkflow.stage.link");
        }
        return map;
    }
	
}
