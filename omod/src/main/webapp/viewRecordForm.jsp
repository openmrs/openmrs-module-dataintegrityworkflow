<%@ include file="localHeader.jsp"%>

<openmrs:hasPrivilege privilege="View Record Assignments">

<%@ taglib prefix="kc" tagdir="/WEB-INF/tags/module/dataintegrityworkflow/"%>
<openmrs:htmlInclude file="/moduleResources/dataintegrityworkflow/css/module.css"/>
<openmrs:htmlInclude file="/moduleResources/dataintegrityworkflow/js/module.js"/>
<script>
    var $j = jQuery.noConflict();
    $j(function() {
        var availableTags = ["default"
                <c:forEach items="${allusers}" var="user">
                ,"${user}"
                </c:forEach>
        ];
        $j("#assigneeId" ).autocomplete({
            source: availableTags
        });
    });

    $j(document).ready(function()
    {
        $j('.toggleAddTag').click(function(event)
        {
            $j('#addTag').slideToggle('fast');
            event.preventDefault();
        });
        colorVisibleTableRows("table", "white", "whitesmoke");
        colorVisibleTableRows("table7", "white", "whitesmoke");
    });

    function renderCell(data, colDatatype) {
    <openmrs:globalProperty key="dataintegrity.actionServerUrl" var="actionServerUrl"/>
        <c:if test="${empty actionServerUrl}">
                <c:set var="actionServerUrl" value="${pageContext.request.contextPath}"/>
                </c:if>

        var linkPrefix = '<a target="new" href="<c:out value="${actionServerUrl}"/>';

        if (colDatatype == "Person")
            return linkPrefix + '/personDashboard.form?personId=' + data + '">' + data + '</a>';
        if (colDatatype == "Patient")
            return linkPrefix + '/patientDashboard.form?patientId=' + data + '">' + data + '</a>';
        if (colDatatype == "Concept")
            return linkPrefix + '/dictionary/concept.htm?conceptId=' + data + '">' + data + '</a>';
        if (colDatatype == "User")
            return linkPrefix + '/admin/users/user.form?userId=' + data + '">' + data + '</a>';
        if (colDatatype == "Encounter")
            return linkPrefix + '/admin/encounters/encounter.form?encounterId=' + data + '">' + data + '</a>';
        if (colDatatype == "Observation")
            return linkPrefix + '/admin/observations/obs.form?obsId=' + data + '">' + data + '</a>';
        if (colDatatype == "Date") {
            // TODO find a good way of doing this from javascript
            return data;
        }
        if (colDatatype == "Yes/No")
            return data == "1" ? "Yes" : "No";
        return data;
    }

</script>

<style>
    form {display: inline; }
</style>

<h2>
    <spring:message code="dataintegrityworkflow.record"/>
</h2>

<b class="boxHeader"><spring:message code="dataintegrityworkflow.record.basic"/></b>
<div class="box" >
    <table id="table">
        <tr>
            <th width="100"><spring:message code="dataintegrityworkflow.recordId"/></th>
            <td>RECORD-<c:out value="${record.integrityCheckResult.integrityCheckResultId} "/> </td>
        </tr>
        <tr>
            <th width="100"><spring:message code="dataintegrityworkflow.status"/></th>
            <td class="status">
                <c:out value="${record.recordStatus.status} "/>
            </td>
        </tr>
        <tr>
            <th width="300"><spring:message code="dataintegrityworkflow.assignee"/></th>
            <td><c:out value="${record.currentAssignee.assignee} "/> </td>
        </tr>
        <tr>
            <th width="400"><spring:message code="dataintegrityworkflow.stage"/></th>
            <td><c:out value="${record.currentAssignee.currentIntegrityRecordAssignment.currentStage.status} "/> </td>
        </tr>
        <tr>
            <th width="400"><spring:message code="dataintegrityworkflow.assigneeBy"/></th>
            <td><c:out value="${record.currentAssignee.currentIntegrityRecordAssignment.assignBy.personName} "/> </td>
        </tr>
        <tr>
            <th width="300"><spring:message code="dataintegrityworkflow.dateOfAssign"/></th>
            <td><openmrs:formatDate date="${record.currentAssignee.currentIntegrityRecordAssignment.assignedDate}" type="long"/></td>
        </tr>
        <tr>
            <th width="400"><spring:message code="dataintegrityworkflow.checkName"/></th>
            <td>
                <c:out value="${check.name}"/>
            </td>
        </tr>
        <tr>
            <th width="400"><spring:message code="dataintegrityworkflow.checkDescription"/></th>
            <td>
                <c:out value="${check.description}"/>
            </td>
        </tr>
        <tr>
            <th width="300"><spring:message code="dataintegrityworkflow.lastupdated"/></th>
            <td><openmrs:formatDate date="${record.lastUpdated}" type="long"/>
            </td>
        </tr>
    </table>
</div>

<b class="boxHeader"><spring:message code="dataintegrityworkflow.record.data"/></b>
<div class="box" style="text-align: center;">
    <table id="table7">
        <c:forEach items="${check.resultsColumns}" var="column" >
            <th><c:out value="${column.displayName}"/></th>
        </c:forEach>
        <tbody>
        <tr>
            <c:forEach items="${check.resultsColumns}" var="column" >
                <td>
                    <script>
                        document.write(renderCell('${record.integrityCheckResult.data[column.name]}','${column.datatype}'));
                    </script>
                </td>
            </c:forEach>
        </tr>
        </tbody>
    </table>
</div>

<b class="boxHeader"><spring:message code="dataintegrityworkflow.record.comments"/></b>
<div class="box" >
    <c:forEach items="${comments}" var="commentObj" >
        <c:out value="${commentObj.comment}"/>
        <div class="description">
            <c:out value="${commentObj.creator.personName}"/>
            <kc:prettyTime date="${commentObj.dateCreated}"></kc:prettyTime>
        </div>
    </c:forEach>
</div>

<openmrs:hasPrivilege privilege="View Record Assignments">
    <b class="boxHeader"><spring:message code="dataintegrityworkflow.record.status.manage"/></b>
    <div class="box" >
        <table id="table8">
            <tr>
                <th width="400"><spring:message code="dataintegrityworkflow.status"/> </th>
                <form method="post">
                    <td>
                        <input type=hidden name=recordId value=<c:out value="${recordId}"/> >
                        <input type=hidden name=checkId value=<c:out value="${checkId}"/> >
                        <input type=hidden name=resultId value=<c:out value="${record.integrityCheckResult.integrityCheckResultId}"/> >
                        <select name="status">
                            <c:forEach items="${status}" var="statusObj" >
                                <option value="<c:out value="${statusObj.statusId}"/>"> <c:out value="${statusObj.status}"/> </option>
                            </c:forEach>
                            <option value="-" selected="selected">-</option>
                        </select>
                        <input type="submit" name="statusStage" value="<spring:message code="dataintegrityworkflow.record.change" />" />
                </form>
            </tr>
        </table>
    </div>
</openmrs:hasPrivilege>


<openmrs:hasPrivilege privilege="Manage Record Assignees">
    <b class="boxHeader"><spring:message code="dataintegrityworkflow.record.assignments"/></b>
    <div class="box" >
        <table id="table4">
            <c:if test="${not empty record.currentAssignee.assignee}">
                <tr>
                    <th width="400"><spring:message code="dataintegrityworkflow.assignee"/> </th>
                    <form method="post">
                        <td>
                            <input type=hidden name=recordId value=<c:out value="${recordId}"/> >
                            <input type=hidden name=checkId value=<c:out value="${checkId}"/> >
                            <input type=hidden name=resultId value=<c:out value="${record.integrityCheckResult.integrityCheckResultId}"/> >
                            <c:out value="${record.currentAssignee.assignee}"/>
                        </td>
                        <td>
                            <input type="submit" id="delAssigned" name="delAssigned" value="<spring:message code="dataintegrityworkflow.assignee.remove" />" />
                        </td>
                    </form>
                </tr>
                <th width="400"><spring:message code="dataintegrityworkflow.stage"/></th>

                <form method="post">
                    <input type=hidden name=recordId value=<c:out value="${recordId}"/> >
                    <input type=hidden name=checkId value=<c:out value="${checkId}"/> >
                    <input type=hidden name=resultId value=<c:out value="${record.integrityCheckResult.integrityCheckResultId}"/> >
                    <td>
                        <select name="stage">
                            <c:forEach items="${stages}" var="stageObj" >
                                <option value="<c:out value="${stageObj.workflowStageId}"/>"> <c:out value="${stageObj.status}"/> </option>
                            </c:forEach>
                            <option value="-" selected="selected">-</option>
                        </select>
                    </td>
                    <td>
                        <input type="submit" name="changeStage" value="<spring:message code="dataintegrityworkflow.record.change" />" />
                    </td>
                </form>
            </c:if>
            <tr>
                <th width="400"><spring:message code="dataintegrityworkflow.assignee.change"/> </th>
                <form method="post">
                    <input type=hidden name=recordId value=<c:out value="${recordId}"/> >
                    <input type=hidden name=checkId value=<c:out value="${checkId}"/> >
                    <input type=hidden name=resultId value=<c:out value="${record.integrityCheckResult.integrityCheckResultId}"/> >
                    <td>
                        <input id="assigneeId" name="assigneeId" />
                    </td>
                    <td>
                        <input type="submit" id="chnageAssigned" name="changeAssigned" value="<spring:message code="dataintegrityworkflow.assignee.changeValue" />" onClick="return checkAssignee()"/>
                    </td>
                </form>
            </tr>
        </table>
    </div>
</openmrs:hasPrivilege>

<b class="boxHeader"><spring:message code="dataintegrityworkflow.record.add.comment"/></b>
<div class="box" >
    <table id="table6">
        <form method="post">
            <tr>
                <th width="400" valign="top"><spring:message code="dataintegrityworkflow.record.comment"/> </th>
                <td><textarea name="comment" rows="10" cols="120" type="_moz" size="35"></textarea> </td>
            </tr>
            <td>
            </td>
            <td>
                <input type=hidden name=recordId value=<c:out value="${recordId}"/> >
                <input type=hidden name=checkId value=<c:out value="${checkId}"/> >
                <input type=hidden name=resultId value=<c:out value="${record.integrityCheckResult.integrityCheckResultId}"/> >
                <input type="submit" name="addComment" value="<spring:message code="dataintegrityworkflow.record.comment.add"/>" />
        </form>
    </table>
</div>
</openmrs:hasPrivilege>

<%@ include file="/WEB-INF/template/footer.jsp" %>