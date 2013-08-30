<%@ include file="localHeader.jsp"%>

<openmrs:hasPrivilege privilege="Manage Record Assignee">
    <h2><spring:message code="dataintegrityworkflow.change.key"/></h2>
    <b class="boxHeader"><spring:message code="dataintegrityworkflow.check"/></b>
    <div class="box" >
        <c:if test="${not empty checkkey}">
            <form method="post">
                <input type=hidden name=checkId value= <c:out value="${checkkey.integrityCheck.id}"/> >
                <table>
                    <tr>
                        <td width="200"><spring:message code="dataintegrityworkflow.check.name"/></td>
                        <td> <c:out value="${checkkey.integrityCheck.name}"/></td>
                    </tr>
                    <tr>
                        <td width="200"><spring:message code="dataintegrityworkflow.check.key"/></td>
                        <td><input type="text" size="12" name="keyVal" value="${checkkey.keyVal}"  size="50" /></td>
                    </tr>
                    <tr>
                        <td> </td>
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