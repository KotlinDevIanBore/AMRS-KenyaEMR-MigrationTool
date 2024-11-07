function loadsites(id){
        var getUrl = window.location;
        var baseurls =  getUrl.origin + '/' +getUrl.pathname.split('/')[1]+'/modules/'+id;
        alert(baseurls);
         $.ajax({
                                url: baseurls,
                                type: 'GET',
                                success: function (result) {
                                const jsonString = JSON.stringify(result, null, 2);

                                    // Set the value of the text area to the JSON string
                                    document.getElementById("txtmodlues").value = jsonString;
                                var x = result;
                                alert(x);
                                 console.log(result);
                                  }
                        });
        }

      $(document).ready(function() {
        $('#locations').select2();
      });