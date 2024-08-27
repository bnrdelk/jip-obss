package Service.User;
import Service.Course.CourseService;
import Table.Course;
import Table.User;
import exceptions.UserNotFoundException;

import java.sql.*;

public class UserService implements IUserService {
    private Connection conn;

    public UserService() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/testdb", "root", "test");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUser(User newUser) throws SQLException {
        PreparedStatement preInsert = conn.prepareStatement("INSERT INTO users (id, name, email, courseId) VALUES (?, ?, ?, ?)");
        preInsert.setInt(1, newUser.getId());
        preInsert.setString(2, newUser.getName());
        preInsert.setString(3, newUser.getEmail());
        preInsert.setInt(4, newUser.getCourse().getId());
        preInsert.executeUpdate();
    }

    @Override
    public void deleteUser(int id) throws UserNotFoundException, SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM users WHERE id = ?");
        preparedStatement.setInt(1, id);
        int rowsDeleted = preparedStatement.executeUpdate();
        if (rowsDeleted == 0) {
            throw new UserNotFoundException("User not found.");
        }
        System.out.println(rowsDeleted + " row/s deleted.");
    }

    @Override
    public boolean hasUser(int i) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT 1 FROM users WHERE id = ?");
        preparedStatement.setInt(1, i);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next(); // if there is next, (1) true
    }

    @Override
    public User getUserById(int id) throws UserNotFoundException, ClassNotFoundException {
        try (PreparedStatement preSelect = conn.prepareStatement("SELECT id, name, email, courseId FROM users WHERE id = ?")) {
            preSelect.setInt(1, id);
            ResultSet resultSet = preSelect.executeQuery();
            if (resultSet.next()) {
                CourseService courseService = new CourseService();
                int courseId = resultSet.getInt("courseId");
                Course course = courseService.getCourseById(courseId);
                return new User(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("email"), course);
            } else {
                throw new UserNotFoundException("User not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
