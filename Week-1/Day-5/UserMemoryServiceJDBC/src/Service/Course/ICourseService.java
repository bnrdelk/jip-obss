package Service.Course;
import Table.Course;
import exceptions.CourseNotFoundException;

import java.sql.SQLException;

public interface ICourseService {
    Course getCourseById(int courseId) throws SQLException;
    void addCourse(Course newCourse) throws SQLException;
    void deleteCourse(int id) throws CourseNotFoundException, SQLException;
    boolean hasCourse(int id) throws SQLException;
}
