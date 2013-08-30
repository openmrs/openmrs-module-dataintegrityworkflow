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
    });

function getStatusById(status) {
    if(status == "0") { return "New"; }
    if(status == "1") { return "Ignored"; }
    if(status == "2") { return "Voided"; }
    return status;
}

} );


