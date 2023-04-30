package service;

import Model.*;

import java.io.File;
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

    public void Invoicing(String MeetingType){

    }

    public String vote(String voteData) {
        return ballots.addBallot(voteData);
    }

    public boolean checkVoteActivityStatus() {
        return status;
    }

    public List<Candidate> getCandidates() {
        return candidates.getCandidates();
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

    public int realtimeCountBallots() {
        return 0;
    }

    public Map<String, String> getVotedBallot(String uuid) {
        return null;
    }

    public File downloadResult() {
        return null;
    }
}
