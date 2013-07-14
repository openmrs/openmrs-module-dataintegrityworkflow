<%@ include file="localHeader.jsp"%>

<openmrs:require privilege="Manage Record Assignees" otherwise="/login.htm" redirect="/module/dataintegrityworkflow/viewChecks.form" />


<openmrs:htmlInclude file="/moduleResources/dataintegrityworkflow/demo_table_jui.css" />
<openmrs:htmlInclude file="/moduleResources/dataintegrityworkflow/jquery.dataTables.min.js" />
<openmrs:htmlInclude file="/moduleResources/dataintegrityworkflow/css/module.css"/>
<openmrs:htmlInclude file="/moduleResources/dataintegrityworkflow/js/module.js"/>
<style>
    #tabs-nohdr {
        padding: 0px;
        background: none;
        border-width: 0px;
    }
    #tabs-nohdr .ui-tabs-nav {
        padding-left: 0px;
        background: transparent;
        border-width: 0px 0px 1px 0px;
        -moz-border-radius: 0px;
        -webkit-border-radius: 0px;
        border-radius: 0px;
    }
    #tabs-nohdr .ui-tabs-panel {
        background: #d8d8d6 url(http://code.jquery.com/ui/1.8.23/themes/south-street/images/ui-bg_highlight-hard_100_f5f3e5_1x100.png) repeat-x scroll 50% top;
        border-width: 0px 1px 1px 1px;
    }
    /*Model Dialog properties*/
    input.text { margin-bottom:12px; width:95%; padding: .4em; }
    .validateTips { border: 1px solid transparent; padding: 0.3em; }
    .ui-dialog .ui-state-error { padding: .3em; }
    .uiButtonM {
        background: #e8e8e6;
        border: 1px solid #d8d8d6;
        color: #9e9e9c;
    }

    form {display: inline; }
</style>
<script>
    var $j = jQuery.noConflict();
    $j(function() {
        var availableTags = ["default"
                <c:forEach items="${users}" var="user">
                ,"${user}"
                </c:forEach>
        ];
        $j("#assigneeId" ).autocomplete({
            source: availableTags
        });
    });
    $j(function() {
        $j( "#tabs" ).tabs();
    });
    $j(document).ready(function() {
        $j('#table').dataTable( {
            "bFilter": true,
            "iDisplayLength": 15,
            "bProcessing": true,
            "bJQueryUI": true,
            "sPaginationType": "full_numbers",
            "aaSorting": [[ 7, "desc" ]],
            "aoColumns": [
                { "bSearchable": true,
                    "bVisible":    true },
                { "bSearchable": true,
                    "bVisible":    true },
                { "bSearchable": true,
                    "bVisible":    true },
                { "bSearchable": true,
                    "bVisible":    true },
                { "bSearchable": true,
                    "bVisible":    true },
                { "bSearchable": true,
                    "bVisible":    true },
                { "bSearchable": false,
                    "bVisible":    true }
            ]

        } );
        //$j('#submitAssignee').hide();
        $j("#checkboxColumn").find('span').removeClass('ui-icon');
        $j("#removeOpt option:first").attr('selected','selected');
        $j("#assignmentOpt option:first").attr('selected','selected');
        $j("#selectUsr option:first").attr('selected','selected');

        $j('#selectAllCheckBox').click(function() {
            if(this.checked) {
                // Iterate each checkbox
                $j(':checkbox').each(function() {
                    this.checked = true;
                });
            }else {
                $j(':checkbox').each(function() {
                    this.checked = false;
                });
            }
        });

        $j("#unassignButton").mouseover(function() {
            $j(this).removeClass('ui-state-default').addClass('ui-state-hover');
        });

        $j("#unassignButton").mouseout(function() {
            $j(this).removeClass('ui-state-hover').addClass('ui-state-default');
        });

    } );

    function showDiv(action)
    {
        $j('#assignDiv').slideToggle();
    }

    function checkForAssignAssignees()
    {
        var assigned;
        if($j('#formContent input[type=checkbox]:checked').length==0)
        {
            /*var count=0;
            $j('tr .checkboxRow:checked').each(function() {
                assigned=$j(this).closest('tr').children("td:nth-child(4)").text();
                assigned=assigned.trim();
                if(assigned!="")
                {
                 count++;
                }
            });
            if(count>0) {
                return confirmPopUpBox("Some selected records already assigned.Do you need to continue?");
            }*/
            return false;
        }
        return true;
    }

    function checkForRemoveAssignees()
    {
        var assigned;
        if($j('#formContent input[type=checkbox]:checked').length>0)
        {
            var  count=0;
            $j('tr .checkboxRow:checked').each(function() {
                assigned=$j(this).closest('tr').children("td:nth-child(4)").text();
                assigned=assigned.trim();
                if(assigned=="")
                {
                    count++;
                }
            });
            if(count>0) {
                alert("Please select assigned records only");
                return false;
            }
        } else {
            alert("No records selected");
            return false;
        }
        return true;
    }

    function confirmPopUpBox(msg)
    {
        var conf = confirm(msg);
        return conf;
    }

    //Model Dialog properties
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

    $j( "#dialog-form" ).dialog({
        autoOpen: false,
        height: 250,
        width: 350,
        modal: true,
        buttons: {
            "Assign": function() {
                if(checkForAssignAssignees()){
                var assigneeId=$j('#assigneeId').val();
                var selection=$j('#assignmentOpt').val();
                if(assigneeId!=""){
                var input1 = $j("<input>").attr("type", "hidden").attr("name", "operation").val("assign");
                var input2 = $j("<input>").attr("type", "hidden").attr("name", "assigneeId").val(assigneeId);
                var input3 = $j("<input>").attr("type", "hidden").attr("name", "assignmentOptions").val(selection);
                $j('form#workflowRecords').append($j(input1));
                $j('form#workflowRecords').append($j(input2));
                $j('form#workflowRecords').append($j(input3));
                $j('form#workflowRecords').submit();
                $j( this ).dialog( "close" );
                } else {
                    updateTips("Please enter a assignee");
                }}else {
                    updateTips("No records selected")
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
    $j("#assignDialog")
            .button()
            .click(function() {
                $j( "#dialog-form" ).dialog( "open" );
            });
    });
</script>
<h2><c:out value="${check.name}"/>-<spring:message code="dataintegrityworkflow.record.list"/></h2>

<div id="tabs">
    <ul>
        <li><a href="#integrityResultsTab">Records</a></li>
        <li><a href="#checkSummaryTab">Check Summary</a></li>
    </ul>
    <div id="integrityResultsTab">
        <form id="workflowRecords" method="post">
            <input type="hidden" name="checkId" value="${check.id}"/>
            <input type="hidden" name="filter" value="${filter}"/>
            <table cellspacing="0" cellpadding="2" id="table" class="display">
                <thead>
                <tr>
                    <th width="50" id="checkboxColumn"><input type="checkbox" id="selectAllCheckBox" value="0"/></th>
                    <th width="200"><spring:message code="dataintegrityworkflow.recordId"/></th>
                    <th width="300"><spring:message code="dataintegrityworkflow.status"/></th>
                    <th width="300"><spring:message code="dataintegrityworkflow.assignee"/></th>
                    <th width="300"><spring:message code="dataintegrityworkflow.stage"/></th>
                    <th width="300"><spring:message code="dataintegrityworkflow.lastupdated"/></th>
                    <th width="300"><spring:message code="dataintegrityworkflow.summary"/></th>
                </tr>
                </thead>
                <tbody id="formContent">
                <c:forEach items="${records}" var="record" varStatus="loopStatus">
                    <tr class="row" id="${record.integrityCheckResult.integrityCheckResultId}">
                        <td>
                            <input type="checkbox" size="3" name="recordId" class="checkboxRow" value="${record.integrityCheckResult.integrityCheckResultId}" />
                        </td>
                        <td><a href="<openmrs:contextPath/>/module/dataintegrityworkflow/viewRecord.form?resultId=<c:out value="${record.integrityCheckResult.integrityCheckResultId}"/>&checkId=<c:out value="${check.id}"/>">RECORD-<c:out value="${record.integrityCheckResult.integrityCheckResultId}"/></a> </td>
                        <td class="status">
                            <c:choose><c:when test="${not empty record.integrityWorkflowRecord}">
                                <c:out value="${record.integrityWorkflowRecord.integrityCheckResult.status}" />
                            </c:when>
                                <c:otherwise>
                                    <c:out value="${record.integrityCheckResult.status}"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:if test="${not empty record.integrityWorkflowRecord.currentAssignee}">
                                <c:out value="${record.integrityWorkflowRecord.currentAssignee.assignee.username}"/>
                                <span><a href="#" onclick=""></a></span>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${not empty record.integrityWorkflowRecord.currentAssignee}">
                                <c:out value="${record.integrityWorkflowRecord.currentAssignee.currentIntegrityRecordAssignment.currentStage.status}"/>
                            </c:if>
                        </td>
                        <td>
                            <c:choose><c:when test="${not empty record.integrityWorkflowRecord.lastUpdated}">
                                <openmrs:formatDate date="${record.integrityWorkflowRecord.lastUpdated}" type="long"/>
                            </c:when>
                                <c:otherwise>
                                    <openmrs:formatDate date="${record.integrityCheckResult.lastSeen.dateCreated}" type="long"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="<openmrs:contextPath/>/module/dataintegrityworkflow/viewRecordSummary.form?resultId=<c:out value="${record.integrityCheckResult.integrityCheckResultId}"/>&checkId=<c:out value="${check.id}"/>"><spring:message code="dataintegrityworkflow.record.summary"/></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div>
                <table  id="actionRow">
                    <tr>
                        <td id="assign">
                            <button type="button" class="uiButtonM" id="assignDialog"><spring:message code="dataintegrityworkflow.assign"/></button>
                        </td>
                        <td id="remove">
                            <span class="ui-button-text">
                            <input type="submit" id="unassignButton" name="operation" style="height:36px" class="uiButtonM ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" value="<spring:message code="dataintegrityworkflow.records.Unassign"/>" onclick="return checkForRemoveAssignees();"/>
                            </span>
                        </td>
                    </tr>
                </table>
            </div>
            <%--<div id="assignDiv" style="clear: both" class="show_hide slidingDiv">
                <table>
                    <tr>
                        <td>
                            <spring:message code="dataintegrityworkflow.assign"/>
                        </td>
                        <td>
                            <select name="assignmentOptions" id="assignmentOpt">
                                <option value="selected">Selected</option>
                                <option value="all">All</option>
                                &lt;%&ndash; <option value="allAndFuture">All and future</option>&ndash;%&gt;
                            </select>
                        </td>
                        <td>
                            <spring:message code="dataintegrityworkflow.recordsTo"/></span>
                        </td>
                        <td>
                            <select name="selectUser" id="selectUsr">
                                <c:forEach items="${users}" var="user">
                                    <option value="<c:out value="${user}"/>"><c:out value="${user}"/></option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <input type="submit" name="action" value="<spring:message code="dataintegrityworkflow.records.assign"/>" onclick="return checkForAssignAssignees();"/>
                        </td>
                    </tr>
                </table>
            </div>--%>
            <div id="dialog-form" title="Assign Records">
                <p class="validateTips">Select a assignee by typing the username</p>
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
                        <tr>
                            <td>
                                <spring:message code="dataintegrityworkflow.selection"/>
                            </td>
                            <td>
                                <select name="assignmentOptions" id="assignmentOpt">
                                    <option value="selected">Selected</option>
                                    <option value="all">All</option>
                                    <%-- <option value="allAndFuture">All and future</option>--%>
                                </select>
                            </td>
                        </tr>
                    </table>
                </fieldset>
            </div>
        </form>
    </div>
    <div id="checkSummaryTab">
        <b class="boxHeader"><spring:message code="dataintegrityworkflow.check.unresolved.byassignee"/></b>
        <div class="box" >
        <table id="table1">
            <c:forEach items="${stages}" var="stageObj" >
            <tr>
                <c:out value="${stageObj.status}"/>"
            </tr>
            </c:forEach>
        </table>
        </div>
        <b class="boxHeader"><spring:message code="dataintegrityworkflow.check.stage.summary"/></b>
        <div class="box" >
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/template/footer.jsp" %>