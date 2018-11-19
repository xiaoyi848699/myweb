
jQuery(document).ready(function() {
    var userId =getCookie("userId");
    // alert("userId:"+userId)
    if(null != userId){
        $("#mysendtask").attr("href","getMySendTask?addUserId="+userId);
    }else{
        $("#mysendtask").attr("href","getMySendTask");
    }
});
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg)) {
        return unescape(arr[2])
    } else {
        return null
    }
}