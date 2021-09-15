

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

    $('#paiban-view-modal-table').datagrid({
        url:'/yzzb/duty/planByDate',
        fitColumns:true,
        rownumbers:true,//该参数生效必须有一列指定了列宽
        pagination:true,//分页控件
        pageSize:10,
        pageNumber:1,
        queryParams:{
            year:2021,
            month:5
        },
        loadFilter: function(data){
            if(data.success){
                var result=data.data;
                result.rows=result.list;
                return data.data;
            }else{
                layer.msg(data.message);
                var result=new Object();
                result.total=0;
                return result;
            }
        },
        onBeforeLoad:function(param){
            param.pageSize=param.rows;
            param.pageNum=param.page;
            delete param.rows;
            delete param.page;
        },
        columns:[[
            {field:'day',title:'日期',align:'center',width:100},
            {field:'星期',title:'weekIndex',align:'center',width:100},
            {field:'name',title:'姓名',align:'center',width:100},
            {field:'phone',title:'电话',align:'center',width:200},
            {field:'userState',title:'员工状态',align:'center',width:50},
            {field:'stop',title:'是否启用',align:'center',width:50},
            {field:'createTime',title:'数据时间',align:'center',width:50}
        ]]
    });
})

function comboxLoadFilter(data){
    console.log(data);
    return data.data;
}