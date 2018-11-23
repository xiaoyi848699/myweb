
jQuery(document).ready(function() {

    // $('.update_receipt_code').click(function(){
    //    // var dis= $(this).find('.file').display
    //    var dis=document.getElementById("filediv").style.display
    //     if(dis == 'none'){//隐藏
    //         document.getElementById("filediv").style.display = 'block';
    //         document.getElementById("update_receipt_code").html = '完成';
    //     }else{
    //         document.getElementById("filediv").style.display = 'none';
    //         document.getElementById("update_receipt_code").html = '修改收款码';
    //         // var path =$(this).find('.file').val()
    //         // alert(path)
    //         // if(path == null){
    //         //     $(this).find('.hint').text = '请先选择上传收款码'
    //         //     setTimeout(function(){document.getElementById("hint").style.display="none";},3000);
    //         // }
    //     }
    // });
    // var userId =getCookie("adUserId");
    // if(null == userId){
    //     alert('登录过期，请从新登录！');
    //     window.location.href = "hello";
    //     return
    // }
    // alert(userId)
    setTimeout(function(){document.getElementById("hint").style.display="none";},3000);
});
function closeTask(_this) {
    var userId = getCookie("adUserId");
    var value = _this.getAttribute("value");
    if (null != userId) {
        window.location.href="updateTaskStatus?status=2&taskId="+value+"&operateId="+userId
    } else {
        alert('登录过期，请从新登录！');
        window.open("","_top");
    }
}