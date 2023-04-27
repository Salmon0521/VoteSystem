package Model;

public class Admin extends User {

    private boolean holdingStatus = false;

    public Admin(String account, String password) {
        this.account = account;
        this.password = password;
        this.privilege = 1;
    }
}
