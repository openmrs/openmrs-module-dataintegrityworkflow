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
import org.openmrs.api.context.Context;
import org.openmrs.module.dataintegrity.IntegrityCheck;
import org.openmrs.module.dataintegrityworkflow.*;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: harsz89
 */
public class ChangeIntegrityCheckKeyFormController extends SimpleFormController {
    protected final Log log = LogFactory.getLog(getClass());

    private DataIntegrityWorkflowService getDataIntegrityWorkflowService() {
       return (DataIntegrityWorkflowService) Context.getService(DataIntegrityWorkflowService.class);
    }

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
                                    BindException errors) throws Exception {
        String checkId=request.getParameter("checkId");
        String key=request.getParameter("keyVal");
        DataIntegrityWorkflowService dataIntegrityWorkflowService=getDataIntegrityWorkflowService();
        IntegrityCheck integrityCheck=dataIntegrityWorkflowService.getIntegrityCheck(Integer.parseInt(checkId));
        IntegrityCheckKey integrityCheckKey=dataIntegrityWorkflowService.getIntegrityCheckKey(integrityCheck);
        integrityCheckKey.setKeyVal(key);
        dataIntegrityWorkflowService.updateCheckKey(integrityCheckKey);
        return new ModelAndView(new RedirectView(getSuccessView()+"?checkId="+checkId));
    }

    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        String text = "Not used";
        log.debug("Returning hello world text: " + text);
        return text;
    }

    protected Map referenceData(HttpServletRequest req) throws Exception {
        Map<String,Object> modelMap=new HashMap<String, Object>();
        String checkId=req.getParameter("checkId");
        DataIntegrityWorkflowService dataIntegrityWorkflowService=getDataIntegrityWorkflowService();
        IntegrityCheck integrityCheck=dataIntegrityWorkflowService.getIntegrityCheck(Integer.parseInt(checkId));
        IntegrityCheckKey integrityCheckKey=dataIntegrityWorkflowService.getIntegrityCheckKey(integrityCheck);
        modelMap.put("checkkey",integrityCheckKey);
        return modelMap;
    }
}
