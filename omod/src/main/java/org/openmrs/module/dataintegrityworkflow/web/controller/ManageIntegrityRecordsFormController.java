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
import org.openmrs.module.dataintegrity.IntegrityCheck;
import org.openmrs.module.dataintegrity.IntegrityCheckResult;
import org.openmrs.module.dataintegrityworkflow.*;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author: harsz89
 */
public class ManageIntegrityRecordsFormController extends SimpleFormController {
    protected final Log log = LogFactory.getLog(getClass());

    private DataIntegrityWorkflowService getDataIntegrityWorkflowService() {
        return (DataIntegrityWorkflowService) Context.getService(DataIntegrityWorkflowService.class);
    }


    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
                                    BindException errors) throws Exception {
        String action=request.getParameter("action");
        int checkId=Integer.parseInt(request.getParameter("checkId"));
        String[] recordIdList=request.getParameterValues("recordId");
        DataIntegrityWorkflowService integrityWorkflowService=getDataIntegrityWorkflowService();
        IntegrityCheck integrityCheck=integrityWorkflowService.getIntegrityCheck(checkId);
        if("assign".equals(action)) {
            String user=request.getParameter("selectUser");
            String assignmentType=request.getParameter("assignmentOptions");
            if("all".equals(assignmentType))
            {
                int i=0;
                recordIdList=new String[integrityCheck.getIntegrityCheckResults().size()];
                for(IntegrityCheckResult integrityCheckResult:integrityCheck.getIntegrityCheckResults())
                {
                    recordIdList[i]=integrityCheckResult.getIntegrityCheckResultId().toString();
                    i++;
                }
            }
            integrityWorkflowService.createWorkflowRecordsIfNotExists(recordIdList,checkId);
            integrityWorkflowService.assignRecords(recordIdList,checkId,user);
        }

        if("remove".equals(action))
        {
            String removeAssignmentType=request.getParameter("removeOptions");
            if("all".equals(removeAssignmentType))
            {
                int i=0;
                recordIdList=new String[integrityCheck.getIntegrityCheckResults().size()];
                for(IntegrityCheckResult integrityCheckResult:integrityCheck.getIntegrityCheckResults())
                {
                    recordIdList[i]=integrityCheckResult.getIntegrityCheckResultId().toString();
                    i++;
                }
            }
            integrityWorkflowService.removeRecords(recordIdList,checkId);
        }
        return new ModelAndView(new RedirectView(getSuccessView()+"?filter=all&checkId="+checkId));
    }

    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        String text = "Not used";
        log.debug("Returning hello world text: " + text);
        return text;
    }

    protected Map referenceData(HttpServletRequest req) throws Exception {
        Map<String,Object> modelMap=new HashMap<String,Object>();
        int checkId=Integer.parseInt(req.getParameter("checkId"));
        List<IntegrityWorkflowRecordWithCheckResult> records=new ArrayList<IntegrityWorkflowRecordWithCheckResult>();
        IntegrityCheck integrityCheck = null;
        if(Context.isAuthenticated())
        {
            records=getDataIntegrityWorkflowService().getAllIntegrityWorkflowRecordWithCheckResult(checkId);
            integrityCheck=getDataIntegrityWorkflowService().getIntegrityCheck(checkId);
        }
        modelMap.put("records",records);
        modelMap.put("check",integrityCheck);
        /* Normal Users haven't got the privilege to "View All Users" in the System,
                  So need  to use Proxy privileges to overcome the issue. */
        Context.addProxyPrivilege("View Users");
        modelMap.put("users", Context.getUserService().getAllUsers());
        Context.removeProxyPrivilege("View Users");
        return modelMap;
    }
}
