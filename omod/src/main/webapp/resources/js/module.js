var $j = jQuery.noConflict();

$j(document).ready(function() {
    $j('.status').each(function() {
        var status;
        status=getStatusById(this.innerHTML.trim());
        this.innerHTML=status;
        $j('.2').each(function() {
            $j(this).hide();
        });
        $j('.1').each(function() {
            $j(this).hide();
        });
    });



    $j('#viewVoided').click(function() {
        if(this.checked) {
            $j('.2').each(function() {
                $j(this).show();
            });
        }else {
            $j('.2').each(function() {
                $j(this).hide();
            });
        }
        var resultTable = $j("#table").dataTable();
        resultTable.fnStandingRedraw();
    });

    $j('#viewIgnored').click(function() {
        if(this.checked) {
            $j('.1').each(function() {
                $j(this).show();
            });
        }else {
            $j('.1').each(function() {
                $j(this).hide();
            });
        }
        var resultTable = $j("#table").dataTable();
        resultTable.fnStandingRedraw();
    });

    function getStatusById(status) {
        if(status == "0") { return "New"; }
        if(status == "1") { return "Ignored"; }
        if(status == "2") { return "Voided"; }
        return status;
    }

} );

jQuery.fn.dataTableExt.oApi.fnStandingRedraw = function(oSettings) {
    //redraw to account for filtering and sorting
    // concept here is that (for client side) there is a row got inserted at the end (for an add)
    // or when a record was modified it could be in the middle of the table
    // that is probably not supposed to be there - due to filtering / sorting
    // so we need to re process filtering and sorting
    // BUT - if it is server side - then this should be handled by the server - so skip this step
    if(oSettings.oFeatures.bServerSide === false){
        var before = oSettings._iDisplayStart;
        oSettings.oApi._fnReDraw(oSettings);
        //iDisplayStart has been reset to zero - so lets change it back
        oSettings._iDisplayStart = before;
        oSettings.oApi._fnCalculateEnd(oSettings);
    }

    //draw the 'current' page
    oSettings.oApi._fnDraw(oSettings);
};



