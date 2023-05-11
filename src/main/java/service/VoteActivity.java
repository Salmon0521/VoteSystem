package service;

import model.*;

import java.util.*;

public class VoteActivity {
    private String id = "";
    private String name = "";
    private String introduction = "";
    private Date startTime = null;
    private Date endTime = null;
    private boolean status = false;
    private Ballots ballots = new Ballots();
    private Candidates candidates = new Candidates();
    private List<Map<String, String>> result = new ArrayList<>();

    public String vote(String voteData) {
        return ballots.addBallot(voteData);
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void reset() {
        ballots.removeAllBallot();
        candidates.removeAllCandidate();
        status = false;
    }

    public List<Candidate> getCandidates() {
        return candidates.getCandidates();
    }

    public List<Ballot> getBallots() {
        return ballots.getBallots();
    }

    public int countBallot() {
        return ballots.countBallots();
    }

    public void addCandidate(Map<String, String> candidateData) {
        candidates.addCandidate(candidateData);
    }

    public void deleteCandidate(String candidateUUID) {
        for (Candidate candidate : candidates.getCandidates()) {
            if (candidate.getUUID().equals(candidateUUID)) {
                candidates.deleteCandidate(candidateUUID);
                break;
            }
        }
    }
}
