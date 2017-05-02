console.log("In ajax.js...");

$('div.landscape').on('submit','#getsomeipsum', function(event) {
    event.preventDefault();
    var url = $(this).attr('action'); //  + "?" + $(this).serialize();
    console.log("URL="+url);
    console.log("Query params="+$(this).serialize());
    $.ajax({ 
        url: url, 
        data: $(this).serialize(),
        type: 'GET', 
        success: function(data) { 
            $('p.loremtext').remove();
            data.forEach(function (p) {
                console.log(p);
                $('div.vegasipsumtext').append( "<p class=\"loremtext\">" + p + "</p>");
            });
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) { 
            console.log("AJAX GET didn't work!!!:" + 
                    JSON.stringify(XMLHttpRequest,null,'\t'));
            console.log("Status: " + textStatus); 
            console.log("Error: " + errorThrown); 
        } 
    });
});

$('#cb-lorem').click(function(){
    if ($(this).is(':checked') == true) {
        $(this).val(true);
    } else {
        $(this).val(false);
    }
});