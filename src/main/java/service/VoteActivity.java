package service;

import model.*;

import java.util.*;

public class VoteActivity {
    private String title = "";
    private boolean status = false;
    private Ballots ballots = new Ballots();
    private Candidates candidates = new Candidates();
    private Map<String, Map<String, String>> result = new LinkedHashMap<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Candidate> getCandidates() {
        return candidates.getCandidates();
    }

    public List<Map<String, String>> getResult() {
        return new ArrayList<>(result.values());
    }

    private void sortResult () {
        List<Map.Entry<String, Map<String, String>>> list = new ArrayList<>(result.entrySet());
        list.sort((o1, o2) -> {
            int count1 = Integer.parseInt(o1.getValue().get("countNum"));
            int count2 = Integer.parseInt(o2.getValue().get("countNum"));
            return count2 - count1;
        });
        result.clear();
        for (Map.Entry<String, Map<String, String>> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
    }

    public String vote(String voteData) {
        return ballots.addBallot(voteData);
    }

    public void reset() {
        ballots.removeAllBallot();
        candidates.removeAllCandidate();
        result.clear();
        title = "";
        status = false;
    }

    public String getVotedBallot(String ballotUUID) {
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

    public String getCandidateName(String candidateUUID) {
        for (Candidate candidate : candidates.getCandidates()) {
            if (candidate.getUUID().equals(candidateUUID)) {
                return candidate.getName();
            }
        }
        return null;
    }

    public int countBallot() {
        return ballots.numOfBallots();
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
            result.put(candidate.getUUID(), candidateResult);
        }

        for (Ballot ballot : ballots.getBallots()) {
            if (result.containsKey(ballot.getCandidateUUID())) {
                Map<String, String> candidateResult = result.get(ballot.getCandidateUUID());
                int count = Integer.parseInt(candidateResult.get("countNum"));
                count++;
                candidateResult.put("countNum", String.valueOf(count));
                result.put(ballot.getCandidateUUID(), candidateResult);
            }
        }
        sortResult();
    }

}
