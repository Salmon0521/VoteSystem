package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Ballots {
    private List<Ballot> ballotList = new ArrayList<>();

    public String addBallot(Map<String, String> ballotData) {
        Ballot ballot = new Ballot(ballotData.get("candidateId"));
        ballotList.add(ballot);
        return ballot.getUUID();
    }

    public int countBallots(String candidateID) {
        int count = 0;
        for (Ballot ballot : ballotList) {
            if (ballot.getCandidateId().equals(candidateID)) {
                ++count;
            }
        }
        return count;
    }
}
