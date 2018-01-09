<%--
  Created by IntelliJ IDEA.
  User: xjwhhh
  Date: 2018/1/3
  Time: 17:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ReLogin</title>
</head>
<body>

Sorry, your account or password is wrong, please reLogin

<form method="GET" action="<%=response.encodeURL(request.getContextPath() + "/login.jsp")%>">
    </p>
    <input type="submit" name="reLogin" value="reLogin">
</form>

</body>
</html>
