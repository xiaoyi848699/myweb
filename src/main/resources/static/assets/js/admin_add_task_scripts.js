
jQuery(document).ready(function() {

    $('.commit_task').click(function(){
        var userId =getCookie("userId");
        var title=$('#title').val();
        var task_describe=$('#task_describe').val();
        var file=$('#file').val();
        var hint = document.getElementById("hint_self");
        if(userId == null){
            alert('登录过期，请从新登录！');
            window.top.location = "hello";
        }else if(title.length<1){
            hint.innerHTML = "请输入标题";
            hint.style.display = 'block';
            setTimeout(function(){document.getElementById("hint_self").style.display="none";},3000);
        }else if(task_describe.length<1){
            hint.innerText = "请输入任务描述";
            hint.style.display = 'block';
            setTimeout(function(){document.getElementById("hint_self").style.display="none";},3000);
        }else if(file.length<1){
            hint.html = "请选择图片";
            hint.style.display = 'block';
            setTimeout(function(){document.getElementById("hint_self").style.display="none";},3000);
        }else{
            document.getElementById("form1").action="addTask?uid="+userId;
            document.getElementById("form1").submit();
        }
    });

    // var userId =getCookie("userId");
    // if(null == userId){
    //     alert('登录过期，请从新登录！');
    //     window.location.href = "hello";
    //     return
    // }
    // alert(userId)
    setTimeout(function(){document.getElementById("hint").style.display="none";},3000);
});
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