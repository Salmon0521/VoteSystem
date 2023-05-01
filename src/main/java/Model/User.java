package Model;

public abstract class User {
    protected String account="";
    protected String password="";
    protected String email="";

    protected int privilege = 0;

    public int getPrivilege() {
        return privilege;
    }
    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
