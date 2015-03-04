<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>任务管理</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/account/create" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${account.id}"/>
		<fieldset>
			<legend><small>账号添加</small></legend>
			<div class="control-group">
				<label for="account_username" class="control-label">账号:</label>
				<div class="controls">
					<input type="text" id="task_title" name="username"  value="${task.username}" class="input-large required" minlength="3"/>
				</div>
			</div>	
			<div class="control-group">
				<label for="account_password" class="control-label">密码:</label>
				<div class="controls">
					<textarea id="account_password" name="password" class="input-large">${task.password}</textarea>
				</div>
			</div>	
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#task_title").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</body>
</html>
