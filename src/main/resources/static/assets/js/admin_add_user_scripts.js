
jQuery(document).ready(function() {

    $('.commit_task').click(function(){
        var userId =getCookie("adUserId");
        var username=$('#username').val();
        var hint = document.getElementById("hint_self");
        if(userId == null){
            alert('登录过期，请从新登录！');
            window.top.location = "";
        }else if(username.length<1){
            hint.innerHTML = "请输入推荐账号";
            hint.style.display = 'block';
            setTimeout(function(){document.getElementById("hint_self").style.display="none";},3000);
        }else{
            document.getElementById("form_add").action="addUser?addUserId="+userId;
            document.getElementById("form_add").submit();
        }
    });
    var userId =getCookie("adUserId");
    if(null != userId){
        $("#filter").attr("action","getMyAllUserList?addUserId="+userId);
    }
    setTimeout(function(){ var hint = document.getElementById("hint");
        if(null != hint){
            hint.style.display="none";
        }},3000);
});
function filterUser(val) {
    var userId = getCookie("adUserId");
    if (null != userId) {
        document.getElementById("form_filter").action = "getMyAllUserList?addUserId=" + userId + "&status=" + val;
        document.getElementById("form_filter").submit();
    } else {
        alert('登录过期，请从新登录！');
        // window.top.location = "hello";
        window.open("","_top");
    }
}
function operateUser(_this,status) {
    var userId = getCookie("adUserId");
    var value = _this.getAttribute("value");
    if (null != userId) {
        window.location.href="changeUserStatus?status="+status+"&id="+value+"&operateId="+userId
    } else {
        alert('登录过期，请从新登录！');
        // window.top.location = "hello";
        window.open("","_top");
    }
}
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg)) {
        return unescape(arr[2])
    } else {
        return null
    }
}
function onFileChange(target) {
    var fileSize = target.files[0].size;
    // }
    var size = fileSize / 1024;
    if(size>2000){
        alert("上传图片不能大于2M");
        target.value="";
        return
    }
    var name=target.value;
    var resultName = name.substring(name.lastIndexOf(".")).toLowerCase();

    if ('.jpg'!=resultName.toString().toLowerCase()&&'.png'!=resultName.toString().toLowerCase()&&'.jpeg'!=resultName.toString().toLowerCase()){
        alert('只能上传png、jpg、jpeg格式文件，您上传的文件类型为'+resultName+'，请重新上传');
        target.value="";
    }
}