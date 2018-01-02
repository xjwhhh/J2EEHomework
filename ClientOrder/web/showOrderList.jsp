<%@ page import="com.sun.org.apache.xpath.internal.operations.Or" %><%--
  Created by IntelliJ IDEA.
  User: xjwhhh
  Date: 2018/1/2
  Time: 21:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>html>
<head>
    <title>ShowOrderList</title>
</head>
<body>

<h1>show your orderList</h1>

<jsp:useBean id="OrderList" type="entity.OrderList" scope="session"/>

<jsp:useBean id="order" type="entity.Order" />


<%
    for (int i = 0; i < OrderList.getOrderList().size(); i++) {
//        pageContext.setAttribute("order", OrderList.getOrder(i));
    }
%>
<TR>
    <%--<TD align="center"><jsp:getProperty name="order" property="id" /></TD>--%>
    <%--<TD align="center"><jsp:getProperty name="item" property="companyName"/></TD>--%>
    <%--<TD align="center"><jsp:getProperty name="item"--%>
                                        <%--property="type" /></TD>--%>
    <%--<TD align="center"><jsp:getProperty name="item"--%>
                                        <%--property="price" /></TD>--%>
    <%--<TD align="center"><jsp:getProperty name="item"--%>
                                        <%--property="date" /></TD>--%>
</TR>


</body>
</html>
