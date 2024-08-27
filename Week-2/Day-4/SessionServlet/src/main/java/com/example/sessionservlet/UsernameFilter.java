package com.example.sessionservlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class UsernameFilter implements jakarta.servlet.Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        jakarta.servlet.Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String username = ((HttpServletRequest) request).getParameter("name");

        if(username != null && username.contains(" "))
        {
            throw new ServletException();
        }
        System.out.println("username contains space/s");

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        jakarta.servlet.Filter.super.destroy();
    }

}
