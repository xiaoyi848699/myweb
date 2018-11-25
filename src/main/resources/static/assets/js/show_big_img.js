
function showBigImg(_this) {
    // alert(_this.getAttribute("src"))
    // alert($(".data td img:nth-child(1)").attr("src"))
    var src = _this.getAttribute("src");//获取当前点击的pimg元素中的src属性
    imgShow("#outerdiv", "#innerdiv", "#bigimg", src);
}
function showBigImgValue(_this,val) {
    // alert($(".data td img:nth-child(1)").attr("src"))
    var src;
    if(val == 0){
        $('#nickname').hide();
        $('#commit_btn').hide();
        src = _this.getAttribute("src");//获取当前点击的pimg元素中的src属性
    }else{
        src = _this.getAttribute("value");//获取当前点击的pimg元素中的src属性
        var value1 = _this.getAttribute("value1");//获取当前点击的pimg元素中的src属性
        var value2 = _this.getAttribute("value2");//获取当前点击的pimg元素中的src属性
        if(null== src){
            alert('用户没用上传收款码，无法返款！');
            return
        }
        // alert("value1"+value1+"value2"+value2)
        $('#nickname').show();
        $('#commit_btn').show();
        $('#nickname').html(value2)
        $('#commit_btn').click(function(){
            // alert("value1"+value1)
            var userId =getCookie("adUserId");
            if(null == userId){
                alert('登录过期，请从新登录！');
                window.top.location = "";
            }else{
                // window.location.href = "updateUserTaskStatus?userTaskId="+value1+"&status=3&operateId="+userId;
                document.write("<form action='updateUserTaskStatus' method='post' name='post_form' style='display:none'>");
                document.write("<input type='hidden' name='userTaskId' value="+value1+">");
                document.write("<input type='hidden' name='status' value="+3+">");
                document.write("<input type='hidden' name='operateId' value="+userId+">");
                document.write("</form>");
                document.post_form.submit();
            }
        });
    }
    imgShow("#outerdiv", "#innerdiv", "#bigimg", src);
}
function updateTaskStatus(_this) {
    var value1 = _this.getAttribute("value1");//获取当前点击的pimg元素中的src属性
    var userId =getCookie("adUserId");
    if(null == userId){
        alert('登录过期，请从新登录！');
        window.top.location = "";
    }else{
        // window.location.href = "updateUserTaskStatus?userTaskId="+value1+"&status=3&operateId="+userId;
        document.write("<form action='updateUserTaskStatus' method='post' name='post_form' style='display:none'>");
        document.write("<input type='hidden' name='userTaskId' value="+value1+">");
        document.write("<input type='hidden' name='status' value="+3+">");
        document.write("<input type='hidden' name='operateId' value="+userId+">");
        document.write("</form>");
        document.post_form.submit();
    }
}

function imgShow(outerdiv, innerdiv, bigimg, src){

    // var src = _this.attr("src");//获取当前点击的pimg元素中的src属性
    $(bigimg).attr("src", src);//设置#bigimg元素的src属性

    /*获取当前点击图片的真实大小，并显示弹出层及大图*/
    $("<img/>").attr("src", src).load(function(){
        var windowW = $(window).width();//获取当前窗口宽度
        var windowH = $(window).height();//获取当前窗口高度
        var realWidth = this.width;//获取图片真实宽度
        var realHeight = this.height;//获取图片真实高度
        var imgWidth, imgHeight;
        var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放

        if(realHeight>windowH*scale) {//判断图片高度
            imgHeight = windowH*scale;//如大于窗口高度，图片高度进行缩放
            imgWidth = imgHeight/realHeight*realWidth;//等比例缩放宽度
            if(imgWidth>windowW*scale) {//如宽度扔大于窗口宽度
                imgWidth = windowW*scale;//再对宽度进行缩放
            }
        } else if(realWidth>windowW*scale) {//如图片高度合适，判断图片宽度
            imgWidth = windowW*scale;//如大于窗口宽度，图片宽度进行缩放
            imgHeight = imgWidth/realWidth*realHeight;//等比例缩放高度
        } else {//如果图片真实高度和宽度都符合要求，高宽不变
            imgWidth = realWidth;
            imgHeight = realHeight;
        }
        $(bigimg).css("width",imgWidth);//以最终的宽度对图片缩放

        var w = (windowW-imgWidth)/2;//计算图片与窗口左边距
        var h = (windowH-imgHeight)/2;//计算图片与窗口上边距
        $(innerdiv).css({"top":h, "left":w});//设置#innerdiv的top和left属性
        $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
    });

    $(outerdiv).click(function(){//再次点击淡出消失弹出层
        $(this).fadeOut("fast");
    });
}