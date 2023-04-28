package Model;

public class Participant extends User{
    private boolean voteStatus = false;

    public Participant(String account, String password, int privilege) {
        this.account = account;
        this.password = password;
        this.privilege = privilege;
    }

    public Participant(String account, String password, int privilege, String email) {
        this.account = account;
        this.password = password;
        this.privilege = privilege;
        this.email = email;
    }

    public boolean isVoteStatus() {
        return voteStatus;
    }

    public void setVoteStatus(boolean voteStatus) {
        this.voteStatus = voteStatus;
    }
}
