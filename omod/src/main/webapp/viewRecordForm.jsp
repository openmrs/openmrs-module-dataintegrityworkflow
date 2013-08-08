<%@ include file="localHeader.jsp"%>

<openmrs:hasPrivilege privilege="View Record Assignments">
<openmrs:htmlInclude file="/moduleResources/dataintegrityworkflow/css/module.css"/>
<openmrs:htmlInclude file="/moduleResources/dataintegrityworkflow/js/module.js"/>
<style>
     /*Model Dialog properties*/
     input.text { margin-bottom:12px; width:95%; padding: .4em; }
    .validateTips { border: 1px solid transparent; padding: 0.3em; }
    .ui-dialog .ui-state-error { padding: .3em; }
/*===========================================================*/
    .uiButtonM {
        background: #e8e8e6;
        border: 1px solid #d8d8d6;
        color: #9e9e9c;
    }
</style>
<script>
    var $j = jQuery.noConflict();
    $j(function() {
        var availableTags = ["default","Unassign"
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

        $j(".hover-on").mouseover(function() {
            $j(this).removeClass('ui-state-default').addClass('ui-state-hover');
        });

        $j(".hover-on").mouseout(function() {
            $j(this).removeClass('ui-state-hover').addClass('ui-state-default');
        });
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

    //Model Dialogs properties
    $j(function() {
        var tips = $j( ".validateTips" );
        function updateTips( t ) {
            tips
                    .text( t )
                    .addClass( "ui-state-highlight" );
            setTimeout(function() {
                tips.removeClass( "ui-state-highlight", 1500 );
            }, 500 );
        }

        $j( "#dialog-form-assignee" ).dialog({
            autoOpen: false,
            height: 200,
            width: 350,
            modal: true,
            buttons: {
                "Assign": function() {
                        var assigneeId=$j('#assigneeId').val();
                        if(assigneeId!=""){
                            $j("#assignee-change").submit();
                            $j( this ).dialog( "close" );
                        } else {
                            updateTips("Please enter a assignee");
                        }
                },
                Cancel: function() {
                    $j( this ).dialog( "close" );
                }
            },
            close: function() {
                $j('#assigneeId').text="";
            }
        });
        $j("#changeAssignDialog")
                .button()
                .click(function() {
                    $j( "#dialog-form-assignee" ).dialog( "open" );
                });
    });

    $j(function() {
        var tips = $j( ".validateTips" );
        function updateTips( t ) {
            tips
                    .text( t )
                    .addClass( "ui-state-highlight" );
            setTimeout(function() {
                tips.removeClass( "ui-state-highlight", 1500 );
            }, 500 );
        }

        $j( "#dialog-form-stage" ).dialog({
            autoOpen: false,
            height: 200,
            width: 350,
            modal: true,
            buttons: {
                "Change": function() {
                        var selection=$j('#stages').val();
                        if(selection!="-"){
                            $j("#stage-change").submit();
                            $j( this ).dialog( "close" );
                        } else {
                            updateTips("Please select a stage");
                        }
                },
                Cancel: function() {
                    $j( this ).dialog( "close" );
                }
            },
            close: function() {
            }
        });

        $j("#changeStageDialog")
                .button()
                .click(function() {
                    $j( "#dialog-form-stage" ).dialog( "open" );
                });
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
    <form method="post">
        <input type=hidden name=recordId value=<c:out value="${recordId}"/> >
        <input type=hidden name=checkId value=<c:out value="${checkId}"/> >
        <input type=hidden name=resultId value=<c:out value="${record.integrityCheckResult.integrityCheckResultId}"/> >
        <table id="table">
            <tr>
                <th width="100"><spring:message code="dataintegrityworkflow.recordId"/></th>
                <td>RECORD-<c:out value="${record.integrityCheckResult.integrityCheckResultId} "/> </td>
            </tr>
            <tr>
                <th width="100"><spring:message code="dataintegrityworkflow.status"/></th>
                <td class="status">
                    <c:out value="${record.integrityCheckResult.status} "/>
                </td>
                <td>
                    <c:choose><c:when test="${record.integrityCheckResult.status!=1}">
                        <input type=hidden name=status value=<c:out value="${record.integrityCheckResult.status}"/> >
                        <input type="submit" id="ignore" class="ui-button ui-widget ui-state-default ui-corner-all hover-on" style="height:20px;width:60px;font-size:10px" name="statusChange" value="<spring:message code="dataintegrityworkflow.record.ignore"/>" />
                    </c:when>
                        <c:otherwise>
                            <input type=hidden name=status value=<c:out value="${record.integrityCheckResult.status}"/> >
                            <input type="submit" id="unignore" class="ui-button ui-widget ui-state-default ui-corner-all hover-on" style="height:20px;width:60px;font-size:10px" name="statusChange" value="<spring:message code="dataintegrityworkflow.record.unignore"/>" />
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <th width="300"><spring:message code="dataintegrityworkflow.assignee"/></th>
                <td><c:out value="${record.currentAssignee.assignee} "/> </td>
                <openmrs:hasPrivilege privilege="Manage Record Assignees">
                <td>
                    <input type="button" style="height:20px; width:60px ;font-size:10px"  id="changeAssignDialog" value="<spring:message code="dataintegrityworkflow.record.change"/>"/>
                </td>
                </openmrs:hasPrivilege>
            </tr>
            <tr>
                <th width="400"><spring:message code="dataintegrityworkflow.stage"/></th>
                <td><c:out value="${record.currentAssignee.currentIntegrityRecordAssignment.currentStage.status} "/> </td>
                <td>
                    <input type="button"  style="height:20px; width:60px ;font-size:10px" id="changeStageDialog" value="<spring:message code="dataintegrityworkflow.record.change"/>"/>
                </td>
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
    </form>
</div>
<br/>
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
<br/>
<b class="boxHeader"><spring:message code="dataintegrityworkflow.record.comments"/></b>
<div class="box" >
    <c:forEach items="${comments}" var="commentObj" >
        <c:out value="${commentObj.comment}"/>
        <div class="description">
            <c:out value="${commentObj.creator.personName}"/>
            <kc:prettyTime date="${commentObj.dateCreated}"></kc:prettyTime>
        </div>
    </c:forEach>
    <br/>
    <table id="table9">
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
                <input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all hover-on" style="height:20px; font-size:10px" id="addComment" name="addComment" value="<spring:message code="dataintegrityworkflow.record.comment"/>" />
        </form>
    </table>
</div>

<%--<openmrs:hasPrivilege privilege="Manage Record Assignees">
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
</openmrs:hasPrivilege>--%>
<div id="dialog-form-assignee" title="Assign Record">
    <p class="validateTips">Select a assignee by typing the username</p>
    <form id="assignee-change" method="post">
        <input type=hidden name=recordId value=<c:out value="${recordId}"/> >
        <input type=hidden name=checkId value=<c:out value="${checkId}"/> >
        <input type=hidden name=resultId value=<c:out value="${record.integrityCheckResult.integrityCheckResultId}"/> >
        <input type="hidden" name="changeAssigned" value="changeAssigned"/>
    <fieldset>
        <table>
            <tr>
                <td>
                    <spring:message code="dataintegrityworkflow.user"/></span>
                </td>
                <td>
                    <input id="assigneeId" />
                </td>
            </tr>
        </table>
    </fieldset>
    </form>
</div>

<div id="dialog-form-stage" title="Change Stage">
    <p class="validateTips">Select a stage</p>
    <form id="stage-change" method="post">
        <input type=hidden name=recordId value=<c:out value="${recordId}"/> >
        <input type=hidden name=checkId value=<c:out value="${checkId}"/> >
        <input type=hidden name=resultId value=<c:out value="${record.integrityCheckResult.integrityCheckResultId}"/> >
        <input type="hidden" name="changeStage" value="changeStage"/>
        <fieldset>
        <table>
            <tr>
                <td>
                    <spring:message code="dataintegrityworkflow.stage"/></span>
                </td>
                <td>
                    <select name="stage" id="stages">
                        <c:forEach items="${stages}" var="stageObj" >
                            <option value="<c:out value="${stageObj.workflowStageId}"/>"> <c:out value="${stageObj.status}"/> </option>
                        </c:forEach>
                        <option value="-" selected="selected">-</option>
                    </select>
                </td>
            </tr>
        </table>
    </fieldset>
    </form>
</div>
</openmrs:hasPrivilege>

<%@ include file="/WEB-INF/template/footer.jsp" %>