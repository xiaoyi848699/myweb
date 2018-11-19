
jQuery(document).ready(function() {
    //登录
    $('.page-container form').submit(function(){
        var username = $(this).find('.username').val();
        var password = $(this).find('.password').val();
        if(username == '') {
            $(this).find('.error').fadeOut('fast', function(){
                $(this).css('top', '27px');
            });
            $(this).find('.error').fadeIn('fast', function(){
                $(this).parent().find('.username').focus();
            });
            return false;
        }
        if(password == '') {
            $(this).find('.error').fadeOut('fast', function(){
                $(this).css('top', '96px');
            });
            $(this).find('.error').fadeIn('fast', function(){
                $(this).parent().find('.password').focus();
            });
            return false;
        }

        setCookie("username",username,10*60*1000);
    });
    //注册
    $('.page-container-register form').submit(function(){
        var username = $(this).find('.username').val();
        var recommendation = $(this).find('.recommendation').val();
        var phone = $(this).find('.phone').val();
        var email = $(this).find('.email').val();
        var password = $(this).find('.password').val();
        var re_password = $(this).find('.re_password').val();
        if(username == '') {
            $(this).find('.error').fadeOut('fast', function(){
                $(this).css('top', '27px');
            });
            $(this).find('.error').fadeIn('fast', function(){
                $(this).parent().find('.username').focus();
            });
            return false;
        }
        if(recommendation == '') {
            $(this).find('.error').fadeOut('fast', function(){
                $(this).css('top', '96px');
            });
            $(this).find('.error').fadeIn('fast', function(){
                $(this).parent().find('.recommendation').focus();
            });
            return false;
        }
        if(phone == '') {
            $(this).find('.error').fadeOut('fast', function(){
                $(this).css('top', '165px');
            });
            $(this).find('.error').fadeIn('fast', function(){
                $(this).parent().find('.phone').focus();
            });
            return false;
        }
        if(email == '') {
            $(this).find('.error').fadeOut('fast', function(){
                $(this).css('top', '234px');
            });
            $(this).find('.error').fadeIn('fast', function(){
                $(this).parent().find('.email').focus();
            });
            return false;
        }
        if(password == '') {
            $(this).find('.error').fadeOut('fast', function(){
                $(this).css('top', '303px');
            });
            $(this).find('.error').fadeIn('fast', function(){
                $(this).parent().find('.password').focus();
            });
            return false;
        }
        if(password != re_password){
            $(this).find('.error').fadeOut('fast', function(){
                $(this).css('top', '372px');
            });
            $(this).find('.error').fadeIn('fast', function(){
                $(this).parent().find('.re_password').focus();
            });
            alert('密码不一致')
            return false;
        }
        setCookie("username",username,10*60*1000);
    });

    $('.page-container form .username, .page-container form .password').keyup(function(){
        $(this).parent().find('.error').fadeOut('fast');
    });
    $('.register').click(function(){
            $('.page-container').slideUp();
            $('.page-container-register').slideDown();
            // $('.page-container').fadeOut();
            // $('.page-container-register').fadeIn(800);
    });
    $('.login').click(function(){
        $('.page-container-register').slideUp();
        $('.page-container').slideDown();
        // $('.page-container-register').fadeOut();
        // $('.page-container').fadeIn(800);
    });
    $('.update_receipt_code').click(function(){
        alert("aaa")
        // if (plus.os.name == "Android" ) {
        //     alert("bbb")
        //     plus.runtime.launchApplication( {
        //         pname:"com.taobao.taobao"
        //         ,extra:{url:"https://m.taobao.com/#index"}}, function ( e ) {
        //         alert( "打开失败:" + e.message );
        //     } );
        // } else if ( plus.os.name == "iOS" ) {
        //     alert("bbb")
        //     plus.runtime.launchApplication( {action:"taobao://"}, function ( e ) {
        //         alert( "打开失败: " + e.message );
        //     } );
        // }
    });
    // var  message = $("#hintmessage").val();
    // var  message = getCookie("message");
    // alert("message:"+message)
    // if(null != message && message.length > 0){
    //     $('#operateHint').css("display","block");
    //     $('#operateHint').css("background"," #FF0F0F");
    //     // $('#hintmessage').html(message);
    //     setTimeout("timeshow()",4000);
    // }
    setTimeout(function(){document.getElementById("hint").style.display="none";},3000);
});
// function timeshow(){
//     $('#operateHint').css("display","none");
// }