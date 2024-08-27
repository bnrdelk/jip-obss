package com.example.forwardredirectservlet;

import java.io.*;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class ForwardRedirectServlet extends HttpServlet {
    private String method;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        method = getServletConfig().getInitParameter("method");
        if ("forward".equals(method)) {
            request.getRequestDispatcher("forward.jsp").forward(request, response);
        } else if ("redirect".equals(method)) {
            response.sendRedirect(request.getContextPath() + "redirect.jsp");
        } else {
            response.getWriter().write("INVALID!");
        }
    }
}