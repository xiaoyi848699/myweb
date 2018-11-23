
jQuery(document).ready(function() {
    // var userId =getCookie("userId");
    // alert("userId:"+userId)
    // if(null != userId){
    //     $("#mysendtask").attr("href","getMySendTask?status=0&addUserId="+userId);
    //     $("#dealtask").attr("href","getMyTaskUserTaskList?status=0&uid="+userId);
    //     $("#recommendlist").attr("href","getMyAllUserList?status=0&addUserId="+userId);
    // }else{
    //     $("#mysendtask").attr("href","getMySendTask");
    // }
});
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg)) {
        return unescape(arr[2])
    } else {
        return null
    }
}
function getMunePage(val) {
    var userId =getCookie("adUserId");
    if(null == userId){
        alert('登录过期，请从新登录！');
        window.top.location = "hello";
        return
    }else{
        switch (val) {
            case 0:
                window.open("getMySendTask?status=0&addUserId="+userId,"main");
                break
            case 1:
                window.open("getMyTaskUserTaskList?status=0&uid="+userId,"main");
                break
            case 2:
                window.open("getMyAllUserList?status=0&addUserId="+userId,"main");
                break
            case 99:
                clearCookie("userId", "", -1);
                window.open("hello","_top");
                break
        }
    }
}
function clearCookie(name, value, time) {
    var exp = new Date();
    exp.setTime(exp.getTime() + time * 1);
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString()
}