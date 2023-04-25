package Model;

import java.util.Date;
import java.util.UUID;

public class Ballot {
    private UUID uuid = null;
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
        this.uuid = UUID.randomUUID();
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
