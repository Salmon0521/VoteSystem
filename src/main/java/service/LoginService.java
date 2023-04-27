package service;

import Model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class LoginService {
    List<User> users = new ArrayList();

    public LoginService() {
        Path path = Paths.get("");
        String directoryName = path.toAbsolutePath().toString();
        System.out.println("Current Working Directory is = " +directoryName);
        try
        {
            String line = "";
            final String delimiter = ",";
            String filePath = "D:\\OOAD\\VoteSystem\\src\\main\\resources\\user/Paticipant.csv";
            FileReader fileReader = new FileReader(filePath);

            BufferedReader reader = new BufferedReader(fileReader);
            while ((line = reader.readLine()) != null)   //loops through every line until null found
            {
                String[] token = line.split(delimiter);    // separate every token by comma
                System.out.println(token[0] + " | "+ token[1]+ " | "+ token[2]+ " | "+ token[3]);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }/*
        users.add(new User("admin", "admin"));
        users.add(new User("user", "user"));*/
    }

    public boolean login(String username, String password) {
        return true;
    }

    public boolean logout(String username) {
        return true;
    }

    private boolean checkUsername(String username) {
        return true;
    }
}
