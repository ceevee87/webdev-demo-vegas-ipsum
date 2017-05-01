console.log("In ajax.js...");

$('button#getsomeipsum').click(function(event) {
    $.ajax({ 
        url: 'http://localhost:8080/VegasIpsum/api/', 
        contentType : 'text/plain; charset=utf-8',
        type: 'GET', 
        success: function(data) { 
            $('p.loremtext').remove();
            data.forEach(function (p) {
                // console.log(p);
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
