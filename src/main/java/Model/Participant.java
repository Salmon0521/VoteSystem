package Model;

public class Participant extends User{
    private String ballotUUID = "";

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

    public String getBallotUUID() {
        return ballotUUID;
    }

    public void setBallotUUID(String ballotUUID) {
        this.ballotUUID = ballotUUID;
    }
}
