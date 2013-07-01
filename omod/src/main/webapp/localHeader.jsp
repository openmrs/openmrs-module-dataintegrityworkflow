<%@ taglib prefix="fn" uri="/WEB-INF/taglibs/fn.tld" %>
<ul id="menu">
	<li class="first">
		<a href="${pageContext.request.contextPath}/admin"><spring:message code="admin.title.short"/></a>
	</li>
	<openmrs:hasPrivilege privilege="Manage Record Assignees">
		<li <c:if test='<%= request.getRequestURI().contains("viewChecks") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/dataintegrityworkflow/viewChecks.form">
				<spring:message code="dataintegrityworkflow.manage.link"/>
			</a>
		</li>
        <li <c:if test='<%= request.getRequestURI().contains("addWorkflowStage") %>'>class="active"</c:if>>
        <a href="${pageContext.request.contextPath}/module/dataintegrityworkflow/addWorkflowStage.form">
            <spring:message code="dataintegrityworkflow.stage.link"/>
        </a>
        </li>
	</openmrs:hasPrivilege>
	<openmrs:hasPrivilege privilege="View Record Assignments">
		<li <c:if test='<%= request.getRequestURI().contains("viewAssignedRecords") %>'>class="active"</c:if>>
            <a href="${pageContext.request.contextPath}/module/dataintegrityworkflow/viewAssignedRecords.form">
                <spring:message code="dataintegrityworkflow.view.link"/>
            </a>
        </li>
	</openmrs:hasPrivilege>
</ul>