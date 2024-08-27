package com.example.sessionservlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class Filter implements jakarta.servlet.Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        jakarta.servlet.Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String uri = ((HttpServletRequest)request).getServletPath();

        HttpSession session = ((HttpServletRequest) request).getSession();
        Boolean loggedIn = (Boolean) session.getAttribute("isUserLoggedIn");
        System.out.println(loggedIn);
        System.out.println(uri);
        if (uri.startsWith("/private")) {
            if(loggedIn == null || !loggedIn)
            {
                ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/login");
                return;
            }
            System.out.println("req starts with /private");
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        jakarta.servlet.Filter.super.destroy();
    }

}
