package com.example.headerfooterservlet;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String footerMsg = request.getParameter("footerMsg");
        if (footerMsg == null || footerMsg.trim().isEmpty()) {
            footerMsg = "footer is dynamic.";
        }

        String headerMsg = "header is static.";

        request.setAttribute("headerMsg", headerMsg);
        request.setAttribute("footerMsg", footerMsg);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/main.jsp");
        dispatcher.include(request, response);
    }

}