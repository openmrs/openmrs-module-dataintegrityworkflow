var $j = jQuery.noConflict();
$j(document).ready(function() {
$j('.status').each(function() {
    this.innerHTML=renderStatus(this.innerHTML);
});

function renderStatus(status) {
    if(status == "0") { return "New"; }
    if(status == "1") { return "Ignored"; }
    if(status == "2") { return "Voided"; }
    return "Other";
}
} );


