package model;

import util.Utility;

public class Ballot {
    private String uuid = "";
    private String candidateUUID = "";

    public Ballot(String candidateUUID) {
        setUUID();
        setCandidateUUID(candidateUUID);
    }
    public String getUUID() {
        return uuid;
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
}
