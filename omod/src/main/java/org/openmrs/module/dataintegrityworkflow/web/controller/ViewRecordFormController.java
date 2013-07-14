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
import org.openmrs.module.dataintegrity.DataIntegrityConstants;
import org.openmrs.module.dataintegrity.IntegrityCheck;
import org.openmrs.module.dataintegrity.IntegrityCheckResult;
import org.openmrs.module.dataintegrityworkflow.*;
import org.openmrs.web.WebConstants;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: harsz89
 */
public class ViewRecordFormController extends SimpleFormController {

    protected final Log log = LogFactory.getLog(getClass());

    private DataIntegrityWorkflowService getDataIntegrityWorkflowService() {
        return (DataIntegrityWorkflowService)    Context.getService(DataIntegrityWorkflowService.class);
    }

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
                                    BindException errors) throws Exception {
        String checkId=request.getParameter("checkId");
        String recordId=request.getParameter("recordId");
        String resultId=request.getParameter("resultId");
        String statusChange=request.getParameter("statusChange");
        String stageChange=request.getParameter("changeStage");
        String addComment=request.getParameter("addComment");
        String addChangeUser=request.getParameter("changeAssigned");
        DataIntegrityWorkflowService integrityWorkflowService=getDataIntegrityWorkflowService();
        IntegrityWorkflowRecord integrityWorkflowRecord;
        if(recordId!=null) {
            integrityWorkflowRecord=integrityWorkflowService.getIntegrityWorkflowRecordByRecordId(Integer.parseInt(recordId));
            if(stageChange!=null) {
                String stage=request.getParameter("stage");
                IntegrityRecordStageChange integrityWorkflowStageChange=new IntegrityRecordStageChange();
                integrityWorkflowStageChange.setChangeDate(new Date());
                integrityWorkflowStageChange.setChangeBy(Context.getAuthenticatedUser());
                integrityWorkflowStageChange.setFromWorkflowStage(integrityWorkflowRecord.getCurrentAssignee().getCurrentIntegrityRecordAssignment().getCurrentStage());
                integrityWorkflowStageChange.setToWorkflowStage(integrityWorkflowService.getWorkflowStage(Integer.parseInt(stage)));
                integrityWorkflowStageChange.setIntegrityRecordAssignment(integrityWorkflowRecord.getCurrentAssignee().getCurrentIntegrityRecordAssignment());
                integrityWorkflowService.saveIntegrityRecordStageChange(integrityWorkflowStageChange);
                integrityWorkflowRecord.getCurrentAssignee().getCurrentIntegrityRecordAssignment().setCurrentStage(integrityWorkflowService.getWorkflowStage(Integer.parseInt(stage)));
                integrityWorkflowService.updateIntegrityWorkflowRecord(integrityWorkflowRecord);
            } else if (addComment!=null) {
                String comment=request.getParameter("comment");
                if(!"".equals(comment) && comment!=null) {
                    IntegrityRecordComment integrityRecordComment=new IntegrityRecordComment();
                    integrityRecordComment.setComment(comment);
                    integrityRecordComment.setIntegrityWorkflowRecord(integrityWorkflowRecord);
                    integrityWorkflowService.saveIntegrityRecordComment(integrityRecordComment);
                }
            } else if (addChangeUser!=null) {
                String[] record=new String[1];
                record[0]=resultId;
                String user=request.getParameter("assigneeId");
                if(!"Unassign".equals(user)){
                integrityWorkflowService.assignRecords(record, Integer.parseInt(checkId), user);
                } else {
                    if(Context.getUserService().getUserByUsername(user)!=null)  {
                    integrityWorkflowService.removeRecordsAssignees(record,Integer.parseInt(checkId));
                    } else {
                        request.getSession().setAttribute(WebConstants.OPENMRS_ERROR_ATTR,
                                "dataintegrityworkflow.record.usernotfound");
                    }
                }

            } else if (statusChange!=null) {
                int status=Integer.parseInt(request.getParameter("status"));
                IntegrityCheckResult integrityCheckResult;
                IntegrityCheck integrityCheck=integrityWorkflowService.getIntegrityCheck(Integer.parseInt(checkId));
                RecordStatusChange recordStatusChange=new RecordStatusChange();
                recordStatusChange.setChangeDate(new Date());
                recordStatusChange.setChangeBy(Context.getAuthenticatedUser());
                recordStatusChange.setIntegrityWorkflowRecord(integrityWorkflowRecord);
                integrityWorkflowService.saveIntegrityRecordStatusChange(recordStatusChange);
                integrityCheckResult=integrityWorkflowService.findResultForIntegrityCheckById(integrityCheck,Integer.parseInt(resultId));
                integrityCheckResult.setStatus(DataIntegrityConstants.RESULT_STATUS_NEW.equals(status) ?
                        DataIntegrityConstants.RESULT_STATUS_IGNORED :
                        DataIntegrityConstants.RESULT_STATUS_NEW);
                integrityWorkflowService.saveIntegrityCheck(integrityCheck);
                integrityWorkflowRecord.setLastUpdated(new Date());
                integrityWorkflowService.saveIntegrityWorkflowRecord(integrityWorkflowRecord);
            }
        }
        return new ModelAndView(new RedirectView(getSuccessView()+"?recordId="+recordId+"&checkId="+checkId+"&resultId="+resultId));
    }
    protected Object formBackingObject(HttpServletRequest request) throws Exception {

        String text = "Not used";
        log.debug("Returning hello world text: " + text);
        return text;
    }

    protected Map referenceData(HttpServletRequest req) throws Exception {
        Map<String,Object> modelMap=new HashMap<String, Object>();
        String resultId=req.getParameter("resultId");
        String recordId=req.getParameter("recordId");
        String checkId=req.getParameter("checkId");
        IntegrityWorkflowRecord integrityWorkflowRecord;
        DataIntegrityWorkflowService dataIntegrityWorkflowService=getDataIntegrityWorkflowService();
        IntegrityCheck integrityCheck=dataIntegrityWorkflowService.getIntegrityCheck(Integer.parseInt(checkId));
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
        modelMap.put("check",integrityCheck);
        return modelMap;
    }
}
