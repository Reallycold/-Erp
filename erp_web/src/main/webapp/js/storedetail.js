$(function(){
	$('#grid').datagrid({
		url:'storedetail_listByPage',
		columns:[[
		           {field:'uuid',title:'编号',width:100},
		  		    {field:'storeName',title:'仓库名称',width:100},
		  		    {field:'goodsName',title:'商品名称',width:100},
		  		    {field:'num',title:'数量',width:100}
		          ]],
		 singleSelect:true,
		 pagination:true
	});
	
});