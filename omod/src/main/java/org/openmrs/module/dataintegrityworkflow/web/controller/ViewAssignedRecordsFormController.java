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
package org.openmrs.module.dataintegrityworkflow.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService;
import org.openmrs.module.dataintegrityworkflow.IntegrityWorkflowRecord;
import org.springframework.web.servlet.mvc.SimpleFormController;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: harsz89
 */
public class ViewAssignedRecordsFormController extends SimpleFormController {

    protected final Log log = LogFactory.getLog(getClass());

    private DataIntegrityWorkflowService getDataIntegrityWorkflowService() {
        return (DataIntegrityWorkflowService) Context.getService(DataIntegrityWorkflowService.class);
    }

        protected Object formBackingObject(HttpServletRequest request) throws Exception {
            String text = "Not used";
            log.debug("Returning hello world text: " + text);
            return text;
        }

    protected Map referenceData(HttpServletRequest req) throws Exception {
        String assignee=req.getParameter("assignee");
        String checkId=req.getParameter("checkId");
        String fromDate=req.getParameter("fromDate");
        String toDate=req.getParameter("toDate");
        String stage=req.getParameter("stage");
        String status=req.getParameter("status");
        Map<String,Object> modelMap=new HashMap<String, Object>();
        User user;
        List<IntegrityWorkflowRecord> records = null;
        if(assignee!=null) {
            user=Context.getUserService().getUserByUsername(assignee);
            modelMap.put("assignedUser",user);
        } else {
            user=Context.getAuthenticatedUser();
        }
        DataIntegrityWorkflowService dataIntegrityWorkflowService=getDataIntegrityWorkflowService();

        if(checkId==null) {
            records=dataIntegrityWorkflowService.getAssignedIntegrityWorkflowRecordsOfCurrentUser(user);
        } else if(checkId!=null && fromDate!=null && toDate!=null && status!=null && stage!=null){
            DateFormat formatter ;
            Date fromDateFormatted;
            Date toDateFormatted;
            formatter = new SimpleDateFormat("dd/MM/yy");
            fromDateFormatted = formatter.parse(fromDate);
            toDateFormatted = formatter.parse(toDate);
            records=dataIntegrityWorkflowService.getResultsForCustomQuery(status,stage,fromDateFormatted,toDateFormatted,user,checkId);
        } else {
            records=dataIntegrityWorkflowService.getAssignedIntegrityWorkflowRecordsOfSpecifiedCheckAndCurrentUser(user,Integer.parseInt(checkId));
            modelMap.put("check",dataIntegrityWorkflowService.getIntegrityCheck(Integer.parseInt(checkId)));
        }
        modelMap.put("records",records);
        return modelMap;
    }
}
