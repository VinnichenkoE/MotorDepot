package com.vinnichenko.motorDepot.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.vinnichenko.motorDepot.controller.command.SessionParameter.*;

public class LanguageFilter implements Filter {

    private static final String LANGUAGE_PARAMETER = "lang";
    private static final String REFERER = "referer";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String lang = servletRequest.getParameter(LANGUAGE_PARAMETER);
        req.getSession().setAttribute(LANGUAGE, lang);
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        System.out.println(req.getHeader(REFERER));
        resp.sendRedirect(req.getHeader(REFERER));
    }

    @Override
    public void destroy() {
    }
}
