package model;

public abstract class User {
    protected String account="";
    protected String password="";

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

}
