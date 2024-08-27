package Table;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;
    private String email;
    transient private Course course;

    public User(int id, String name, String email, Course course) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email + "]";
    }
}
