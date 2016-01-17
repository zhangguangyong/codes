$(function(){
	
	// 条件
	
	// 列表
	var $datagrid = $('#dictionaryDatagrid');
	
	/** 初始化搜索条件 */
	function initSearchCondition(){
		//获取实体数据
		$.util.sjax({
			url: WEB_ROOT + '/system/Dictionary/getSearchCondition',
			success: function(result){
				//前置条件
				var preconditions = $.util.toJsonObject(result['preconditions'])[0];
				var properties = preconditions['properties'].split(',');
				var expressions = preconditions['expressions'].split(',');
				
				// 获取条件分组信息
				var conditionsGroup = $.util.toJsonObject(result['conditionsGroup']); 
				// 根据分组信息进行条件的分组
				var conditions = $.util.toJsonObject(result['conditions']); 
				// 条件分组组装
				$.each(conditionsGroup, function(i, e){
					var conds = [];
					var groupNo = e['groupNo'];
					$.each(conditions, function(idx, ele){
						if( groupNo == ele['groupNo'] ){
							conds.push( ele );
						}
					});
					e['conditions'] = conds;
				});
				
				// 渲染条件
				var conditionsHtml = '';
				$.each( conditionsGroup, function(i, e){
					var trs = '';
					// 一组条件
					$.each(e['conditions'], function(idx, ele){
						var tr = '';
						if( idx == 0 ){
							tr += '<td> <span> '+ getGroupName(ele['groupNo']) +' </span> </td>';
						} else {
							tr += '<td> <div> '+ getJoinTypeSelect(ele['joinType']) +' </div> </td>';
						}
						tr += '<td> <div> '+ getPropertySelect(properties, ele['property']) +' </div> </td>';
						tr += '<td> <div> '+ getExpressionSelect(expressions, ele['expression']) +' </div> </td>';
						tr += '<td> <div> <input type="text" /> </div> </td>';
						tr = '<tr> '+ tr +' </tr>'
						trs += tr;
					});
					
					// 组连接类型
					if( i > 0 ){
						conditionsHtml += '<td> <div class="groupJoinType" > '+ getJoinTypeSelect( e['groupJoinType'] ) +' </div> </td>';
					}
					
					conditionsHtml += '<td> <table> '+ trs +' </table> </td>';
				} );
				conditionsHtml += '<td> <div class="buttons" > <button type="button">搜索</button> <button type="button">重置</button> </div> </td>';
				conditionsHtml = '<table> <tbody> <tr> '+ conditionsHtml +' </tr> </tbody> </table>';
				$('#searchConditionForm').html( conditionsHtml );
			}
		});
	}
	
	/** 初始化列表 */
	function initDatagrid(){
		$datagrid.datagrid({
			
		});
	}
	
	function init(){
		initSearchCondition();
		// initDatagrid();
	}
	
	init();
	
	// 辅助借口------------------------------------------
	function getGroupName(groupNo){
		var g;
		switch(groupNo){
			case '1': g = '一'; break;
			case '2': g = '二'; break;
			case '3': g = '三'; break;
			case '4': g = '四'; break;
			case '5': g = '五'; break;
		}
		return '第'+ g +'组';
	}
	
	function getJoinTypeSelect(joinType){
		var items = [
			{'text': '并且', 'value': 'and' },
			{'text': '或者', 'value': 'or' }
		];
		return getSelectHtml(items, joinType);
	}
	
	function getPropertySelect(properties, selected){
		return getSelectHtml(properties, selected);
	}

	function getExpressionSelect(expressions, selected){
		return getSelectHtml(expressions, selected);
	}
	
	function getSelectHtml(items, selected){
		var html = '';
		$.each(items, function(i, e){
			var text = e;
			var value = e;
			if( $.isPlainObject(e) ){
				text = e['text'];
				value = e['value'];
			}
			html += '<option value="'+value+'"';
			if( value == selected ){
				html += ' selected="selected" ';
			}
			html += '>'+value+'</option>';
		});
		return '<select> '+ html +' </select>';
	}
	
});



