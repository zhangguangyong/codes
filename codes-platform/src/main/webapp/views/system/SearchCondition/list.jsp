<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../resources/inc/easyui.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>搜索条件-列表</title>
</head>
<body>
	
	<div class="easyui-layout" style="width: 100%; height: 100%;" >
		<div data-options="region:'north',title:'选择实体',split:true"
			style="height: 80px;">
			<!-- 预备条件 -->
			<div style="margin: 10px;" >
				<!-- 实体选择 -->
				<label>请选择实体：</label>
				<select class="easyui-combobox" id="entityCombobox" name="entityName" ></select>
				<!-- 保存按钮 -->
				<a id="buttonSave" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
			</div>
		</div>
		<div data-options="region:'center',title:''">
			
			<!-- 可选属性和可选操作 -->
			<div>
				<table id="preconditionsGrid" title="前置条件" ></table>
			</div>
			
			<!-- 搜索条件-->
			<div style="margin-top: 5px;" >
				<table id="conditionsGrid" title="搜索条件" ></table>
			</div>
			
			<!-- 排序条件 -->
			<div style="margin-top: 5px;" >
				<table id="conditionsGroupGrid" title="搜索条件分组"  ></table>
			</div>

			<!-- 排序条件 -->
			<div style="margin-top: 5px;" >
				<table id="ordersGrid" title="排序条件" ></table>
			</div>
			
			<!-- 分页设置 -->
			<div style="margin-top: 5px;" >
				<table id="pagesGrid" title="分页条件" ></table>
			</div>
			
		</div>
	</div>
	

	<script type="text/javascript"
		src="${ctx}/views/system/SearchCondition/js/list.js"></script>
</body>
</html>