package Model;

public class Admin extends User {

    private boolean holdingStatus = false;

    public Admin(String account, String password, int privilege) {
        this.account = account;
        this.password = password;
        this.privilege = privilege;
    }

    public Admin(String account, String password, int privilege, String email) {
        this.account = account;
        this.password = password;
        this.privilege = privilege;
        this.email = email;
    }
}
