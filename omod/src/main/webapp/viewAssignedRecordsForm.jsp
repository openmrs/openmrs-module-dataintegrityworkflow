<%@ include file="localHeader.jsp"%>

<openmrs:require privilege="View Record Assignments" otherwise="/login.htm" redirect="/module/dataintegrityworkflow/viewChecks.form" />

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
                    "bVisible":    true },
                { "bSearchable": true,
                    "bVisible":    true },
                { "bSearchable": true,
                    "bVisible":    true }
            ]

        } );
    });
</script>
<h2>
    <c:if test="${not empty check}">
        <c:out value="${check.name}"/>-
    </c:if>
    <spring:message code="dataintegrityworkflow.assigned.record.list"/>
<c:if test="${not empty assignedUser}">
    -<c:out value="${assignedUser.username}"/>
</c:if>
</h2>
<div>
    <div id="recordTableFilters">
        <input type="checkbox" id="viewVoided"> </input>
        <label for="viewVoided">View Voided Records</label>
        <input type="checkbox" id="viewIgnored"> </input>
        <label for="viewIgnored">View Ignored Records</label>
    </div>
        <table cellspacing="0" cellpadding="2" id="table" class="display">
            <thead>
            <tr>
                <th width="200"><spring:message code="dataintegrityworkflow.checkName"/></th>
                <th width="200"><spring:message code="dataintegrityworkflow.recordId"/></th>
                <th width="300"><spring:message code="dataintegrityworkflow.status"/></th>
                <th width="300"><spring:message code="dataintegrityworkflow.assigneeBy"/></th>
                <th width="300"><spring:message code="dataintegrityworkflow.stage"/></th>
                <th width="300"><spring:message code="dataintegrityworkflow.lastupdated"/></th>
            </tr>
            </thead>
            <tbody id="formContent">
            <c:forEach items="${records}" var="record" varStatus="loopStatus">
                <tr class="row ${record.integrityCheckResult.status}">
                    <td><c:out value="${record.integrityCheckResult.integrityCheck.name}"/></td>
                    <td><a href="<openmrs:contextPath/>/module/dataintegrityworkflow/viewRecord.form?recordId=<c:out value="${record.integrityCheckResult.integrityCheckResultId}"/>&checkId=<c:out value="${record.integrityCheckResult.integrityCheck.id}"/>">RECORD-<c:out value="${record.integrityCheckResult.integrityCheckResultId}"/></a> </td>
                    <td class="status">
                            <c:out value="${record.integrityCheckResult.status}" />
                    </td>
                    <td>
                            <c:out value="${record.currentAssignee.currentIntegrityRecordAssignment.assignBy.personName}"/>
                    </td>
                    <td>
                            <c:out value="${record.currentAssignee.currentIntegrityRecordAssignment.currentStage.status}"/>
                    </td>
                    <td>
                            <openmrs:formatDate date="${record.lastUpdated}" type="long"/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
</div>
<%@ include file="/WEB-INF/template/footer.jsp" %>