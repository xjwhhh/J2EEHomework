package tag;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

public class checkSessionHandler extends BodyTagSupport {

    @Override
    public int doEndTag() throws JspException {

        ServletRequest request=pageContext.getRequest();
        ServletResponse response=pageContext.getResponse();
        JspWriter out = pageContext.getOut();
        String account = request.getParameter("account");
        boolean isLoginAction = (null == account) ? false : true;
        try {
            if (isLoginAction) {
                out.println("<p>you are logging</p>");
            } else {
                out.println("<p>you didn't login</p>");
                out.println("<form method='GET' action='http://localhost:8080/login.jsp'>");
                out.println("</p>");
                out.println("<input type='submit' name='reLogin' value='reLogin'>");
                out.println("</form>");
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return super.doEndTag();
    }
}
