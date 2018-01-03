<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: xjwhhh
  Date: 2018/1/2
  Time: 18:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>Login</title>
</head>
<body>

<h1>hello</h1>

<%!
    int allCounter;
    int visitorCounter;
    int loginCounter;
    ServletContext context;

%>

<%context=application;%>

<%!
    private void refreshCounter() {
        allCounter = Integer.parseInt(String.valueOf(context.getAttribute("allCounter")));
        visitorCounter = Integer.parseInt(String.valueOf(context.getAttribute("visitorCounter")));
        loginCounter = Integer.parseInt(String.valueOf(context.getAttribute("loginCounter")));

        System.out.println("allCounter:" + allCounter);
        System.out.println("loginCounter:" + loginCounter);
        System.out.println("visitorCounter:" + visitorCounter);
    }
%>

<%
    System.out.println("doGet");

    Map paraMap = request.getParameterMap();

    String login = "";

    Cookie cookie = null;
    Cookie[] cookies = request.getCookies();

    if (null != cookies) {
        // Look through all the cookies and see if the
        // cookie with the login info is there.
        for (int i = 0; i < cookies.length; i++) {
            cookie = cookies[i];
            if (cookie.getName().equals("LoginCookie")) {
                login = cookie.getValue();
                break;
            }
        }
    }

    refreshCounter();

    // Logout action removes session, but the cookie remains
    // 注销
    System.out.println("sdfghj" + session.getAttribute("isShowReload"));
    if (null != request.getParameter("Logout")) {
        System.out.println("destroy session");
        if (null != session) {
            if (session.getAttribute("isShowReload") != null && String.valueOf(session.getAttribute("isShowReload")).equals("true")) {

                session.invalidate();
                session = null;
                refreshCounter();
                System.out.println("Logout");
            }
        }
    }

    refreshCounter();

    //普通登录
//    if (!paraMap.containsKey("reLogin")) {
//        System.out.println("普通登录");
        session = request.getSession(true);
        session.setAttribute("isShowReload", "false");

        System.out.println("Login");
//    }

    refreshCounter();


%>

<form method="post" action="<%=response.encodeURL(request.getContextPath() + "/jspShowOrder")%>">
    account:<input type="text" name="account" value="">
    password:<input type="password" name="password" value="">
    <input type="submit" name="submit" value="submit">
</form>

<p>总访问数：<%=application.getAttribute("allCounter")%>
</p>
<p>登录访问数：<%=application.getAttribute("loginCounter")%>
</p>
<p>游客访问数：<%=application.getAttribute("visitorCounter")%>
</p>



</body>
</html>
