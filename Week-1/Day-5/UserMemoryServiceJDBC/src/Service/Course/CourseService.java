package Service.Course;

import Table.Course;
import exceptions.CourseNotFoundException;

import java.sql.*;

public class CourseService implements ICourseService {
    Connection conn ;

    public CourseService () throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/testdb", "root", "test");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Course getCourseById(int courseId) throws SQLException {
        String sql = "SELECT id, name FROM courses WHERE id = ?";
        try (PreparedStatement preSelect = conn.prepareStatement(sql)) {
            preSelect.setInt(1, courseId);
            ResultSet rs = preSelect.executeQuery();
            if (rs.next()) {
                return new Course(rs.getInt("id"), rs.getString("name"));
            } else {
                throw new SQLException("Course not found.");
            }
        }
    }

    @Override
    public void addCourse(Course newCourse) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO courses (id, name) VALUES(?,?) ");
        preparedStatement.setInt(1, newCourse.getId());
        preparedStatement.setString(2, newCourse.getName());

        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteCourse(int id) throws CourseNotFoundException, SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM courses WHERE id = ?");
        preparedStatement.setInt(1, id);
        int rowsDeleted = preparedStatement.executeUpdate();
        if(rowsDeleted == 0) {
            throw new CourseNotFoundException("Course not found.");
        }
        System.out.println(rowsDeleted + " rows deleted.");
    }

    @Override
    public boolean hasCourse(int id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT id FROM courses WHERE id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }
}
