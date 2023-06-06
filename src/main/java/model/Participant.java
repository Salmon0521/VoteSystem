package model;

public class Participant extends User{
    private String ballotUUID = "";
    private boolean voted = false;

    public Participant(String account, String password, int privilege) {
        this.account = account;
        this.password = password;
        this.privilege = privilege;
    }

    public String getBallotUUID() {
        return ballotUUID;
    }

    public void setBallotUUID(String ballotUUID) {
        this.ballotUUID = ballotUUID;
    }

    public boolean getVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }
}
