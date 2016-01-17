<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- 服务端参数 -->
<c:set var="ctx"  value="${pageContext.request.contextPath}" />
<input type="hidden" id="input_hidden_ctx" name="ctx" value="${ctx}" />

<script type="text/javascript">
	var WEB_ROOT = document.getElementById('input_hidden_ctx').value;
</script>

<!-- jQuery -->
<script type="text/javascript" src="${ctx}/resources/jquery/javascript.extension.js"></script>
<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resources/jquery/jquery.json.js"></script>
<script type="text/javascript" src="${ctx}/resources/jquery/jquery.timers.js"></script>
<script type="text/javascript" src="${ctx}/resources/jquery/jquery.jqprint.js"></script>

<script type="text/javascript" src="${ctx}/resources/jquery/jquery.extension.js"></script>
