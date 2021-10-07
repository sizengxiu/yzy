

$(function() {
    var date = new Date();
    var year=date.getFullYear();
    var month=date.getMonth()+1;
    $("#yearCom").combobox({
        editable: false,
        panelHeight: 'auto',
        height:38,
        valueField: 'value',
        textField: 'key',
        value:year,
        url: '/yzzb/duty/getZbYearList',
        loadFilter: comboxLoadFilter
    });
    $("#monthCom").combobox({
        editable: false,
        panelHeight: 'auto',
        height:38,
        valueField: 'value',
        textField: 'key',
        value:month,
        url: '/yzzb/duty/getZbMonthList',
        loadFilter: comboxLoadFilter
    });

    $('#paiban-view-table').datagrid({
        url:'/yzzb/duty/getPbListByDate',
        fitColumns:true,
        rownumbers:true,//该参数生效必须有一列指定了列宽
        pagination:false,//分页控件
        queryParams:{
            year:$('#yearCom').combobox('getValue'),
            month:$('#monthCom').combobox('getValue')
        },
        loadFilter: function(data){
            if(data.success){
                var result=data.data;
                result.rows=result.list;
                if(result.size==0){
                    layer.msg("当前月份未查询到排班数据，请点击排班按钮进行排班！");
                    $("#unpublish_div").hide();
                    $("#pbBtn").show();
                }else{
                     isDataPublished();
                }
                return data.data;
            }else{
                layer.msg(data.message);
                var result=new Object();
                result.total=0;
                return result;
            }
        },
        onLoadSuccess:function(data){
            console.info(data);
            if(data.size==0){
                return;
            }
            var list=data.list;
            for(var i=0,size=data.size;i<size;i=i+3){
                $('#paiban-view-table').datagrid('mergeCells',{
                    index:i,
                    field:'date',
                    rowspan:3
                });
            }

        },
        columns:[[
            {field:'date',title:'值班日期',align:'center',width:100,formatter:dateFormatter},
            {field:'weekIndex',title:'周几',align:'center',width:100},
            {field:'code',title:'工号',align:'center',width:50},
            {field:'name',title:'姓名',align:'center',width:100},
            {field:'phone',title:'手机号',align:'center',width:200},
            {field:'fixedPhone',title:'固定电话',align:'center',width:200},
            {field:'sex',title:'性别',align:'center',width:50,formatter:sexFormatter}
        ]]
    });
});

function comboxLoadFilter(data){
    console.log(data);
    return data.data;
}


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
 *
 * @param value
 * @param row
 * @param index
 * @returns {string}
 */
function dateFormatter(value,row,index) {
    var date = new Date(value); //
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'年'+m+'月'+d+'日';
}

/**
 * 开始排班
 */
function planByDate(){
    var year = $('#yearCom').combobox('getValue');
    var month = $('#monthCom').combobox('getValue');
    $.ajax({
        url:'/yzzb/duty/planByDate',
        type:'post',
        dataType:'json',
        data:{'year':year,'month':month},
        success:function(result){
            if(!result.success){
                layer.msg(result.message);
            }else{
                getPbListByDate();
            }
        },
        error:function(status){
        }
    });

}

/**
 * 查看排班
 */
function getPbListByDate(){
    $('#paiban-view-table').datagrid('load',{
        year:$('#yearCom').combobox('getValue'),
        month:$('#monthCom').combobox('getValue')
    });

}

/**
 * 发布排班数据
 */
function publishPbData(){
    var year = $('#yearCom').combobox('getValue');
    var month = $('#monthCom').combobox('getValue');
    $.ajax({
        url:'/yzzb/duty/publishPbData',
        type:'post',
        dataType:'json',
        data:{'year':year,'month':month},
        success:function(result){
            if(result.success){
                $("#unpublish_div").hide();
                $("#pbBtn").hide();
                layer.alert("排班数据发布成功！");
            }else{
                $("#unpublish_div").show();
                $("#pbBtn").show();
                layer.msg(result.message);
            }
        },
        error:function(status){
        }
    });
}

/**
 * 当前月份数据是否允许发布 */
function isDataPublished(){
    var year = $('#yearCom').combobox('getValue');
    var month = $('#monthCom').combobox('getValue');
    $.ajax({
        url:'/yzzb/duty/isDataPublished',
        type:'post',
        dataType:'json',
        data:{'year':year,'month':month},
        success:function(result){
            if(result.success){
                $("#unpublish_div").hide();
                $("#pbBtn").hide();
            }else{
                $("#unpublish_div").show();
                $("#pbBtn").show();
            }
        },
        error:function(status){
        }
    });
}