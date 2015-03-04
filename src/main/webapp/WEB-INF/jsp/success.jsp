<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
    <head>
        <title></title>
    </head>
    <body>
        <shiro:hasAnyRoles name="admin">
            <shiro:principal/>拥有角色admin
        </shiro:hasAnyRoles>
        <shiro:guest>
            欢迎游客访问，<a href="${pageContext.request.contextPath}/login">点击登录</a><br/>
        </shiro:guest>
        <shiro:user>
            欢迎[<shiro:principal/>]登录，<a href="${pageContext.request.contextPath}/logout">点击退出</a><br/>
        </shiro:user>
 
        <shiro:hasPermission name="user:create">
            拥有创建用户角色
        </shiro:hasPermission>
    </body>
</html>