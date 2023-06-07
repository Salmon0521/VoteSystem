package model;

public class Admin extends User {

    public Admin(String account, String password, int privilege) {
        this.account = account;
        this.password = password;
        this.privilege = privilege;
    }
}
