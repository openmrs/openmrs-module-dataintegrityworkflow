<%@ include file="localHeader.jsp"%>

<openmrs:hasPrivilege privilege="Manage Record Assignee">
    <h2><spring:message code="dataintegrityworkflow.key.list"/></h2>
    <b class="boxHeader"><spring:message code="dataintegrityworkflow.check.list"/></b>
    <div class="box" >
                <table>
                    <thead>
                        <th width="200"><spring:message code="dataintegrityworkflow.check.key"/></th>
                        <th width="200"><spring:message code="dataintegrityworkflow.checkkey.name"/></th>
                    </thead>
                    <tbody>
                    <c:forEach items="${checkwithkeys}" var="check" varStatus="varStatus">
                    <tr class="${varStatus.index % 2 == 0 ? 'evenRow' : 'oddRow'}">
                        <td>
                           <a href ="<openmrs:contextPath/>/module/dataintegrityworkflow/changeCheckKey.form?checkId=<c:out value="${check.integrityCheck.id}"/>"><c:out value="${check.integrityCheckKey.keyVal}"/></a>
                        </td>
                        <td>
                            <c:out value="${check.integrityCheck.name}"/>
                        </td>
                    </tr>
                    </c:forEach>
                    </tbody>
                </table>
    </div>
    </br>
</openmrs:hasPrivilege>

<%@ include file="/WEB-INF/template/footer.jsp" %>