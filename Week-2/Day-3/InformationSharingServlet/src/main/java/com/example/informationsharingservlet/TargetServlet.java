package com.example.informationsharingservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/targetServlet")
public class TargetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String attr = (String) req.getAttribute("attr");
        resp.getWriter().write("forwarded value: " + attr);

        String contextAttr = (String) getServletContext().getAttribute("contextAttr");
        resp.getWriter().write("context value: " + contextAttr);
    }
}
