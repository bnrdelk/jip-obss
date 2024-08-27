package exceptions;

public class CourseNotFoundException extends Exception{
    public CourseNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}