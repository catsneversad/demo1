<%--
  Created by IntelliJ IDEA.
  User: magzhan
  Date: 10/7/2020
  Time: 9:14 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/upload" method="post" enctype="multipart/form-data">
    <input type="file" name="file" multiple><br>
    <input type="submit">
</form>
</body>
</html>