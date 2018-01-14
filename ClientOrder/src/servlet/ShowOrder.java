package servlet;

import entity.Order;
import entity.OrderRecord;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * 说明：
 * 用户登录时进行检验，如果是正确的用户名密码则会显示该用户的orderList
 * 没有专门写一个警示界面，而是如果有缺货，则会在订单记录后面显示缺货，否则显示足货
 * 如果用户名密码错误，则会显示一个错误界面，可以重新登录
 */

public class ShowOrder extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataSource datasource = null;

    //声明Connection对象
    Connection con;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init() {

        InitialContext jndiContext = null;

        Properties properties = new Properties();
        properties.put(javax.naming.Context.PROVIDER_URL, "jnp:///");
        properties.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
        try {
            jndiContext = new InitialContext(properties);
            datasource = (DataSource) jndiContext.lookup("java:comp/env/jdbc/j2eeHomework");
            System.out.println("got context");
            System.out.println("About to get ds---ShowMyOrder");
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
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
            System.out.println("aaaaa");
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
                    displayMyOrderListPage(req, resp);
                    displayCounterPage(req, resp);
                    displayLogoutPage(req, resp);

                } else {
                    System.out.println(account + " session null");
                    // Display the login page. If the cookie exists, set login
                    resp.sendRedirect(req.getContextPath() + "/Login");
                }
            } else {

                PrintWriter out = resp.getWriter();
                // 用户名密码错误，重新登录
                out.println("Sorry, your account or password is wrong, please try to login again");
                out.println("<form method='GET' action='" + resp.encodeURL(req.getContextPath() + "/login") + "'>");
                out.println("</p>");
                out.println("<input type='submit' name='reLogin' value='reLogin'>");
                out.println("</form>");
                out.println("<p>Servlet is version @version@</p>");
                displayCounterPage(req, resp);
                out.println("</body></html>");
            }
            session.setAttribute("isShowReload", "true");
        } else {
            System.out.println("bbbb");
            // 或未注销，重新加载该页面，session不为空
            String account = (String) session.getAttribute("account");
            String userId = String.valueOf(session.getAttribute("userId"));
            System.out.println("reload the page");

            req.setAttribute("account", account);
            req.setAttribute("userId", userId);
            getOrderList(req, resp);
            displayMyOrderListPage(req, resp);
            displayCounterPage(req, resp);
            displayLogoutPage(req, resp);

        }

    }

    //检验账号密码是否匹配，若匹配返回userId，否则返回-1
    private int checkLogin(String account, String password) {
        int result = -1;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            con = datasource.getConnection();
            stmt = con.prepareStatement("select id from user where account = ? and password=?");
            stmt.setString(1, account);
            stmt.setString(2, password);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

    }

    //检验是否有货
    private boolean checkOrder(OrderRecord record) {
        PreparedStatement stmt = null;
        ResultSet result = null;
        int supplyNumber = 0;

        try {
            con = datasource.getConnection();
            stmt = con.prepareStatement("select number from supplyInfo where goodsId = ?");
            stmt.setString(1, String.valueOf(record.getGoodsId()));
            result = stmt.executeQuery();
            while (result.next()) {
                supplyNumber = result.getInt("number");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplyNumber < record.getNumber();
    }

    public void getOrderList(HttpServletRequest req, HttpServletResponse res) {

        PreparedStatement stmt = null;
        ResultSet result = null;
        ArrayList<Order> list = new ArrayList<>();
        Statement sm = null;

        try {
            con = datasource.getConnection();
            System.out.println("userId:" + req.getAttribute("userId"));
            stmt = con.prepareStatement("select id,orderTime from myOrder where userId = ?");
            System.out.println("test");
            stmt.setString(1, String.valueOf(req.getAttribute("userId")));
            result = stmt.executeQuery();
            while (result.next()) {
                Order order = new Order();
                order.setId(result.getInt("id"));
                order.setOrderTime(result.getString("orderTime"));
                list.add(order);
            }
            System.out.println(list.size());
            for (int i = 0; i < list.size(); i++) {
                Order order = list.get(i);
                stmt = con.prepareStatement("select goodsId,name,price,number from orderInfo where orderId = ?");
                stmt.setString(1, String.valueOf(order.getId()));
                result = stmt.executeQuery();
                ArrayList<OrderRecord> records = new ArrayList<>();
                while (result.next()) {
                    OrderRecord record = new OrderRecord();
                    record.setGoodsId(result.getInt("goodsId"));
                    record.setName(result.getString("name"));
                    record.setNumber(result.getInt("number"));
                    record.setPrice(result.getDouble("price"));
                    record.setSupply(result.getInt("supply"));
                    records.add(record);
                }
//                OrderRecordList orderRecordList = new OrderRecordList(records);
                order.setRecords(records);
                list.set(i, order);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        req.setAttribute("list", list);

    }

    public void displayCounterPage(HttpServletRequest req, HttpServletResponse res) throws IOException {
        PrintWriter out = res.getWriter();
        ServletContext context = getServletContext();
        int allCounter = Integer.parseInt(String.valueOf(context.getAttribute("allCounter")));
        int visitorCounter = Integer.parseInt(String.valueOf(context.getAttribute("visitorCounter")));
        int loginCounter = Integer.parseInt(String.valueOf(context.getAttribute("loginCounter")));
        out.println("<p>总访问数：" + allCounter + "</p>");
        out.println("<p>登录访问数：" + loginCounter + "</p>");
        out.println("<p>游客访问数：" + visitorCounter + "</p>");
    }

    public void displayLogoutPage(HttpServletRequest req, HttpServletResponse res) throws IOException {
        PrintWriter out = res.getWriter();

        // 注销Logout
        out.println("<form method='GET' action='" + res.encodeURL(req.getContextPath() + "/login") + "'>");
        out.println("</p>");
        out.println("<input type='submit' name='Logout' value='Logout'>");
        out.println("</form>");
        out.println("<p>Servlet is version @version@</p>");
        out.println("</body></html>");

    }

    public void displayMyOrderListPage(HttpServletRequest req, HttpServletResponse res) throws IOException {
        System.out.println("display orderList");
        ArrayList list = (ArrayList) req.getAttribute("list"); // resp.sendRedirect(req.getContextPath()+"/MyStockList");

        PrintWriter out = res.getWriter();
        out.println("<html><body>");
        out.println("<table width='650' border='0' >");
        out.println("<tr>");
        out.println("<td width='650' height='80' background='" + req.getContextPath() + "/image/top.jpg'>&nbsp;</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("<p>Welcome " + req.getAttribute("account") + "</p>");

        out.println("My Order List:  ");

        for (int i = 0; i < list.size(); i++) {
            Order order = (Order) list.get(i);
//            OrderRecordList orderRecords = order.getRecords();
            for (int j = 0; j < order.getRecords().size(); j++) {
                OrderRecord record = order.getRecords().get(j);
                out.print("orderId:" + order.getId() + "\tname:" + record.getName() + "\tprice:" + record.getPrice() + "\tnumber:" + record.getNumber());
                if (record.getSupply()==0) {
                    out.println("\t缺货");
                } else {
                    out.println("\t足货");
                }
            }
        }
        out.println("</p>");
        // 点击here，刷新该页面，会话有效
        out.println("Click <a href='" + res.encodeURL(req.getRequestURI()) + "'>here</a> to reload this page.<br>");
    }

}
