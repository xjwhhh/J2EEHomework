package servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Login() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet");

        Map paraMap=request.getParameterMap();


        String login = "";
        HttpSession session = request.getSession(false);
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

        // Logout action removes session, but the cookie remains
        if (null != request.getParameter("Logout")) {
            if (null != session) {
                session.invalidate();
                session = null;
            }
        }

        ServletContext context=getServletContext();
        int allCounter=Integer.parseInt((String) context.getAttribute("allCounter"));
        int visitorCounter=Integer.parseInt((String) context.getAttribute("visitorCounter"));
        int loginCounter=Integer.parseInt((String) context.getAttribute("loginCounter"));
        //不是重新登录，也不是注销
        if(null==request.getParameter("Logout")&&(!paraMap.containsKey("reLogin"))){
            System.out.println("Login");
            allCounter++;
            visitorCounter++;
            context.setAttribute("allCounter",Integer.toString(allCounter));
            context.setAttribute("visitorCounter",Integer.toString(visitorCounter));
        }

        //注销
        if(null!=request.getParameter("Logout")){
            System.out.println("Logout");
            loginCounter--;
            visitorCounter++;
            context.setAttribute("loginCounter",Integer.toString(loginCounter));
            context.setAttribute("visitorCounter",Integer.toString(visitorCounter));

        }

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        out.println(
                "<form method='POST' action='"
                        + response.encodeURL(request.getContextPath() + "/showOrder")
                        + "'>");
        out.println(
                "account: <input type='text' name='account' value='" + login + "'>");
        out.println(
                "password: <input type='password' name='password' value=''>");
        out.println("<input type='submit' name='Submit' value='Submit'>");

        out.println("</form>");
        out.println("<p>总访问数："+allCounter+"</p>");
        out.println("<p>登录访问数："+loginCounter+"</p>");
        out.println("<p>游客访问数："+visitorCounter+"</p>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
