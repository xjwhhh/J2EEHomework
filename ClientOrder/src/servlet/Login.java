package servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.util.Map;

public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    int allCounter;
    int visitorCounter;
    int loginCounter;

    public Login() {
        super();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet");

        Map paraMap = request.getParameterMap();

        String login = "";
        HttpSession session = request.getSession(true);

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
//        if (!paraMap.containsKey("reLogin")) {
//            System.out.println("普通登录");
        session = request.getSession(true);
        session.setAttribute("isShowReload", "false");

        System.out.println("Login");
//        }

        refreshCounter();

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        out.println(
                "<form method='POST' action='"
                        + response.encodeURL(request.getContextPath() + "/jspShowOrder")
                        + "'>");
        out.println(
                "account: <input type='text' name='account' value='" + login + "'>");
        out.println(
                "password: <input type='password' name='password' value=''>");
        out.println("<input type='submit' name='Submit' value='Submit'>");

        out.println("</form>");
        out.println("<p>总访问数：" + allCounter + "</p>");
        out.println("<p>登录访问数：" + loginCounter + "</p>");
        out.println("<p>游客访问数：" + visitorCounter + "</p>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void refreshCounter() {
        ServletContext context = getServletContext();
        allCounter = Integer.parseInt(String.valueOf(context.getAttribute("allCounter")));
        visitorCounter = Integer.parseInt(String.valueOf(context.getAttribute("visitorCounter")));
        loginCounter = Integer.parseInt(String.valueOf(context.getAttribute("loginCounter")));

        System.out.println("allCounter:" + allCounter);
        System.out.println("loginCounter:" + loginCounter);
        System.out.println("visitorCounter:" + visitorCounter);
    }

}
