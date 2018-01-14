<%@ page import="com.sun.org.apache.xpath.internal.operations.Or" %>
<%@ page import="entity.*" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: xjwhhh
  Date: 2018/1/2
  Time: 21:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>ShowOrderList</title>
</head>
<body>

<h1>show your orderList</h1>

<jsp:useBean id="orderList" type="entity.OrderList" scope="session"/>

<%--<jsp:useBean id="order" class="entity.Order" />--%>
<%--<jsp:useBean id="orderRecordList" class="entity.OrderRecordList" />--%>
<%--<jsp:useBean id="orderRecord" class="entity.OrderRecord"/>--%>

<H4>
    <BR>
    <TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
        <TBODY>

        <TR>
            <TH width="10%">orderId</TH>
            <TH width="10%">orderTime</TH>
            <TH width="10%">goodsId</TH>
            <TH width="10%">name</TH>
            <TH width="10%">number</TH>
            <TH width="10%">price</TH>
            <TH width="10%">supply</TH>
        </TR>

        <%
            System.out.println(orderList.getOrderList().size());
            for (int i = 0; i < orderList.getOrderList().size(); i++) {
//                pageContext.setAttribute("order", orderList.getOrder(i));
                Order order = orderList.getOrder(i);
                List<OrderRecord> orderRecordList = order.getRecords();

                    for (int j = 0; j < orderRecordList.size(); j++) {
//               pageContext.setAttribute("orderRecord",orderRecordList.getOrderRecordList().get(j));
//                System.out.println(orderRecordList.getOrderRecord(j).getGoodsId());
                        OrderRecord orderRecord = orderRecordList.get(j);

        %>


        <tr>
            <%--<TD align="center">--%>
            <%--<jsp:getProperty name="order" property="id"/>--%>
            <%--</TD>--%>
            <%--<TD align="center">--%>
            <%--<jsp:getProperty name="order" property="orderTime"/>--%>
            <%--</TD>--%>
            <%--<td align="center"><jsp:getProperty name="orderRecord" property="goodsId"/> </td>--%>
            <%--<td align="center"><jsp:getProperty name="orderRecord" property="name"/> </td>--%>
            <%--<td align="center"><jsp:getProperty name="orderRecord" property="number"/> </td>--%>
            <%--<td align="center"><jsp:getProperty name="orderRecord" property="price"/> </td>--%>

            <td align="center"><%=order.getId()%>
            </td>
            <td align="center"><%=order.getOrderTime()%>
            </td>
            <td align="center"><%=orderRecord.getGoodsId()%>
            </td>
            <td align="center"><%=orderRecord.getName()%>
            </td>
            <td align="center"><%=orderRecord.getNumber()%>
            </td>
            <td align="center"><%=orderRecord.getPrice()%>
            </td>
            <%
                if(orderRecord.getSupply()==0){
            %>
            <td align="center">缺货</td>
            <%}
            else{%>
                <td align="center">足货</td>
            <%}%>




        </tr>


        <%}%>
        <%}%>
        </TBODY>
    </TABLE>
</H4>

<p>总访问数：<%=application.getAttribute("allCounter")%>
</p>
<p>登录访问数：<%=application.getAttribute("loginCounter")%>
</p>
<p>游客访问数：<%=application.getAttribute("visitorCounter")%>
</p>

<form method="GET" action="<%=response.encodeURL(request.getContextPath() + "/login.jsp")%>">
    </p>
    <input type="submit" name="Logout" value="Logout">
</form>

</body>
</html>
