package service;

import Model.Participant;
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
        try
        {
            String line = "";
            final String delimiter = ",";
            String filePath = this.getClass().getResource("/").getPath();
            FileReader fileReader = new FileReader(filePath+"/user/Paticipant.csv");

            BufferedReader reader = new BufferedReader(fileReader);
            while ((line = reader.readLine()) != null)   //loops through every line until null found
            {
                User user = new Participant();
                String[] token = line.split(delimiter);    // separate every token by comma
                System.out.println(token[0] + " | "+ token[1]+ " | "+ token[2]+ " | "+ token[3]);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
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
