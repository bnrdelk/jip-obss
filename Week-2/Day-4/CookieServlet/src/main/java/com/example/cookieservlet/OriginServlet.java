package com.example.cookieservlet;

import java.io.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "originServlet", value = "/origin-servlet")
public class OriginServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        Cookie cookie = new Cookie("cookie", "123");
        response.addCookie(cookie);
        Cookie cookie1 = new Cookie("cookie2", "1234");
        response.addCookie(cookie1);
        Cookie cookie2 = new Cookie("cookie3", "12345");
        response.addCookie(cookie2);

        //response.sendRedirect("target-servlet");
        RequestDispatcher dispatcher = request.getRequestDispatcher("target-servlet");
        dispatcher.forward(request, response);
    }

    public void destroy() {
    }
}