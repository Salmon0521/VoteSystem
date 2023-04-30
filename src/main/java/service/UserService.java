package service;

import Model.Admin;
import Model.Participant;
import Model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class UserService {
    List<User> users = new ArrayList();

    public UserService() {
        try
        {
            String line = "";
            final String delimiter = ",";
            String filePath = this.getClass().getResource("/").getPath();
            FileReader fileReader = new FileReader(filePath+"/user/User.csv");

            BufferedReader reader = new BufferedReader(fileReader);
            while ((line = reader.readLine()) != null)   //loops through every line until null found
            {
                String[] token = line.split(delimiter);    // separate every token by comma
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
    }

    public Integer login(String account, String password) {
        for (User user : users) {
            if (checkLogin(user, account, password)) {
                return user.getPrivilege();
            }
        }
        return null;
    }

    private boolean checkLogin(User user, String account, String password) {
        if (user.getAccount().equals(account) && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    public void updateUserVotingStatus(String account, String ballotUUID) {
        for (User user : users) {
            if (user.getAccount().equals(account)) {
                if (user instanceof Participant) {
                    ((Participant) user).setBallotUUID(ballotUUID);
                    ((Participant) user).setVoted(true);
                }
            }
        }
    }

    public String getUserBallotUUID(String account) {
        for (User user : users) {
            if (user.getAccount().equals(account)) {
                if (user instanceof Participant) {
                    return ((Participant) user).getBallotUUID();
                }
            }
        }
        return null;
    }

    public boolean getVoted(String account) {
        for (User user : users) {
            if (user.getAccount().equals(account)) {
                if (user instanceof Participant) {
                    return ((Participant) user).getVoted();
                }
            }
        }
        return false;
    }
}
