package servlet;

import entity.Order;
import entity.OrderList;
import entity.ResultMessage;
import factory.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.xml.transform.Result;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class JspShowOrder extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        boolean cookieFound = false;
        System.out.println("user.account : " + req.getParameter("account"));
        Cookie cookie = null;
        Cookie[] cookies = req.getCookies();
        if (null != cookies) {
            // Look through all the cookies and see if the
            // cookie with the login info is there.
            for (int i = 0; i < cookies.length; i++) {
                cookie = cookies[i];
                if (cookie.getName().equals("LoginCookie")) {
                    cookieFound = true;
                    break;
                }
            }
        }

        ServletContext context = getServletContext();
        int allCounter = Integer.parseInt(String.valueOf(context.getAttribute("allCounter")));
        int visitorCounter = Integer.parseInt(String.valueOf(context.getAttribute("visitorCounter")));
        int loginCounter = Integer.parseInt(String.valueOf(context.getAttribute("loginCounter")));

        if (String.valueOf(session.getAttribute("isShowReload")).equals("false")) {
            String account = req.getParameter("account");
            String password = req.getParameter("password");
            boolean isLoginAction = (null == account) ? false : true;
            int userId = -1;
            if ((userId = checkLogin(account, password)) != -1) {
                if (isLoginAction) { // User is logging in

                    visitorCounter--;
                    loginCounter++;
                    context.setAttribute("visitorCounter", Integer.toString(visitorCounter));
                    context.setAttribute("loginCounter", Integer.toString(loginCounter));

                    if (cookieFound) { // If the cookie exists update the value only
                        // if changed
                        if (!account.equals(cookie.getValue())) {
                            cookie.setValue(account);
                            resp.addCookie(cookie);
                        }
                    } else {
                        // If the cookie does not exist, create it and set value
                        cookie = new Cookie("LoginCookie", account);
                        cookie.setMaxAge(Integer.MAX_VALUE);
                        System.out.println("Add cookie");
                        resp.addCookie(cookie);
                    }

                    // create a session to show that we are logged in
//                    session = req.getSession(true);
                    session.setAttribute("account", account);

                    req.setAttribute("account", account);
                    session.setAttribute("userId", userId);
                    req.setAttribute("userId", userId);
                    getOrderList(req, resp);
//                    displayMyOrderListPage(req, resp);
//                    displayCounterPage(req, resp);
//                    displayLogoutPage(req, resp);

                } else {
                    System.out.println(account + " session null");
                    // Display the login page. If the cookie exists, set login
                    resp.sendRedirect(req.getContextPath() + "/Login");
                }
            } else {

//                PrintWriter out = resp.getWriter();
//                // 用户名密码错误，重新登录
//                out.println("Sorry, your account or password is wrong, please try to login again");
//                out.println("<form method='GET' action='" + resp.encodeURL(req.getContextPath() + "/login") + "'>");
//                out.println("</p>");
//                out.println("<input type='submit' name='reLogin' value='reLogin'>");
//                out.println("</form>");
//                out.println("<p>Servlet is version @version@</p>");
////                displayCounterPage(req, resp);
//                out.println("</body></html>");

                resp.sendRedirect(req.getContextPath() + "/reLogin.jsp");

            }
            session.setAttribute("isShowReload", "true");
        } else {
            // 或未注销，重新加载该页面，session不为空
            String account = (String) session.getAttribute("account");
            String userId = String.valueOf(session.getAttribute("userId"));
            System.out.println("reload the page");

            req.setAttribute("account", account);
            req.setAttribute("userId", userId);
            getOrderList(req, resp);
//            displayMyOrderListPage(req, resp);
//            displayCounterPage(req, resp);
//            displayLogoutPage(req, resp);

        }

    }

    private int checkLogin(String account, String password) {
        return ServiceFactory.getUserManageService().login(account, password);

    }

    private void getOrderList(HttpServletRequest req, HttpServletResponse res) {
        int userId = Integer.valueOf(String.valueOf(req.getAttribute("userId")));
        ArrayList<Order> list = ServiceFactory.getOrderManageService().getOrderList(userId);
        OrderList orderList = new OrderList(list);
        HttpSession session = req.getSession();
        session.setAttribute("orderList", orderList);
        ServletContext context = getServletContext();
        try {
            context.getRequestDispatcher("/showOrderList.jsp").forward(
                    req, res);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }

    }

}
