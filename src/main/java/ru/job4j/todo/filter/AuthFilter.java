package ru.job4j.todo.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest sReq, ServletResponse sResp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) sReq;
        HttpServletResponse resp = (HttpServletResponse) sResp;
        String uri = req.getRequestURI();
        if (uri.endsWith("auth.do") || uri.endsWith("reg.do")) {
            chain.doFilter(sReq, sResp);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth.jsp");
            return;
        }
        chain.doFilter(sReq, sResp);
    }
}
