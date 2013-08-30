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
                    "bVisible":    true },
                { "bSearchable": true,
                    "bVisible":    true }
            ]
        } );
    });
</script>
<h2><spring:message code="dataintegrityworkflow.record.stageChanges"/>-${user}:RECORD-<c:out value="${recordId}"/>
</h2>
<div>
    <table cellspacing="0" cellpadding="2" id="table" class="display">
        <thead>
        <tr>
            <th width="200"><spring:message code="dataintegrityworkflow.record.changeBy"/></th>
            <th width="300"><spring:message code="dataintegrityworkflow.record.changeDate"/></th>
            <th width="300"><spring:message code="dataintegrityworkflow.record.fromStage"/></th>
            <th width="300"><spring:message code="dataintegrityworkflow.record.toStage"/></th>
        </tr>
        </thead>
        <tbody id="formContent">
        <c:forEach items="${stagechanges}" var="change" varStatus="loopStatus">
            <tr class="row">
                <td><c:out value="${change.changeBy.personName}"/></td>
                <td><openmrs:formatDate date="${change.changeDate}" type="long"/></td>
                <td><c:out value="${change.fromWorkflowStage.status}"/></td>
                <td><c:out value="${change.toWorkflowStage.status}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>