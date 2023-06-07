package model;

import java.util.ArrayList;
import java.util.List;

public class Ballots {
    private List<Ballot> ballotList = new ArrayList<>();

    public List<Ballot> getBallots() {
        return ballotList;
    }

    public String addBallot(String ballotData) {
        Ballot ballot = new Ballot(ballotData);
        ballotList.add(ballot);
        return ballot.getUUID();
    }

    public int numOfBallots() {
        return ballotList.size();
    }

    public void removeAllBallot() {
        ballotList.clear();
    }

}
