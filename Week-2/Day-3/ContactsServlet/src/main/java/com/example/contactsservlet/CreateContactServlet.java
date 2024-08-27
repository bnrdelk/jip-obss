package com.example.contactsservlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

@WebServlet(name = "createContact", value = "/create-contact")
public class CreateContactServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {
        req.getRequestDispatcher("/create-contact.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {
        String name = req.getParameter("name");
        String gsm = req.getParameter("gsm");

        if (name == null || name.trim().isEmpty() || gsm == null || gsm.trim().isEmpty()) {
            System.out.println("Name and telephone number are required.");
            req.getRequestDispatcher("/create-contact.jsp").forward(req, resp);
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/contactsdb", "root", "test");
            PreparedStatement statement = conn.prepareStatement("INSERT INTO Contacts (name, tel_number) VALUES (?, ?)");
            statement.setString(1, name);
            statement.setString(2, gsm);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                    Contact contact = new Contact(name, gsm);
                    req.setAttribute("contact", contact);
                    req.getRequestDispatcher("/success.jsp").forward(req, resp);
                } else {
                    req.setAttribute("message", "No rows inserted.");
                    req.getRequestDispatcher("/create-contact.jsp").forward(req, resp);
                }

                statement.close();
                conn.close();

            } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
