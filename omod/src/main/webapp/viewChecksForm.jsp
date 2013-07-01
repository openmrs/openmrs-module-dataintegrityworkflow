<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="Manage Record Assignees" otherwise="/login.htm" redirect="/module/dataintegrityworkflow/viewChecks.form" />

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="localHeader.jsp" %>

<openmrs:htmlInclude file="/moduleResources/dataintegrityworkflow/css/smoothness/jquery-ui-1.8.16.custom.css" />

<style>
    #integrityCheckTable td { padding: 0.5em; vertical-align: middle; }
    #integrityCheckTable th { padding: 0.5em; }

    td.run { line-height: 2em; }
    td.run .details { white-space: nowrap; }
    td.run .passed { width: 7em; display: inline-block; text-align: center; line-height: 2em; font-weight: bold; text-transform: uppercase; }
    td.run .true { background: #afa; }
    td.run .false { background: #faa; }
    td.run .dateRan { margin-left: 0.5em; white-space: nowrap; }
    td.run .expander { display: none; }

    tr.retired, tr.retired * { text-decoration: inherit; font-style: italic; }
    tr.retired td.action { text-decoration: inherit; font-style: normal; }
</style>

<script>
    $j(document).ready(function(){
        // hide retired checks
        toggleRowVisibilityForClass('integrityCheckTable', 'retired');

        // hide previous results
        $j("td.run .history").hide();

        $j("td.run span.true").html("PASSED");
        $j("td.run span.false").html("FAILED");
    })
</script>

<h2><spring:message code="dataintegrityworkflow.manage.title"/></h2>
<div id="pageBody">
<b class="boxHeader">
	<span style="float: right">
		<a href="#" id="showRetired" onclick="return toggleRowVisibilityForClass('integrityCheckTable', 'retired');">
            <spring:message code="general.toggle.retired"/>
        </a>
	</span>
    <spring:message code="dataintegrityworkflow.check.list.title"/>
</b>
<div class="box">
    <c:if test="${not empty checks}">
        <table id="integrityCheckTable" cellpadding="10" cellspacing="0" >
            <thead>
            <tr>
                <th><spring:message code="dataintegrityworkflow.check.name"/></th>
                <th><spring:message code="dataintegrityworkflow.check.description"/></th>
                <th><spring:message code="dataintegrityworkflow.check.status"/></th>
                <th><spring:message code="dataintegrityworkflow.check.lastRunTimeStamp"/></th>
            </tr>
            </thead>
            <c:forEach items="${checks}" var="check" varStatus="varStatus">
                <tr class="<c:choose><c:when test="${varStatus.index % 2 == 0}">oddRow</c:when><c:otherwise>evenRow</c:otherwise></c:choose> <c:if test="${check.retired}">retired</c:if>">
                    <c:choose><c:when test="${not empty check.integrityCheckRuns}">
                        <td><a href ="<openmrs:contextPath/>/module/dataintegrityworkflow/manageIntegrityRecords.form?filter=all&checkId=<c:out value="${check.id}"/>"><c:out value="${check.name} "/></a></td>
                    </c:when>
                    <c:otherwise>
                        <td><a href ="#"><c:out value="${check.name} "/></a></td>
                    </c:otherwise>
                    </c:choose>
                        <td>${check.description}</td>
                        <c:if test="${not empty check.integrityCheckRuns}">
                            <c:set value="${check.mostRecentRun}" var="run"/>
                            <td class="run"><span class="passed ${run.checkPassed}">${run.checkPassed}</span></td>
                            <td>
                                <span class="dateRan"><openmrs:formatDate date="${run.dateCreated}" type="long"/></span>
                            </td>
                        </c:if>
                        <c:if test="${not empty check.resultsColumns and empty check.integrityCheckRuns}">
                            <td><spring:message code="dataintegrityworkflow.check.no-runs"/></td>
                            <td><spring:message code="dataintegrityworkflow.check.data.notAvailable"/></td>
                        </c:if>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${empty checks}"><spring:message code="dataintegrityworkflow.list.empty"/></c:if>
</div>
</div>
<%@ include file="/WEB-INF/template/footer.jsp" %>