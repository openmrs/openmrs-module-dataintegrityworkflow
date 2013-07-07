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
    });
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
            <td><c:out value="${record.integrityCheckResult.status} "/> </td>
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

<openmrs:hasPrivilege privilege="Manage Record Assignees">
    <b class="boxHeader"><spring:message code="dataintegrityworkflow.record.assignments"/></b>
    <div class="box" >
        <table id="table4">
            <tr>
                <th width="400"><spring:message code="dataintegrityworkflow.assignee"/> </th>
                <form method="post">
                    <td>
                        <input type=hidden name=recordId value=<c:out value="${recordId}"/> >
                        <input type=hidden name=checkId value=<c:out value="${checkId}"/> >
                        <input type=hidden name=resultId value=<c:out value="${resultId}"/> >
                        <div id="assignee">
                            <c:out value="${record.currentAssignee.assignee}"/>
                        </div>
                    </td>
                    <td>
                        <input type="submit" id="delAssigned" name="delAssigned" value="<spring:message code="dataintegrityworkflow.assignee.remove" />" />
                    </td>
                </form>
            </tr>
            <tr>
                <th width="400"><spring:message code="dataintegrityworkflow.assignee.change"/> </th>
                <form method="post">
                    <td>
                        <input type=hidden name=recordId value=<c:out value="${recordId}"/> >
                        <input type=hidden name=checkId value=<c:out value="${checkId}"/> >
                        <input type=hidden name=resultId value=<c:out value="${resultId}"/> >
                        <input id="assigneeId" />
                    </td>
                    <td>
                        <input type="submit" id="chnageAssigned" name="changeAssigned" value="<spring:message code="dataintegrityworkflow.assignee.changeValue" />" onClick="return checkAssignee()"/>
                    </td>
                </form>
            </tr>
        </table>
    </div>
</openmrs:hasPrivilege>

<openmrs:hasPrivilege privilege="View Record Assignments">
    <b class="boxHeader"><spring:message code="dataintegrityworkflow.record.stage.manage"/></b>
    <div class="box" >
        <table id="table5">
            <tr>
                <th width="400"><spring:message code="dataintegrityworkflow.stage"/></th>
                <td>
                    <form method="post">
                        <input type=hidden name=recordId value=<c:out value="${recordId}"/> >
                        <input type=hidden name=checkId value=<c:out value="${checkId}"/> >
                        <input type=hidden name=resultId value=<c:out value="${resultId}"/> >
                        <select name="status">
                            <c:forEach items="${stages}" var="stageObj" >
                                <option value="<c:out value="${stageObj.status}"/>"> <c:out value="${stageObj.status}"/> </option>
                            </c:forEach>
                            <option value="-" selected="selected">-</option>
                        </select>
                        <input type="submit" name="changeStage" value="<spring:message code="dataintegrityworkflow.record.stage.change" />" />
                    </form>
                </td>
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
                <input type=hidden name=resultId value=<c:out value="${resultId}"/> >
                <input type="submit" name="addComment" value="<spring:message code="dataintegrityworkflow.record.comment.add"/>" />
        </form>
    </table>
</div>
</openmrs:hasPrivilege>

<%@ include file="/WEB-INF/template/footer.jsp" %>