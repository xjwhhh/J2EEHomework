<%@ page import="com.sun.org.apache.xpath.internal.operations.Or" %>
<%@ page import="entity.Order" %>
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

<jsp:useBean id="order" class="entity.Order" />
<jsp:useBean id="orderRecordList" class="entity.OrderRecordList" />
<jsp:useBean id="orderRecord" class="entity.OrderRecord"/>

<H4>
    <BR>
    <TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
        <TBODY>

        <%
            System.out.println(orderList.getOrderList().size());
            for (int i = 0; i < orderList.getOrderList().size(); i++) {
//                System.out.println(orderList.getOrder(i).getId());
//                pageContext.setAttribute("order", orderList.getOrder(i));
//                pageContext.setAttribute("orderRecordList",order.getRecords());
//
//            System.out.println(order.getId());
//            System.out.println(orderRecordList.getOrderRecordList());

//            System.out.println(pageContext.getAttribute("order"));
//            order=(Order) pageContext.getAttribute("order");
//            System.out.println(t.getId());
                order=orderList.getOrder(i);
                System.out.println(order.getId());
                orderRecordList=order.getRecords();

                System.out.println(orderRecordList.getOrderRecordList());


            if(orderRecordList.getOrderRecordList()!=null){
            for(int j=0;j<orderRecordList.getOrderRecordList().size();j++){
//               pageContext.setAttribute("orderRecord",orderRecordList.getOrderRecordList().get(j));
                System.out.println(orderRecordList.getOrderRecord(j).getGoodsId());
                orderRecord=orderRecordList.getOrderRecord(j);

        %>

        <TR>
            <TH width="10%">id</TH>
            <TH width="10%">orderTime</TH>
            <TH width="10%">goodsId</TH>
            <TH width="10%">name</TH>
            <TH width="10%">number</TH>
            <TH width="10%">price</TH>
            <TH width="10%">supply</TH>
        </TR>


       <tr>
           <TD align="center">
               <jsp:getProperty name="order" property="id"/>
           </TD>
           <TD align="center">
               <jsp:getProperty name="order" property="orderTime"/>
           </TD>
            <td align="center"><jsp:getProperty name="orderRecord" property="goodsId"/> </td>
            <td align="center"><jsp:getProperty name="orderRecord" property="name"/> </td>
            <td align="center"><jsp:getProperty name="orderRecord" property="number"/> </td>
            <td align="center"><jsp:getProperty name="orderRecord" property="price"/> </td>

           <%
               
           %>


       </tr>


        <%}%>
        <%}%>
        <%}%>
        </TBODY>
    </TABLE>
</H4>


</body>
</html>
