
jQuery(document).ready(function() {

    $('.update_receipt_code').click(function(){
        document.getElementById("file").click();
       // var dis=document.getElementById("filediv").style.display
       //  if(dis == 'none'){//隐藏
       //      document.getElementById("filediv").style.display = 'block';
       //      document.getElementById("update_receipt_code").textContent = '完成';
       //  }else{
       //      document.getElementById("filediv").style.display = 'none';
       //      document.getElementById("update_receipt_code").textContent = '修改收款码';
       //      // var path =$(this).find('.file').val()
       //      // alert(path)
       //      // if(path == null){
       //      //     $(this).find('.hint').text = '请先选择上传收款码'
       //      //     setTimeout(function(){document.getElementById("hint").style.display="none";},3000);
       //      // }
       //  }
    });
    var userId =getCookie("userId");
    if(null == userId){
        alert('登录过期，请从新登录！');
        window.location.href = "hello";
        return
    }
    // alert(userId)
    $("#homepage").attr("href","homepage?userId="+userId);
    $("#list").attr("href","list?userId="+userId);
    $("#person").attr("href","person?userId="+userId);
    setTimeout(function(){document.getElementById("hint").style.display="none";},3000);
});
function checkFileType(target){
    // var fileSize = 0;
    // if (isIE && !target.files) {
    //     var filePath = target.value;
    //     var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
    //     var file = fileSystem.GetFile (filePath);
    //     fileSize = file.Size;
    // } else {
    var fileSize = target.files[0].size;
    // }
    var size = fileSize / 1024;
    if(size>1000){
        alert("上传图片不能大于1M");
        target.value="";
        return
    }
    var name=target.value;
    var resultName = name.substring(name.lastIndexOf(".")).toLowerCase();

    if ('.jpg'!=resultName.toString().toLowerCase()&&'.png'!=resultName.toString().toLowerCase()&&'.jpeg'!=resultName.toString().toLowerCase()){
        alert('只能上传png、jpg、jpeg格式文件，您上传的文件类型为'+resultName+'，请重新上传');
        // resetFile();
    }else{
        var form = document.getElementById('upfile_form');
        var userId =getCookie("userId");
        if(null != userId){
            $("#hint").html = "图片上传中"
            $("#hint").display = 'block'
            form .action="updateUserReceiptCode?id="+userId;
            //再次修改input内容
            form.submit();
        }else{
            alert('登录过期，请从新登录！');
            //在原有窗口打开
            window.location.href = "hello";
            //打开新的窗口
            // window.open("http://www.baidu.com");
        }
    }
}