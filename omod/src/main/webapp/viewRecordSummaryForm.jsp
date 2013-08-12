<%@ include file="localHeader.jsp"%>

<openmrs:require privilege="Manage Record Assignees" otherwise="/login.htm" redirect="/module/dataintegrityworkflow/viewChecks.form"/>

<openmrs:htmlInclude file="/moduleResources/dataintegrityworkflow/demo_table_jui.css" />
<openmrs:htmlInclude file="/moduleResources/dataintegrityworkflow/jquery.dataTables.min.js" />
<openmrs:htmlInclude file="/moduleResources/dataintegrityworkflow/css/module.css"/>
<openmrs:htmlInclude file="/moduleResources/dataintegrityworkflow/js/module.js"/>
<script>
    var $j = jQuery.noConflict();

    $j(document).ready(function() {
        $j('#table2').dataTable( {
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
                    "bVisible":    true }
            ]
        } );
    });

</script>
<h2><spring:message code="dataintegrityworkflow.record.summary"/>:RECORD-<c:out value="${record.integrityCheckResult.integrityCheckResultId}"/>
</h2>
    <b class="boxHeader"><spring:message code="dataintegrityworkflow.record.history.header"/></b>
    <div class="box" id="">
        <table cellspacing="0" cellpadding="2" id="table2" class="display">
            <thead>
            <tr>
                <th width="200"><spring:message code="dataintegrityworkflow.record.changeBy"/></th>
                <th width="300"><spring:message code="dataintegrityworkflow.record.changeDate"/></th>
                <th width="300"><spring:message code="dataintegrityworkflow.record.action"/></th>
            </tr>
            </thead>
            <tbody id="contents">
            <c:forEach items="${summary}" var="summaryEntry" varStatus="loopStatus">
                <tr class="row">
                    <td><c:out value="${summaryEntry.changeBy.personName}"/></td>
                    <td><openmrs:formatDate date="${summaryEntry.dateActionPerformed}" type="long"/></td>
                    <td class="status"><c:out value="${summaryEntry.action}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/WEB-INF/template/footer.jsp" %>