package service;

import Dao.UserDao;
import Dao.UserDaoImpl;
import model.Participant;
import model.User;

import java.util.List;


public class UserService {
    private List<User> users;

    public UserService() {
        UserDao userDao = new UserDaoImpl();
        users = userDao.getAllUsers();
    }

    public List<User> getUsers() {
        return users;
    }

    public boolean checkAccount(String account) {
        for (User user : users) {
            if (user.getAccount().equals(account)){
                return true;
            }
        }
        return false;
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
        else {
            return false;
        }
    }

    public void updateUserVoted(String account, String ballotUUID) {
        for (User user : users) {
            if (user.getAccount().equals(account) && user instanceof Participant) {
                    ((Participant) user).setBallotUUID(ballotUUID);
                    ((Participant) user).setVoted(true);
            }
        }
    }

    public String getUserBallotUUID(String account) {
        for (User user : users) {
            if (user.getAccount().equals(account) && user instanceof Participant) {
                    return ((Participant) user).getBallotUUID();
            }
        }
        return null;
    }

    public boolean getUserVoted(String account) {
        for (User user : users) {
            if (user.getAccount().equals(account) && user instanceof Participant) {
                    return ((Participant) user).getVoted();
            }
        }
        return false;
    }

    public void resetUserVoted() {
        for (User user : users) {
            if (user instanceof Participant) {
                ((Participant) user).setVoted(false);
                ((Participant) user).setBallotUUID("");
            }
        }
    }
}
