import Service.Course.CourseService;
import Service.User.UserService;
import Table.Course;
import Table.User;
import exceptions.CourseNotFoundException;
import exceptions.UserNotFoundException;

import java.io.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, UserNotFoundException, ClassNotFoundException {
        Course course = new Course(1, "Java");
        User user = new User(1, "Beyza", "b@gmail.com", course);

        UserService userService = new UserService();
        CourseService courseService = new CourseService();

        /*
        CREATE DATABASE IF NOT EXISTS testdb;
        USE testdb;

        CREATE TABLE IF NOT EXISTS courses (
            id INT PRIMARY KEY,
            name VARCHAR(100)
        );

        CREATE TABLE IF NOT EXISTS users (
            id INT PRIMARY KEY,
            name VARCHAR(100),
            email VARCHAR(100),
            courseId INT,
            FOREIGN KEY (courseId) REFERENCES courses(id)
        );
        */

        try {
            courseService.addCourse(course);
            userService.addUser(user);
            System.out.println("Added successfully:\n" + user + "\n" + course);

            System.out.println("---------------------------");

            System.out.println("Is there an user that have id: " + user.getId() + " in db?");

            if(userService.hasUser(1)) {
                System.out.println("YES: There is an user with that id in db.");
                User retrievedUser = userService.getUserById(1);
                System.out.println(retrievedUser + "\n" + retrievedUser.getCourse().toString());
            } else {
                System.out.println("NO: There is no user with that id in db.");
            }

            System.out.println("---------------------------");

            System.out.println("USER DELETING..");
            // first deleted the user because of keys
            userService.deleteUser(1);
            System.out.println("COURSE DELETING..");
            courseService.deleteCourse(1);

            System.out.println("---------------------------");

            System.out.println("Is there an user that have id: " + user.getId() + " in db?");

            if(userService.hasUser(1)) {
                System.out.println("YES: There is an user with that id.");
                User retrievedUser = userService.getUserById(1);
                System.out.println(retrievedUser + "\n" + retrievedUser.getCourse().toString());
            } else {
                System.out.println("NO: There is no user with that id.");
            }
        } catch (SQLException | CourseNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}