<%@ include file="localHeader.jsp"%>

<openmrs:hasPrivilege privilege="Manage Record Assignee">

    <script>
        $j(document).ready(function() {
            $j('.toggleAddTag').click(function(event) {
                $j('#addTag').slideToggle('fast');
                event.preventDefault();
            });
        });
    </script>

    <h2><spring:message code="dataintegrityworkflow.workflow.stages"/></h2>
    <a class="toggleAddTag" href="#"><spring:message code="dataintegrityworkflow.add.workflow.stage"/></a>
    <div id="addTag" style="border: 1px black solid; background-color: #e0e0e0; display: none">
        <form method="post">
            <table>
                <tr>
                    <td>
                        <spring:message code="dataintegrityworkflow.workflowStage.name"/>:
                    </td>
                    <td>
                        <input type="text" name="status" size="50"  value="" />
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input type="submit" value="<spring:message code="general.save"/>" /> <input type="button" value="Cancel" class="toggleAddTag" />
                    </td>
                </tr>
            </table>

        </form>
    </div>
    <br/>
    <br/>

    <b class="boxHeader"><spring:message code="dataintegrityworkflow.available.workflow.stages"/></b>
    <div class="box" >
        <table>
            <tr>
                <th width="400"><spring:message code="dataintegrityworkflow.workflow.stage"/></th>
            </tr>
            <c:forEach items="${stages}" var="stage"  varStatus="loopStatus">
                <tr class="${loopStatus.index % 2 == 0 ? 'evenRow' : 'oddRow'}">
                    <td><a href="<openmrs:contextPath/>/module/dataintegrityworkflow/workflowStage.form?workflowId=<c:out value="${stage.workflowStageId}"/>"><c:out value="${stage.status}"/></a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
    </br>
    <b class="boxHeader"><spring:message code="dataintegrityworkflow.help"/></b>
    <div class="box">
        <ul>
            <li><i><spring:message code="dataintegrityworkflow.workflow.help.l1"/></i></li>
        </ul>
    </div>
</openmrs:hasPrivilege>

<%@ include file="/WEB-INF/template/footer.jsp" %>