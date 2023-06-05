package service;

import model.*;

import java.util.*;

public class VoteActivity {
    private String id = "";
    private String title = "";
    private String introduction = "";
    private boolean status = false;
    private Ballots ballots = new Ballots();
    private Candidates candidates = new Candidates();
    private List<Map<String, String>> result =  new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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
        result.clear();
        title = "";
        status = false;
    }

    public String getBallot(String ballotUUID) {
        String candidateUUID = "";
        for (Ballot ballot : ballots.getBallots()) {
            if (ballot.getUUID().equals(ballotUUID)) {
                candidateUUID = ballot.getCandidateUUID();
                break;
            }
        }

        for (Candidate candidate : candidates.getCandidates()) {
            if (candidate.getUUID().equals(candidateUUID)) {
                return candidate.getName();
            }
        }
        return null;
    }

    public List<Candidate> getCandidates() {
        return candidates.getCandidates();
    }

    public String getCandidateName(String candidateUUID) {
        for (Candidate candidate : candidates.getCandidates()) {
            if (candidate.getUUID().equals(candidateUUID)) {
                return candidate.getName();
            }
        }
        return null;
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

    public void invoicing() {
        for (Candidate candidate : candidates.getCandidates()) {
            Map<String, String> candidateResult = new HashMap<>();
            candidateResult.put("name", candidate.getName());
            candidateResult.put("image", candidate.getImage());
            candidateResult.put("countNum", "0");
            result.add(candidateResult);
        }

        for (Ballot ballot : ballots.getBallots()) {
            for (Candidate candidate : candidates.getCandidates()) {
                if (ballot.getCandidateUUID().equals(candidate.getUUID())) {
                    for (Map<String, String> candidateResult : result) {
                        if (candidateResult.get("name").equals(candidate.getName())) {
                            int count = Integer.parseInt(candidateResult.get("countNum"));
                            count++;
                            candidateResult.put("countNum", String.valueOf(count));
                            break;
                        }
                    }
                    break;
                }
            }
        }

        Collections.sort(result, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> candidateResult1, Map<String, String> candidateResult2) {
                int count1 = Integer.parseInt(candidateResult1.get("countNum"));
                int count2 = Integer.parseInt(candidateResult2.get("countNum"));
                return count2 - count1;
            }
        });
    }

    public List<Map<String, String>> getResult() {
        return result;
    }
}
