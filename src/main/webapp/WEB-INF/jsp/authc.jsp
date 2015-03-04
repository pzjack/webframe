<%-- 
    Document   : authc
    Created on : 2014-10-9, 21:49:37
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>authc test</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <shiro:notAuthenticated>
            欢迎[<shiro:principal/>]，<a href="${pageContext.request.contextPath}/login">点击登录</a><br/><br/>
        </shiro:notAuthenticated>
        <shiro:user>
            欢迎[<shiro:principal/>]登录，<a href="${pageContext.request.contextPath}/logout">点击退出</a><br/>
        </shiro:user>
        <form method="POST" action="api/v1/app/qryphone">
            <input name="uid" value="32"/>
            <input name="token" value="64F3AD7EC6E64DB38904B6650548435B"/>
            <input name="query" value="大送奶"/>
            <input name="role" value="MILKMAN"/>
            <input type="submit" value="Submit"/>
        </form>
    </body>
</html>
