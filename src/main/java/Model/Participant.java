package Model;

public class Participant extends User{
    private boolean voteStatus = false;

    public Participant(String account, String password) {
        this.account = account;
        this.password = password;
    }
}
