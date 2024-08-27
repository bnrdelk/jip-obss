import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Connection conn;

    static {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/classicmodels", "root","test");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Scanner scanner = new Scanner(System.in);
            String action = "", name = "";

            while (true) {
                System.out.println("Enter the action: search, insert, update, delete or exit");
                action = scanner.nextLine();

                switch (action) {
                    case "search": {
                        System.out.println("Please enter the contact name you want to search: ");
                        name = scanner.nextLine();

                        System.out.println("SEARCHING.. ");
                        searchContactName(name);
                        break;
                    }
                    case "insert": {
                        System.out.println("Please enter the contact name, surname, email you want to add: ");
                        name = scanner.nextLine();

                        String[] input = name.split(",");

                        System.out.println("INSERTING.. ");
                        insertContact(input);
                        break;
                    }
                    case "delete": {
                        System.out.println("Please enter the contact name that you want to delete: ");
                        name = scanner.nextLine();


                        System.out.println("DELETING.. ");
                        deleteContact(name);
                        break;
                    }
                    case "update": {
                        System.out.println("Please enter the contact id that you want to update: ");
                        int id = Integer.parseInt(scanner.nextLine());

                        System.out.println("Please enter the contact name, surname, email you want to change: ");

                        String[] input = scanner.nextLine().split(",");

                        System.out.println("UPDATING.. ");
                        updateContact(input, id);
                        break;
                    }
                    case "exit": {
                        System.out.println("EXITING.. ");
                        return;
                    }
                    default: {
                        System.out.println("PLEASE ENTER A VALID ACTION");
                        break;
                    }

                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        }

    private static void updateContact(String[] input, int id) throws SQLException {
        PreparedStatement preInsert = conn.prepareStatement("UPDATE contacts SET firstName = ?, lastName = ?, email = ? WHERE id = " + id);

        preInsert.setString(1, input[0]);
        preInsert.setString(2, input[1]);
        preInsert.setString(3, input[2]);
        int rowCount = preInsert.executeUpdate();

        System.out.println("updated row count:" + rowCount);
    }

    private static void insertContact(String[] input) throws SQLException {
        PreparedStatement preInsert = conn.prepareStatement("INSERT INTO contacts (firstName, lastName, email) VALUES (?, ?, ?)");

        preInsert.setString(1, input[0]);
        preInsert.setString(2, input[1]);
        preInsert.setString(3, input[2]);
        int rowCount = preInsert.executeUpdate();

        System.out.println("inserted row count:" + rowCount);
        /*
        PreparedStatement pre = conn.prepareStatement("SELECT * FROM contacts");
        ResultSet resultSet = pre.executeQuery();

        while (resultSet.next()) {
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String email = resultSet.getString("email");
            System.out.println(" firstName: " + firstName + "lastname: " + lastName + "email: " + email);
        }*/

    }

    private static void searchContactName (String name) throws SQLException {
        PreparedStatement pre = conn.prepareStatement("SELECT * FROM contacts WHERE firstName = ?");
        pre.setString(1, name);
        ResultSet resultSet = pre.executeQuery();


        while (resultSet.next()) {
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String email = resultSet.getString("email");
            System.out.println(" firstName: " + firstName + " lastname: " + lastName + " email: " + email);
        }

    }

    private static void deleteContact (String name) throws SQLException {
        PreparedStatement preDelete = conn.prepareStatement("DELETE FROM contacts WHERE lastName = ?");
        preDelete.setString(1, name);
        int rowCountDeleted = preDelete.executeUpdate();
        System.out.println("deleted row count:" + rowCountDeleted);

    }

/*
        // note: try with resources automatically closes the connection / resource
        try(
    static Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/classicmodels", "root","test")) {
            conn.setAutoCommit(false);

            PreparedStatement preUpdate = conn.prepareStatement("UPDATE contacts SET firstName = ? WHERE lastName = ?");
            preUpdate.setString(1, "changed");
            preUpdate.setString(2, "x");
            int rowCount = preUpdate.executeUpdate();

            PreparedStatement preSelectAfterUpdate = conn.prepareStatement("SELECT * FROM contacts");
            ResultSet resultSetUpdate = preSelectAfterUpdate.executeQuery();

            System.out.println("Updated row count: " + rowCount);

            while(resultSetUpdate.next()) {
                String firstName = resultSetUpdate.getString("firstName");
                String lastName = resultSetUpdate.getString("lastName");
                String email = resultSetUpdate.getString("email");
                System.out.println(" firstName: " + firstName + " :))))) lastname: " + lastName + " :))))) email: " + email);
            }

            conn.commit();


            PreparedStatement preDelete = conn.prepareStatement("DELETE FROM contacts WHERE lastName = ?");
            preDelete.setString(1, "x");
            int rowCountDeleted = preDelete.executeUpdate();

            PreparedStatement preSelectAfterDelete = conn.prepareStatement("SELECT * FROM contacts");
            ResultSet resultSetDelete = preSelectAfterDelete.executeQuery();

            while(resultSetDelete.next()) {
                String firstName = resultSetDelete.getString("firstName");
                String lastName = resultSetDelete.getString("lastName");
                String email = resultSetDelete.getString("email");
                System.out.println(" firstName: " + firstName + " :))))) lastname: " + lastName + " :))))) email: " + email);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
/*
            // note: PreparedStatement -> prevents SQL injection
            PreparedStatement preInsert = conn.prepareStatement("INSERT INTO contacts (firstName, lastName, email) VALUES (?, ?, ?)");
            preInsert.setString(1, "beyza");
            preInsert.setString(2, "nur");
            preInsert.setString(3, "b@b.com");

            preInsert.setString(1, "beyza");
            preInsert.setString(2, "Doe");
            preInsert.setString(3, "b@b.com");

            preInsert.setString(1, "x");
            preInsert.setString(2, "x");
            preInsert.setString(3, "x@b.com");

            preInsert.setString(1, "a");
            preInsert.setString(2, "a");
            preInsert.setString(3, "a@b.com");
            preInsert.executeUpdate();

            PreparedStatement preSelect = conn.prepareStatement("SELECT * FROM contacts WHERE firstName LIKE \'%x%\' OR lastName LIKE \'%b%\'");
            ResultSet resultSet = preSelect.executeQuery();

            while(resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                System.out.println(" firstName: " + firstName + " :))))) lastname: " + lastName + " :))))) email: " + email);
            }
            */

/*
// note: try with resources automatically closes the connection / resource
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/classicmodels", "root","test")) {
            // note: PreparedStatement -> prevents SQL injection
            PreparedStatement pre = conn.prepareStatement("SELECT country, AVG(creditLimit) as avg FROM customers WHERE creditLimit>5000 GROUP BY country HAVING avg > 100000;");

            ResultSet resultSet = pre.executeQuery();

            while(resultSet.next()) {
                String country = resultSet.getString("country");
                double avg = resultSet.getDouble("avg");
                System.out.println(" country: " + country + " :))))) credit limit AVG: " + avg);
            }

            pre.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // try with resources automatically closes the connection / resource
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/classicmodels", "root","test")) {
            Statement statement = conn.createStatement();
            int rowCount = statement.executeUpdate("UPDATE customers SET contactFirstName=\'Beyza\' WHERE customerNumber=103");

            System.out.println("Updated row count: " + rowCount);

            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        // try with resources automatically closes the connection / resource
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/classicmodels", "root","test")) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT customerNumber,contactFirstName, contactLastName  FROM customers");

            while(resultSet.next()) {
                int customerNumber = resultSet.getInt("customerNumber");
                String contactFirstName = resultSet.getString("contactFirstName");
                String contactLastName = resultSet.getString("contactLastName");
                System.out.println("Number: " + customerNumber + " Firstname: " + contactFirstName + " Firstname: " + contactLastName);
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
    }

