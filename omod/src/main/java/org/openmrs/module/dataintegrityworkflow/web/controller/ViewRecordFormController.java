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
import org.openmrs.module.dataintegrity.IntegrityCheckResult;
import org.openmrs.module.dataintegrityworkflow.DataIntegrityWorkflowService;
import org.openmrs.module.dataintegrityworkflow.IntegrityRecordComment;
import org.openmrs.module.dataintegrityworkflow.IntegrityWorkflowRecord;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: harsz89
 */
public class ViewRecordFormController extends SimpleFormController {

    protected final Log log = LogFactory.getLog(getClass());

    private DataIntegrityWorkflowService getDataIntegrityWorkflowService() {
        return (DataIntegrityWorkflowService) Context.getService(DataIntegrityWorkflowService.class);
    }

    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        return "hellp";
    }

    protected Map referenceData(HttpServletRequest req) throws Exception {
        Map<String,Object> modelMap=new HashMap<String, Object>();
        String resultId=req.getParameter("resultId");
        String recordId=req.getParameter("recordId");
        String checkId=req.getParameter("checkId");
        IntegrityWorkflowRecord integrityWorkflowRecord;
        DataIntegrityWorkflowService dataIntegrityWorkflowService=getDataIntegrityWorkflowService();
        IntegrityCheck ingrityCheck=dataIntegrityWorkflowService.getIntegrityCheck(Integer.parseInt(checkId));
        if(recordId!=null){
            integrityWorkflowRecord=dataIntegrityWorkflowService.getIntegrityWorkflowRecordByRecordId(Integer.parseInt(recordId));
        } else {
            IntegrityCheckResult integrityCheckResult=new IntegrityCheckResult();
            integrityCheckResult.setId(Integer.parseInt(resultId));
            integrityWorkflowRecord=dataIntegrityWorkflowService.getIntegrityWorkflowRecordByResult(integrityCheckResult);
            if(integrityWorkflowRecord==null) {
                String[] temp=new String[1];
                temp[0]=resultId;
                dataIntegrityWorkflowService.createWorkflowRecordsIfNotExists(temp,Integer.parseInt(checkId));
                integrityWorkflowRecord=dataIntegrityWorkflowService.getIntegrityWorkflowRecordByResult(integrityCheckResult);
            }
        }
        List<IntegrityRecordComment> integrityRecordCommentList=dataIntegrityWorkflowService.getIntegrityRecordComments(integrityWorkflowRecord);
        modelMap.put("record", integrityWorkflowRecord);
        Context.addProxyPrivilege("View Users");
        modelMap.put("allusers", Context.getUserService().getAllUsers());
        Context.removeProxyPrivilege("View Users");
        modelMap.put("stages", dataIntegrityWorkflowService.getWorkflowStages());
        modelMap.put("checkId",checkId);
        modelMap.put("resultId",resultId);
        modelMap.put("recordId",integrityWorkflowRecord.getRecordId());
        modelMap.put("comments",integrityRecordCommentList);
        modelMap.put("check",ingrityCheck);
        return modelMap;
    }
}
