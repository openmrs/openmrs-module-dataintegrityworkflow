var $j = jQuery.noConflict();

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

    $j("#checkboxColumn").find('span').removeClass('ui-icon');

    $j('.status').each(function() {
        this.innerHTML=renderStatus(this.innerHTML);
    });

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

} );


function renderStatus(status) {
    if(status == "0") { return "New"; }
    if(status == "1") { return "Ignored"; }
    if(status == "2") { return "Voided"; }
    return "Other";
}

function showDiv(action)
{
    if('assign'==action) {
    $j('#removeDiv').hide('slow');
    $j('#assignDiv').slideToggle();
    }
    else {
        $j('#assignDiv').hide('slow');
        $j('#removeDiv').slideToggle();
    }
}

function checkForAssignAssignees()
{
    var assigned;
    if($j('#formContent input[type=checkbox]:checked').length>0)
    {
    $j('tr .checkboxRow:checked').each(function() {
        assigned=$j(this).closest('tr').children("td:nth-child(4)").text();
        assigned=assigned.trim();
        if(assigned!="")
        {
         return confirmPopUpBox("Some selected records already assigned.Do you need to continue?")
        }
    });
    } else {
        alert("No records selected")
        return false;
    }
    return true;
}

function checkForRemoveAssignees()
{
    var assigned;
    if($j('#formContent input[type=checkbox]:checked').length>0)
    {
    $j('tr .checkboxRow:checked').each(function() {
        assigned=$j(this).closest('tr').children("td:nth-child(4)").text();
        assigned=assigned.trim();
           if(assigned=="")
           {
               alert("Some records doesn't assign.Please select assigned records only");
               return false;
           }
    });
    } else {
        alert("No records selected")
        return false;
    }
    return true;
}

function confirmPopUpBox(msg)
{
    var conf = confirm(msg);
    return conf;
}



