<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
    <head>
        <title>登录页</title>
        <link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">
        <link href="${ctx}/static/bootstrap/2.3.2/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
        <link href="${ctx}/static/jquery-validation/1.11.1/validate.css" type="text/css" rel="stylesheet" />
        <link href="${ctx}/static/styles/default.css" type="text/css" rel="stylesheet" />
        <script src="${ctx}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
        <style type="text/css">
            body {
                padding-top: 40px;
                padding-bottom: 40px;
                background-color: #f5f5f5;
            }
            .form-signin {
                max-width: 300px;
                padding: 19px 29px 29px;
                margin: 0 auto 20px;
                background-color: #fff;
                border: 1px solid #e5e5e5;
                -webkit-border-radius: 5px;
                -moz-border-radius: 5px;
                border-radius: 5px;
                -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
            }
            .form-signin .form-signin-heading,
            .form-signin .checkbox {
                margin-bottom: 10px;
            }
            .form-signin input[type="text"],
            .form-signin input[type="password"] {
                font-size: 16px;
                height: auto;
                margin-bottom: 15px;
                padding: 7px 9px;
            }
        </style>
    </head>
    <body>
        <form id="loginForm" action="" method="post" class="form-signin">
            <!-- <h2 class="form-signin-heading">请登录</h2> -->
            <c:if test="${not empty shiroLoginFailure}">
                <div class="alert alert-error input-medium controls">
                    <button class="close" data-dismiss="alert">×</button>登录失败，请重试.
                </div>
            </c:if>
            <fieldset>
                <legend><small>请登录</small></legend>
                <div class="control-group">
                    <!-- <label for="task_title" class="control-label">账号:</label> -->
                    <div class="controls">
                        <input type="text" id="username" name="username"  value="${username}" class="input-block-level required" placeholder="账号"/>
                    </div>
                </div>
                <div class="control-group">
                     <!-- <label for="task_title" class="control-label">密码:</label> -->
                    <div class="controls">
                        <input type="password" id="password" name="password" class="input-block-level required"  placeholder="密码"/>
                    </div>
                </div>
                <label class="checkbox">
                    <input id="rememberMe" name="rememberMe" type="checkbox" value="true"> 记住我
                </label>
                <button class="btn btn-large btn-primary" type="submit">登 录</button>
            </fieldset>
        </form>
        <script>
            $(function () {
                $("#username").focus();
                $("#loginForm").validate();
            });
        </script>
        <script src="${ctx}/static/bootstrap/2.3.2/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>