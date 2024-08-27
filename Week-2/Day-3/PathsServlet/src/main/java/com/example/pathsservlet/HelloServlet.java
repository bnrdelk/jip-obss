package com.example.pathsservlet;

import java.io.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet",  value = "/hello-servlet/*")
public class HelloServlet extends HttpServlet {

    public void init() {}

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String pathInfo = request.getPathInfo();
        //System.out.println(pathInfo);

        if (pathInfo != null && pathInfo.contains("secured")) {
            //System.out.println(pathInfo);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Secured area");
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/static.html");
            dispatcher.forward(request, response);
        }
        //super.service(request,response);
    }

    public void destroy() {
    }
}