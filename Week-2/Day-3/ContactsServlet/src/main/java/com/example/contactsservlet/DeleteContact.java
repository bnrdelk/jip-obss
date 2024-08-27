package com.example.contactsservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

@WebServlet(name = "deleteContact", value = "/delete-contact")
public class DeleteContact extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/contactsdb", "root", "test");

            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Contacts WHERE id = ?");
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Contact contact = new Contact(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("tel_number"));
                request.setAttribute("contact", contact);
                request.getRequestDispatcher("/delete-contact.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "Contact not found.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }

            statement.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {
        String name = req.getParameter("name");
        String gsm = req.getParameter("gsm");
        int id = Integer.parseInt(req.getParameter("id"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/contactsdb", "root", "test");
            PreparedStatement statement = conn.prepareStatement("DELETE FROM Contacts WHERE id = ?");
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                Contact contact = new Contact(name, gsm);
                req.setAttribute("contact", contact);
                req.getRequestDispatcher("/success.jsp").forward(req, resp); //redirect
            } else {
                req.setAttribute("message", "No rows deleted.");
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