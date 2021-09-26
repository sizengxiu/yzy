$(function() {
    $('#user-table').datagrid({
       url:'/yzzb/employee/getUsers',
        fitColumns:true,
        rownumbers:true,//该参数生效必须有一列指定了列宽
        pagination:true,//分页控件
        pageSize:10,
        pageNumber:1,
        loadFilter: function(data){
           if(data.success){
               var result=data.data;
               result.rows=result.list;
               return data.data;
           }else{
               layer.msg(data.message);
           }
        },
        onBeforeLoad:function(param){
            param.pageSize=param.rows;
            param.pageNum=param.page;
            delete param.rows;
            delete param.page;
        },
        columns:[[
            {field:'code',title:'工号',align:'center',width:100},
            {field:'name',title:'姓名',align:'center',width:100},
            {field:'sex',title:'性别',align:'center',width:100,formatter:sexFormatter},
            {field:'phone',title:'电话',align:'center',width:200},
            {field:'userState',title:'员工状态',align:'center',width:50,formatter:userStateFormatter},
            {field:'stop',title:'是否启用',align:'center',width:50,formatter:stopFormatter},
            {field:'createTime',title:'数据时间',align:'center',width:100}
        ]]
    });

});


/**
 * 格式化员工性别
 * @param value
 * @param row
 * @param index
 * @returns {string}
 */
function sexFormatter(value,row,index){
    if(value==1){
        return '男';
    }
    return '女';
}
/**
 * 格式化员工的启停状态
 * @param value
 * @param row
 * @param index
 * @returns {string}
 */
function stopFormatter(value,row,index){
    if(value==1){
        return "停用"
    }
    return "启用";
}


/**
 * 格式化员工的在岗状态
 * @param value
 * @param row
 * @param index
 * @returns {string}
 */
function userStateFormatter(value,row,index){
    if(value==1){
        return "在岗"
    }
    return "出差";
}



/**
 * 文件上传change监听事件
 */
function fileSelectChange(){
    console.info("监听到文件改变事件");
    console.info($("#employeeFile").val());
    console.info(event.target.files[0]);
    // var fileName = '已选文件：' + event.target.files[0].name;
    var fileName= '已选文件：' +$("#employeeFile").val();
    $('.custom-file-label').text(fileName);
}

/**
 * 批量新增员工
 */
function addEmployeeByBatch(source){
    var file = document.getElementById("employeeFile").files[0];
    var form = new FormData();
    form.append("file", file);
    $.ajax({
        url:'/yzzb/employee/addEmployeeByBatch',
        type:'post',
        dataType:'json',
        data:form,
        processData: false ,    // 不处理数据
        contentType:false,
        success:function(result){
            console.info(result);
            layer.alert(result.message);
            if(result.success){
                $('#exampleModal').modal('toggle');
                $('#user-table').datagrid('reload');
            }
        },
        error:function(status){

        }
    });
}