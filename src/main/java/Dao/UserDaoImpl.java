package Dao;

import model.Admin;
import model.Participant;
import model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao{
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try
        {
            String line = "";
            final String delimiter = ",";
            String filePath = this.getClass().getResource("/").getPath();
            FileReader fileReader = new FileReader(filePath+"/user/User.csv");

            BufferedReader reader = new BufferedReader(fileReader);
            while ((line = reader.readLine()) != null)
            {
                String[] token = line.split(delimiter);
                if (token[2].equals("0")) {
                    User user = new Participant(token[0], token[1], Integer.parseInt(token[2]));
                    users.add(user);
                }
                else if (token[2].equals("1")){
                    User user = new Admin(token[0], token[1], Integer.parseInt(token[2]));
                    users.add(user);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return users;
    }
}
