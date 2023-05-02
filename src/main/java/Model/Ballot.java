package Model;

import util.Utility;
import java.util.Date;

public class Ballot {
    private String uuid = "";
    private String candidateUUID = "";
    private Date votingTime = null;

    public Ballot(String candidateId) {
        setUUID();
        setCandidateUUID(candidateId);
        setVotingTime();
    }
    public String getUUID() {
        return this.uuid.toString();
    }
    public void setUUID() {
        this.uuid = Utility.generateUUID();
    }
    public String getCandidateUUID() {
        return candidateUUID;
    }
    public void setCandidateUUID(String candidateUUID) {
        this.candidateUUID = candidateUUID;
    }
    public void setVotingTime() {
        this.votingTime = new Date();
    }
}
