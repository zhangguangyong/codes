<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../resources/inc/easyui.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>字典-列表</title>
	<style>
		div.groupJoinType{
			margin: 5px;
		}
	</style>
</head>

<body>

	<!-- 条件 -->
	<div id="searchConditionWrap" class="searchConditionWrap" style="border: 1px red solid; " >
		<form id="searchConditionForm" class="searchConditionForm" >
			<!-- 至少两列 -->
			<table>
				<tbody>
					<tr>
						<td class="groupCondition" >
							<table>
								<tr>
									<td>
										<span class="groupNo" >第一组</span>
									</td>
									<td>
										<div class="properties" >
											<select> <option>xxxxxxxx</option> </select>
										</div>
									</td>
									<td>
										<div class="expressions" >
											<select> <option>xxxxxxxx</option> </select>
										</div>
									</td>
									<td>
										<div class="inputValue" >
											<input type="text" />
										</div>
									</td>
								</tr>
								
								<tr>
									<td>
										<select> <option>或者</option> </select>
									</td>
									<td>
										<select> <option>xxxxxxxx</option> </select>
									</td>
									<td>
										<select> <option>xxxxxxxx</option> </select>
									</td>
									<td>
										<input type="text" />
									</td>
								</tr>
								<tr>
									<td>
										<select> <option>并且</option> </select>
									</td>
									<td>
										<select> <option>xxxxxxxx</option> </select>
									</td>
									<td>
										<select> <option>xxxxxxxx</option> </select>
									</td>
									<td>
										<input type="text" />
									</td>
								</tr>
							</table>
						</td>

						
						<td>
							<div class="groupJoinType" >
								<select>
									<option value="and" selected="selected">并且</option>
									<option value="or">或者</option>
								</select>
							</div>
						</td>
						
						<td>
							<table>
								<tr>
									<td>第二组</td>
									<td>
										<select> <option>xxxxxxxx</option> </select>
									</td>
									<td>
										<select> <option>xxxxxxxx</option> </select>
									</td>
									<td>
										<input type="text" />
									</td>
								</tr>
								<tr>
									<td>
										<select> <option>或者</option> </select>
									</td>
									<td>
										<select> <option>xxxxxxxx</option> </select>
									</td>
									<td>
										<select> <option>xxxxxxxx</option> </select>
									</td>
									<td>
										<input type="text" />
									</td>
								</tr>
								<tr>
									<td>
										<select> <option>并且</option> </select>
									</td>
									<td>
										<select> <option>xxxxxxxx</option> </select>
									</td>
									<td>
										<select> <option>xxxxxxxx</option> </select>
									</td>
									<td>
										<input type="text" />
									</td>
								</tr>
							</table>
						</td>

						<td class="w-150px">
							<div class="btn-group">
								<button type="submit" id="submit" class="btn btn-submit btn-primary">搜索</button>
								<button type="button" onclick="resetForm(this)" class="btn">重置</button>
								<button type="button" onclick="saveQuery()" class="btn">保存</button>
							</div>
						</td>

					</tr>
				</tbody>
			</table>
			
		</form>
	</div>
	
	<!-- 列表 -->
	<div id="datagridWrap" >
		<table id="dictionaryDatagrid" ></table>
	</div>

	<script type="text/javascript"
		src="${ctx}/views/system/Dictionary/js/list.js"></script>
</body>
</html>