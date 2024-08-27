import exceptions.DataExpiredException;
import exceptions.DataNotFoundException;
import exceptions.FileNotSavedException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, DataExpiredException {
        FileDataStore fds = new FileDataStore("text.txt");
        InMemoryDataStore imds = new InMemoryDataStore();

        System.out.println("*File Data Store*");
        System.out.println("-----------------");
        /* File Data Store */
        try {
            fds.put("1", "11");
            System.out.println(fds.get("1"));

            fds.put("2","22", 4);
            System.out.println(fds.get("2"));
            //System.out.println(fds.get("0")); // uncomment -> no such a data
            Thread.sleep(5); // wait for 5 sec, so data be expired
            System.out.println(fds.get("2"));
        } catch (DataExpiredException | FileNotSavedException | DataNotFoundException e) {
            System.out.println(e.getMessage());
        }


        System.out.println("\n*InMemory Data Store*");
        System.out.println("----------------------");
        /* InMemory Data Store */
        try {
            imds.put("1","9000", 2);
            System.out.println(imds.get("1"));
            Thread.sleep(3); // wait for 3 sec, so data be expired
            //System.out.println(fds.get("0")); // uncomment -> no such a data
            System.out.println(imds.get("1"));
        } catch (DataExpiredException | DataNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }
}