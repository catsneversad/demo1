<%--
  Created by IntelliJ IDEA.
  User: magzhan
  Date: 10/7/2020
  Time: 9:14 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<html>
<head>
    <title>Title</title>
</head>
<%ArrayList<String> type = (ArrayList<String>) request.getAttribute("typeOfFiles");%>
<body>
    <h1>HELLO</h1>
    <%for (int i = 0; i < type.size(); i++) {%>
        <div style="display: inline-block; width: 300px; height: 200px; border: 2px #313335 solid">
            <h2><%=i + ": "%><%=type.get(i)%></h2>
        </div>
    <%}%>
</body>
</html>
