$(function(){
	/** 选中实体对应的属性 */
	var _entityProperties;
	/** 实体下拉框 */
	var $entityCombobox = $('#entityCombobox');
	/** 前置条件 */
	var $preconditionsGrid = $('#preconditionsGrid');
	/** 搜索条件 */
	var $conditionsGrid = $('#conditionsGrid');
	/** 搜索条件分组 */
	var $conditionsGroupGrid = $('#conditionsGroupGrid');
	/** 排序条件 */
	var $ordersGrid = $('#ordersGrid');
	/** 分页条件 */
	var $pagesGrid = $('#pagesGrid');
	
	/** 表格工具条 */
	var _toolbar = [{
		iconCls: 'icon-add',
		text: '新增',
		handler: function(){
			buttonActionHandler(this, 'add');
		}
	},{
		iconCls: 'icon-save',
		text: '保存',
		handler: function(){
			buttonActionHandler(this, 'save');
		}
	},{
		iconCls: 'icon-back',
		text: '取消编辑',
		handler: function(){
			buttonActionHandler(this, 'cancel');
		}
	},{
		iconCls: 'icon-remove',
		text: '删除',
		handler: function(){
			buttonActionHandler(this, 'destroy');
		}
	}];
	
	// 条件操作表达式
	var expressionData = [
		{text: '等于', value: 'eq'},
		{text: '等于列', value: 'eqProperty'},
		// {text: '等于或为空', value: 'eqOrIsNull'},
		
		{text: '不等于', value: 'ne'},
		{text: '不等于列', value: 'neProperty'},
		// {text: '不等于或非空', value: 'neOrIsNotNull'},
		
		{text: '大于', value: 'gt'},
		{text: '大于列', value: 'gtProperty'},
		
		{text: '大于等于', value: 'ge'},
		{text: '大于等于列', value: 'geProperty'},
		
		{text: '小于', value: 'lt'},
		{text: '小于列', value: 'ltProperty'},
		
		{text: '小于等于', value: 'le'},
		{text: '小于等于列', value: 'leProperty'},
		
		{text: '属于', value: 'in'},
		{text: '不属于', value: 'nin'},
		{text: '介于', value: 'between'},
		{text: '不介于', value: 'nbetween'},
		
		/*
		{text: '为空字符', value: 'isEmpty'},
		{text: '为非空字符', value: 'isNotEmpty'},
		*/
		{text: '为空', value: 'isNull'},
		{text: '为非空', value: 'isNotNull'},
		
		// 不区分大小写匹配
		{text: '匹配', value: 'ilike'},
		{text: '包含', value: 'ailike'},
		{text: '前包含', value: 'silike'},
		{text: '后包含', value: 'eilike'},
		{text: '不匹配', value: 'nilike'},
		{text: '不包含', value: 'nailike'},
		{text: '前不包含', value: 'nsilike'},
		{text: '后不包含', value: 'neilike'}
		// 分区大小写匹配
		/*
		{text: '_匹配', value: 'like'},
		{text: '_包含', value: 'alike'},
		{text: '_前包含', value: 'slike'},
		{text: '_后包含', value: 'elike'},
		{text: '_不匹配', value: 'nlike'},
		{text: '_不包含', value: 'nalike'},
		{text: '_前不包含', value: 'nslike'},
		{text: '_后不包含', value: 'nelike'}
		*/		
	];
	
	var joinTypeData = [
		{text: '并且', value: 'and'},
		{text: '或者', value: 'or'}
	];

	var groupNoData = [
		{text: '1', value: '1'},
		{text: '2', value: '2'},
		{text: '3', value: '3'},
		{text: '4', value: '4'},
		{text: '5', value: '5'}
	];
	
	var orderData = [
		{text: '正序', value: 'asc'},
		{text: '倒序', value: 'desc'}
	];
	
	var pagePositionData = [
		{text: '底部', value: 'bottom'},
		{text: '顶部', value: 'top'}
	];
	
	var pageListData = [
		{text: '5', value: '5'},
		{text: '10', value: '10'},
		{text: '15', value: '15'},
		{text: '20', value: '20'},
		{text: '25', value: '25'},
		{text: '30', value: '30'},
		{text: '50', value: '50'},
		{text: '100', value: '100'},
		{text: '200', value: '200'}
	];
	
	/**
	 * 初始化实体名称下拉框
	 */
	function initEntityCombbox(){
		$entityCombobox.combobox({
			editable: false,
			width: 400,
			height: 25,
			url: WEB_ROOT + '/system/SearchCondition/getEntityNameMap',
			onLoadSuccess: function(){
				$(this).combobox('select', $(this).combobox('getData')[0]['value']);
			},
			onSelect: function(record){
				var entityName = $(this).combobox('getValue');
				// 根据EntityName 获取Entity相关属性
				initSearchCondition(entityName);
			}
		});
	}
	
	function initSearchCondition(entityName){
		// 获取选中实体的属性
		$.util.ajax({
			url: WEB_ROOT + '/system/SearchCondition/getEntityProperty?entityName='+entityName,
			success: function(result){
				_entityProperties = result;
				//获取实体数据
				$.util.sjax({
					url: WEB_ROOT + '/system/SearchCondition/getEntityCondition?entityName='+entityName,
					success: function(result){
						var preconditionsData;
						var conditionsData;
						var conditionsGroupData;
						var ordersData;
						var pagesData;
						if( result ){
							preconditionsData = toJsonObject( result['preconditions'] );
							conditionsData = toJsonObject( result['conditions'] );
							conditionsGroupData = toJsonObject( result['conditionsGroup'] );
							ordersData = toJsonObject( result['orders'] );
							pagesData = toJsonObject( result['pages'] );
						}
						// 前置条件
						initPreconditionsGrid(preconditionsData);
						// 条件
						initConditionsGrid(conditionsData);
						// 条件分组
						initConditionsGroupGrid(conditionsGroupData);
						// 排序
						initOrdersGrid(ordersData);
						// 分页
						initPagesGrid(pagesData);
					}
				});
			}
		});
	}
	
	/**
	 * 前置条件
	 */
	function initPreconditionsGrid(data){
		$preconditionsGrid.edatagrid({
			collapsible: true,
			toolbar:_toolbar,
			columns: [[
				// 属性名
				{field: 'properties', title: '可用属性', width: '45%', editor : {
					type:'combobox',
					options:{
						data: _entityProperties,
						multiple:true,
						editable: false,
						required:true
					}
				}},
				// 操作符
				{field: 'expressions', title: '可用操作', width: '45%', editor : {
					type:'combobox',
					options:{
						data: expressionData,
						multiple:true,
						editable: false,
						required:true
					}
				}}
			]]
		});
		
		if( data ){
			$preconditionsGrid.datagrid('loadData', data);
		}
	
	}
	
	/**
	 * 初始化属性表格
	 */
	function initConditionsGrid(data){
		$conditionsGrid.edatagrid({
			collapsible: true,
			toolbar:_toolbar,
			columns: [[
				// 属性名
				{field: 'property', title: '属性名称', width: '20%', editor : {
					type:'combobox',
					options:{
						data: _entityProperties,
						editable: false,
						required:true
					}
				}},
				// 操作符
				{field: 'expressions', title: '操作符', width: '40%', editor : {
					type:'combobox',
					options:{
						data: expressionData,
						editable: false,
						required:true
					}
				}},
				// 连接类型
				{field: 'joinType', title: '连接类型', width: 80, editor : {
					type:'combobox',
					options:{
						data: joinTypeData,
						editable: false,
						required:true
					}
				}},
				// 分组号
				{field: 'groupNo', title: '分组号', width: 80, editor : {
					type:'combobox',
					options:{
						data: groupNoData,
						editable: false,
						required:true
					}
				}}
			]]
		});
		
		if( data ){
			$conditionsGrid.datagrid('loadData', data);
		}
	}
	
	/**
	 * 搜索条件分组
	 */
	function initConditionsGroupGrid(data){
		$conditionsGroupGrid.edatagrid({
			collapsible: true,
			toolbar:_toolbar,
			columns: [[
				// 属性名
				{field: 'groupNo', title: '分组号', width: '20%', editor : {
					type:'combobox',
					options:{
						data: groupNoData,
						editable: false,
						required:true
					}
				}},
				// 操作符
				{field: 'groupJoinType', title: '分组连接类型', width: '40%', editor : {
					type:'combobox',
					options:{
						data: joinTypeData,
						multiple:true,
						editable: false,
						required:true
					}
				}}
			]]
		});
		
		if( data ){
			$conditionsGroupGrid.datagrid('loadData', data);
		}
	}
	
	/**
	 * 排序条件
	 */
	function initOrdersGrid(data){
		$ordersGrid.edatagrid({
			collapsible: true,
			toolbar:_toolbar,
			columns: [[
				// 属性名
				{field: 'property', title: '属性名称', width: '20%', editor : {
					type:'combobox',
					options:{
						data: _entityProperties,
						editable: false,
						required:true
					}
				}},
				// 操作符
				{field: 'order', title: '排序', width: '40%', editor : {
					type:'combobox',
					options:{
						data: orderData,
						editable: false,
						required:true
					}
				}}
			]]
		});
		
		if( data ){
			$ordersGrid.datagrid('loadData', data);
		}
	}

	function initPagesGrid(data){
		$pagesGrid.edatagrid({
			collapsible: true,
			toolbar: _toolbar,
			columns: [[
				// 操作符
				{field: 'pagePosition', title: '分页栏显示位置', width: '20%', editor : {
					type:'combobox',
					options:{
						data: pagePositionData,
						editable: false,
						required:true
					}
				}},
				
				// 属性名
				{field: 'pageList', title: '页大小选择列表', width: '40%', editor : {
					type:'combobox',
					options:{
						data: pageListData,
						multiple:true,
						editable: false,
						required:true
					}
				}}
			]]
		});
		
		if( data ){
			$pagesGrid.datagrid('loadData', data);
		}
	}
	
	/**
	 * 按钮动作处理
	 */
	function buttonActionHandler(target, action){
		var $dg = $(target).closest('.datagrid-toolbar').next('.datagrid-view').children('table:hidden');
		switch(action){
			case 'add': $dg.edatagrid('addRow'); break; 
			case 'save': $dg.edatagrid('saveRow'); break;
			case 'cancel': $dg.edatagrid('cancelRow'); break;
			case 'destroy': $dg.edatagrid('destroyRow'); break;
		}
	}
	
	// 保存实体设置搜索条件
	$('#buttonSave').click(function(){
		
		var preconditions = getJsonData($preconditionsGrid);
		var conditions = getJsonData($conditionsGrid);
		var conditionsGroup = getJsonData($conditionsGroupGrid);
		var orders = getJsonData($ordersGrid);
		var pages = getJsonData($pagesGrid);
		
		searchCondition = {};
		searchCondition['preconditions'] = preconditions; 
		searchCondition['conditions'] = conditions; 
		searchCondition['conditionsGroup'] = conditionsGroup; 
		searchCondition['orders'] = orders; 
		searchCondition['pages'] = pages; 
		searchCondition['entityName'] = getEntityName; 
		$.util.ajax({
			url: WEB_ROOT + '/system/SearchCondition/saveEntityCondition',
			data: searchCondition, 
			success: function(result){
				console.info( result );
			}
		});
	});
	
	function init(){
		initEntityCombbox();
	}
	
	init();
	
	// 辅助借口 -----------------------------------
	function toJsonObject(jsonString){
		if( !jsonString || jsonString==''){
			return null;
		}
		return $.util.toJsonObject(jsonString);
	}
	function getJsonData(datagrid){
		var rows = filterRows( datagrid.datagrid('getRows') );
		if( rows.length > 0 ){
			return $.toJSON( rows );
		}
		return '';
	}
	
	function filterRows(rows){
		for (var i = 0; i < rows.length; i++) {
			delete rows[i]['isNewRecord'];
		}
		return rows;
	}
	
	function getEntityName(){
		return $entityCombobox.combobox('getValue');
	}
	
});



