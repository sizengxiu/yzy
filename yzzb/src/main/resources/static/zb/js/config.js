/**
 * 删除所有数据
 */
function deleteAllData(){
    $.ajax({
        url:'/yzzb/test/deleteAllData',
        type:'post',
        dataType:'json',
        success:function(result){
            if(result.success) {
                layer.msg("删除成功！");
            }else{
                layer.msg("删除失败！");
            }
        },
        error:function(status){
        }
    });
}

/**
 * 初始化
 */
function initFirstZbDate(){
    $.ajax({
        url:'/yzzb/duty/initFirstZbDate',
        type:'post',
        dataType:'json',
        success:function(result){
            if(result.success) {
                layer.msg("初始化成功！");
            }else{
                layer.msg("初始化失败！");
            }
        },
        error:function(status){
        }
    });
}