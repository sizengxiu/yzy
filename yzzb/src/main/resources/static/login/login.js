

/**
 * 登录
 */
function login(){
    var username=$('#username').val();
    var password=$('#password').val();
    console.info(111);
    $.ajax({
        url:'/yzzb/login/login',
        type:'post',
        dataType:'json',
        data:{'username':username,'password':password},
        success:function(result){
            console.info(result);
            if(result.success){
                window.location.href=result.data;
            }else{
                layer.msg(result.message);
            }
        },
        error:function(status){

        }
    });
}