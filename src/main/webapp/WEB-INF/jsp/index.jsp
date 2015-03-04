<%-- 
    Document   : index
    Created on : 2014-9-28, 17:34:33
    Author     : Administrator
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <shiro:guest>
            欢迎游客访问，<a href="${pageContext.request.contextPath}/login">点击登录</a><br/>
        </shiro:guest>
        <shiro:user>
            欢迎[<shiro:principal/>]登录，<a href="${pageContext.request.contextPath}/logout">点击退出</a><br/>
        </shiro:user>
        <h1>Hello World!</h1>
        <h2>测试</h2>
    </body>
</html>
