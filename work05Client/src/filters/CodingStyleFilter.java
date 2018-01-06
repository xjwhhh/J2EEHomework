package filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter("/ShowOrder")
public class CodingStyleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
//            HttpServletRequest request=(HttpServletRequest) servletRequest;
//            HttpServletResponse response=(HttpServletResponse) servletResponse;
            servletRequest.setCharacterEncoding("UTF-8");
//            request.setCharacterEncoding("UTF-8");
            System.out.println("filterDone");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
