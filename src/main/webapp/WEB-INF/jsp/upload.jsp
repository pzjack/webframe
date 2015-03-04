<%-- 
    Document   : upload
    Created on : 2014-12-21, 11:13:29
    Author     : panzhen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>图片上传</title>
    </head>
    <body>
        <form action="api/v1/sys/update/upload" method="post" enctype="multipart/form-data">
            <input type="hidden" value="1" name="mid" />
            <input type="hidden" value="2B88EF9238F3452F8EAD4D4974E74E73" name="token" />
            <label>小图片: <input type="file" name="filename" /></label>
            <input type="submit" value="上传" />
        </form>
    </body>
</html>
