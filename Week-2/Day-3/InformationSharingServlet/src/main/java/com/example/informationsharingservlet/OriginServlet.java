package com.example.informationsharingservlet;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/originServlet")
public class OriginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        getServletContext().setAttribute("contextAttr", "contextVal");

        req.setAttribute("attr", "value");

        req.getRequestDispatcher("/targetServlet").forward(req, resp);
        //resp.sendRedirect("targetServlet");
    }
}
