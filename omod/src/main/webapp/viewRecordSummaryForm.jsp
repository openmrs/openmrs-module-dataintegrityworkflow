<%@ include file="localHeader.jsp"%>

<openmrs:require privilege="Manage Record Assignees" otherwise="/login.htm" redirect="/module/dataintegrityworkflow/viewChecks.form"/>

<openmrs:htmlInclude file="/moduleResources/dataintegrityworkflow/demo_table_jui.css" />
<openmrs:htmlInclude file="/moduleResources/dataintegrityworkflow/jquery.dataTables.min.js" />
<openmrs:htmlInclude file="/moduleResources/dataintegrityworkflow/css/module.css"/>
<openmrs:htmlInclude file="/moduleResources/dataintegrityworkflow/js/module.js"/>
<script>
    var $j = jQuery.noConflict();

    $j(document).ready(function() {

        $j('#table').dataTable( {
            "bFilter": true,
            "iDisplayLength": 15,
            "bProcessing": true,
            "bJQueryUI": true,
            "sPaginationType": "full_numbers",
            "aaSorting": [[ 7, "desc" ]],
            "aoColumns": [
                { "bSearchable": true,
                    "bVisible":    true },
                { "bSearchable": true,
                    "bVisible":    true },
                { "bSearchable": true,
                    "bVisible":    true }
            ]

        } );

        $j('#table1').dataTable( {
            "bFilter": true,
            "iDisplayLength": 15,
            "bProcessing": true,
            "bJQueryUI": true,
            "sPaginationType": "full_numbers",
            "aaSorting": [[ 7, "desc" ]],
            "aoColumns": [
                { "bSearchable": true,
                    "bVisible":    true },
                { "bSearchable": true,
                    "bVisible":    true },
                { "bSearchable": true,
                    "bVisible":    true }
            ]

        } );
    });
</script>
<h2><spring:message code="dataintegrityworkflow.record.summary"/>:RECORD-<c:out value="${record.integrityCheckResult.integrityCheckResultId}"/>
</h2>
<div>
    <b class="boxHeader"><spring:message code="dataintegrityworkflow.record.allassignees"/></b>
    <div class="box" >
    <table cellspacing="0" cellpadding="2" id="table" class="display">
        <thead>
        <tr>
            <th width="200"><spring:message code="dataintegrityworkflow.checkId"/></th>
            <th width="300"><spring:message code="dataintegrityworkflow.assignee"/></th>
            <th width="300"><spring:message code="dataintegrityworkflow.record.assignments"/></th>
        </tr>
        </thead>
        <tbody id="formContent">
        <c:forEach items="${record.previousRecordAssignees}" var="recordAssignee" varStatus="loopStatus">
            <tr class="row">
                <td><c:out value="${record.integrityCheckResult.integrityCheck.id}"/></td>
                <td>
                    <c:out value="${recordAssignee.assignee}" />
                </td>
                <td><a href="<openmrs:contextPath/>/module/dataintegrityworkflow/viewRecordAssignment.form?recordId=<c:out value="${record.recordId}"/>&checkId=<c:out value="${record.integrityCheckResult.integrityCheck.id}"/>&assigneeId=<c:out value="${recordAssignee.recordAssigneeId}"/>"><spring:message code="dataintegrityworkflow.assignments"/></a> </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
   </div>
    <br/>
    <b class="boxHeader"><spring:message code="dataintegrityworkflow.record.statusChange"/></b>
    <div class="box" id="">
    <table cellspacing="0" cellpadding="2" id="table1" class="display">
        <thead>
        <tr>
            <th width="200"><spring:message code="dataintegrityworkflow.record.changeBy"/></th>
            <th width="300"><spring:message code="dataintegrityworkflow.record.changeDate"/></th>
            <th width="300"><spring:message code="dataintegrityworkflow.record.action"/></th>
        </tr>
        </thead>
        <tbody id="statusContent">
        <c:forEach items="${record.recordStatusChanges}" var="statusChange" varStatus="loopStatus">
            <tr class="row">
                <td><c:out value="${statusChange.changeBy}"/></td>
                <td><openmrs:formatDate date="${statusChange.changeDate}" type="long"/></td>
                <td><c:out value="${statusChange.action}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>
</div>
<%@ include file="/WEB-INF/template/footer.jsp" %>