package com.example.firstwebapplication;

import java.io.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Welcome to first web application!";
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getMethod();
        System.out.println("service");
        if ("GET".equalsIgnoreCase(method)) {
            doGet(request, response);
        } else if ("POST".equalsIgnoreCase(method)) {
            doPost(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Method not allowed.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("get");
        response.setContentType("text/html");
        String name = request.getParameter("name");

        String destination = request.getParameter("destination");
        if (destination == null || destination.trim().isEmpty()) {
            destination = "index.jsp";
        } else if(destination.equals("google.jsp") || destination.equals("index.jsp") || destination.equals("error.jsp")) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(destination);
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("error.jsp");
        }
        try {

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("post");
        response.setContentType("text/html");
        String name = request.getParameter("name");
        response.getWriter().println("<p>Hello " + name + "!</p>");
    }

}