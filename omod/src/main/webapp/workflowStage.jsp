<%@ include file="localHeader.jsp"%>

<openmrs:hasPrivilege privilege="Manage Record Assignee">
    <h2><spring:message code="dataintegrityworkflow.edit.workflowStage"/></h2>
    <b class="boxHeader"><spring:message code="dataintegrityworkflow.edit.workflowStage"/></b>
    <div class="box" >
        <c:if test="${not empty stage}">
            <form method="post">
                <input type=hidden name=workflowStageId value= <c:out value="${stage.workflowStageId}"/> >
                <table>
                    <tr>
                        <td width="200"><spring:message code="dataintegrityworkflow.workflowStage.name"/></td>
                        <td><input type="text" name="status" value="${stage.status}"  size="50" /></td>
                    </tr>
                    <tr>
                        <td>
                            <input type=hidden name=save value="save">
                            <input type="submit" value="<spring:message code="general.save"/>" />
                        </td>
                    </tr>
                </table>
            </form>
        </c:if>
    </div>
    </br>
</openmrs:hasPrivilege>

<%@ include file="/WEB-INF/template/footer.jsp" %>