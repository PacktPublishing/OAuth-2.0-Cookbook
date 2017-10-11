$(function() {
  var fragment = window.location.hash;
  var parameters = fragment.slice(1).split('&');
  var oauth2Token = {};

  $(parameters).each(function(idx, param) {
    var keyValue = param.split('=');
    oauth2Token[keyValue[0]] = keyValue[1];
  });

  $.ajax({
      url: 'http://localhost:8080/api/profile',
      beforeSend: function(xhr) {
          xhr.setRequestHeader("Authorization", "Bearer " + oauth2Token['access_token']);
      },
      success: function(data){
         $('.name').text(data.name);
         $('.email').text(data.email);

         window.location.replace("#");
      },
      error: function(jqXHR, textStatus, errorThrown)   {
          console.log(textStatus);
      }
  });
});
