import exceptions.UserArrayFullException;
import exceptions.UserNotFoundException;

import java.io.*;
import java.util.Arrays;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws UserArrayFullException, IOException, UserNotFoundException {
        Course java = new Course(1, "Java");

        User b = new User(1, "Beyza", "b@gmail.com", java);

        UserMapService userMapService = new UserMapService();

        try {
            userMapService.addUser(b);
            System.out.println("Eklendi : " + b.getName());

            System.out.println("1 id'sine sahip kullanıcının ders ismi: " + userMapService.getUserById(1).getCourse().getName());

            userMapService.deleteUser(1);
            System.out.println("Silindi : " + b.getName());

            if(userMapService.hasUser(1)) {
                System.out.println(b.getName() + " bulunuyor.");
            } else {
                System.out.println(b.getName() + " bulunmuyor.");
            }

        } catch (UserArrayFullException | UserNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}