package com.example.contactsservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "searchContact", value = "/search-contact")
public class SearchContactServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");

        if (name == null || name.trim().isEmpty()) {
            req.setAttribute("message", "Search term is required.");
            req.getRequestDispatcher("/search-contact.jsp").forward(req, resp);
            return;
        }

        List<Contact> contacts = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/contactsdb", "root", "test");

            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Contacts WHERE name LIKE ?");
            statement.setString(1, "%" + name + "%");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String contactName = resultSet.getString("name");
                String telNumber = resultSet.getString("tel_number");
                contacts.add(new Contact(id, contactName, telNumber));
            }

            resultSet.close();
            statement.close();
            conn.close();

            if (contacts.isEmpty()) {
                req.getRequestDispatcher("/no-contact.jsp").forward(req, resp);
            } else {
                req.setAttribute("contacts", contacts);
                req.getRequestDispatcher("/found-list.jsp").forward(req, resp);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}