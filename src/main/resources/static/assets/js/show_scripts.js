
jQuery(document).ready(function() {


    var userId =getCookie("userId");
    if(null == userId){
        alert('登录过期，请从新登录！');
        window.location.href = "";
        return
    }

    setTimeout(function(){document.getElementById("hint").style.display="none";},3000);
});
