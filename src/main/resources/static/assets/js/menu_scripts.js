
jQuery(document).ready(function() {
    var userId =getCookie("userId");
    // alert("userId:"+userId)
    if(null != userId){
        $("#mysendtask").attr("href","getMySendTask?status = 1 &addUserId="+userId);
        $("#dealtask").attr("href","getMyTaskUserTaskList?status = 2 & uid="+userId);
        $("#recommendlist").attr("href","getMyAllUserList?status = 2 &uid="+userId);
    // }else{
    //     $("#mysendtask").attr("href","getMySendTask");
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