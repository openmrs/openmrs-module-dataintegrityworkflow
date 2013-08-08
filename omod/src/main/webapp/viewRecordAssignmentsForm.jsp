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
            "aoColumns": [
                { "bSearchable": true,
                    "bVisible":    true },
                { "bSearchable": true,
                    "bVisible":    true },
                { "bSearchable": true,
                    "bVisible":    true },
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
<h2><spring:message code="dataintegrityworkflow.record.assignments"/>-${user}:RECORD-<c:out value="${recordId}"/>
</h2>
<div>
        <table cellspacing="0" cellpadding="2" id="table" class="display">
            <thead>
            <tr>
                <th width="200"><spring:message code="dataintegrityworkflow.assigneeBy"/></th>
                <th width="300"><spring:message code="dataintegrityworkflow.dateOfAssign"/></th>
                <th width="300"><spring:message code="dataintegrityworkflow.stage"/></th>
                <th width="300"><spring:message code="dataintegrityworkflow.unassignBy"/></th>
                <th width="300"><spring:message code="dataintegrityworkflow.dateOfUnassign"/></th>
                <th width="300"><spring:message code="dataintegrityworkflow.record.stageChanges"/></th>
            </tr>
            </thead>
            <tbody id="formContent">
            <c:forEach items="${assignments}" var="assignment" varStatus="loopStatus">
                <tr class="row">
                    <td><c:out value="${assignment.assignBy.personName}"/></td>
                    <td><openmrs:formatDate date="${assignment.assignedDate}" type="long"/></td>
                    <td><c:out value="${assignment.currentStage.status}"/></td>
                    <td><c:out value="${assignment.unassignBy.personName}"/></td>
                    <td><openmrs:formatDate date="${assignment.unassignDate}" type="long"/></td>
                    <td><a href="<openmrs:contextPath/>/module/dataintegrityworkflow/viewRecordStageChange.form?recordId=<c:out value="${recordId}"/>&checkId=<c:out value="${checkId}"/>&assigneeId=<c:out value="${assigneeId}"/>&assignmentId=<c:out value="${assignment.assignmentId}"/>"><spring:message code="dataintegrityworkflow.record.changes"/></a> </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
</div>