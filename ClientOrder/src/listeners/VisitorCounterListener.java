package listeners;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class VisitorCounterListener implements ServletContextListener, ServletContextAttributeListener, HttpSessionListener, HttpSessionAttributeListener {
    int allCounter;
    int loginCounter;
    int visitorCounter;

    String allCounterFilePath = "C:\\Users\\xjwhh\\IdeaProjects_Ultimate\\J2EEHomework\\work01\\ClientOrder\\web\\file\\allCounter.txt";
    String loginCounterFilePath = "C:\\Users\\xjwhh\\IdeaProjects_Ultimate\\J2EEHomework\\work01\\ClientOrder\\web\\file\\loginCounter.txt";
    String visitorCounterFilePath = "C:\\Users\\xjwhh\\IdeaProjects_Ultimate\\J2EEHomework\\work01\\ClientOrder\\web\\file\\visitorCounter.txt";

    public void contextInitialized(ServletContextEvent contextEvent) {
        try {
            BufferedReader reader1 = new BufferedReader(new FileReader(allCounterFilePath));
            allCounter = Integer.parseInt(reader1.readLine());
            reader1.close();
            System.out.println("readingAllCounter: " + allCounter);
            BufferedReader reader2 = new BufferedReader(new FileReader(loginCounterFilePath));
            loginCounter = Integer.parseInt(reader2.readLine());
            reader2.close();
            System.out.println("readingLoginCounter: " + loginCounter);
            BufferedReader reader3 = new BufferedReader(new FileReader(visitorCounterFilePath));
            visitorCounter = Integer.parseInt(reader3.readLine());
            reader3.close();
            System.out.println("readingVisitorCounter: " + visitorCounter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ServletContext servletContext = contextEvent.getServletContext();
        servletContext.setAttribute("allCounter", Integer.toString(allCounter));
        servletContext.setAttribute("loginCounter", Integer.toString(loginCounter));
        servletContext.setAttribute("visitorCounter", Integer.toString(visitorCounter));
        System.out.println("Application initialized");
    }

    public void attributeAdded(ServletContextAttributeEvent event) {
        System.out.println("ServletContextAttribute added");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(allCounterFilePath));
            allCounter = Integer.parseInt(reader.readLine());
            reader.close();
            System.out.println("readCounter: " + allCounter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void attributeReplaced(ServletContextAttributeEvent event) {
        System.out.println("ServletContextAttribute replaced");
        writeCounter(event);
    }

    synchronized void writeCounter(ServletContextAttributeEvent event) {
//        System.out.println("3333333333333333333333333");
        switch (event.getName()) {
            case "allCounter":
                writeAllCounter(event);
                break;
            case "loginCounter":
                writeLoginCounter(event);
                break;
            case "visitorCounter":
                writeVisitorCounter(event);
                break;
            default:
                System.out.println("Error_Counter");

        }
        showCounter();

    }

    synchronized void writeAllCounter(ServletContextAttributeEvent event) {
        ServletContext servletContext = event.getServletContext();
        allCounter = Integer.parseInt(String.valueOf(servletContext.getAttribute("allCounter")));
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(allCounterFilePath));
            writer.write(Integer.toString(allCounter));
            writer.close();
            System.out.println("Writing AllCounter");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    synchronized void writeLoginCounter(ServletContextAttributeEvent event) {
        ServletContext servletContext = event.getServletContext();
        loginCounter = Integer.parseInt(String.valueOf(servletContext.getAttribute("loginCounter")));
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(loginCounterFilePath));
            writer.write(Integer.toString(loginCounter));
            writer.close();
            System.out.println("Writing LoginCounter");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    synchronized void writeVisitorCounter(ServletContextAttributeEvent event) {
        ServletContext servletContext = event.getServletContext();
        visitorCounter = Integer.parseInt(String.valueOf(servletContext.getAttribute("visitorCounter")));
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(visitorCounterFilePath));
            writer.write(Integer.toString(visitorCounter));
            writer.close();
            System.out.println("Writing VisitorCounter");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContext servletContext) {
        System.out.println("Application shut down");

    }

    public void sessionCreated(HttpSessionEvent se) {
        ServletContext context = se.getSession().getServletContext();
        int allCounter = Integer.parseInt(String.valueOf(context.getAttribute("allCounter")));
        int visitorCounter = Integer.parseInt(String.valueOf(context.getAttribute("visitorCounter")));
        int loginCounter = Integer.parseInt(String.valueOf(context.getAttribute("loginCounter")));
        HttpSession session = se.getSession();

        if (session.getAttribute("userId") != null) {
            System.out.println("登录");
            loginCounter++;
            context.setAttribute("loginCounter", loginCounter);
        } else {
            System.out.println("游客");
            visitorCounter++;
            context.setAttribute("visitorCounter", visitorCounter);
        }
        allCounter++;
        context.setAttribute("allCounter", allCounter);
        System.out.println("session 创建");
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        ServletContext context = se.getSession().getServletContext();
        int allCounter = Integer.parseInt(String.valueOf(context.getAttribute("allCounter")));
        int visitorCounter = Integer.parseInt(String.valueOf(context.getAttribute("visitorCounter")));
        int loginCounter = Integer.parseInt(String.valueOf(context.getAttribute("loginCounter")));
        HttpSession session = se.getSession();

        if (session.getAttribute("userId") != null) {
            System.out.println("登录");
            loginCounter--;
            context.setAttribute("loginCounter", loginCounter);
        } else {
            System.out.println("游客");
            visitorCounter--;
            context.setAttribute("visitorCounter", visitorCounter);
        }
        allCounter--;
        context.setAttribute("allCounter", allCounter);
        System.out.println("session 销毁");
    }

    public void attributeAdded(HttpSessionBindingEvent se) {

//        ServletContext context=se.getSession().getServletContext();
//        int allCounter=Integer.parseInt(String.valueOf(context.getAttribute("allCounter")));
//        int visitorCounter=Integer.parseInt(String.valueOf(context.getAttribute("visitorCounter")));
//        int loginCounter=Integer.parseInt(String.valueOf(context.getAttribute("loginCounter")));
//        HttpSession session=se.getSession();
//
//        if(String.valueOf(se.getName()).equals("userId")){
//            visitorCounter--;
//            context.setAttribute("visitorCounter",visitorCounter);
//            allCounter--;
//            context.setAttribute("allCounter",allCounter);
//            System.out.println("修改");
//        }

    }

    public void attributeRemoved(HttpSessionBindingEvent se) {
    }

    public void attributeReplaced(HttpSessionBindingEvent se) {
    }

    private void showCounter() {
//        try {
//            BufferedReader reader1 = new BufferedReader(new FileReader(allCounterFilePath));
//            int allCounter = Integer.parseInt(reader1.readLine());
//            reader1.close();
//            BufferedReader reader2 = new BufferedReader(new FileReader(loginCounterFilePath));
//            int loginCounter = Integer.parseInt(reader2.readLine());
//            reader2.close();
//            BufferedReader reader3 = new BufferedReader(new FileReader(visitorCounterFilePath));
//            int visitorCounter = Integer.parseInt(reader3.readLine());
//            reader3.close();
//            System.out.println("allCounter:" + allCounter);
//            System.out.println("loginCounter:" + loginCounter);
//            System.out.println("visitorCounter:" + visitorCounter);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
