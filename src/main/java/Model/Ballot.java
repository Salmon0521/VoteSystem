package Model;

import util.Utility;

import java.util.Date;

public class Ballot {
    private String uuid = "";
    private String candidateId = "";
    private Date votingTime = null;

    public Ballot(String candidateId) {
        setUUID();
        setCandidateId(candidateId);
        setVotingTime();
    }
    public String getUUID() {
        return this.uuid.toString();
    }
    public void setUUID() {
        this.uuid = Utility.generateUUID();
    }
    public String getCandidateId() {
        return candidateId;
    }
    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }
    public Date getVotingTime() {
        return votingTime;
    }
    public void setVotingTime() {
        this.votingTime = new Date();
    }
}
