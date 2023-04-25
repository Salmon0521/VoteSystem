package Model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.*;

public class RankBallotsTest {

    @Test
    public void updateResultFile() {
        List<LinkedHashMap<String, String>> rankBallot = new ArrayList<>();
        LinkedHashMap<String, String> rank = new LinkedHashMap<>();
        rank.put("col1", "CS1");
        rank.put("col2", "name1");
        rank.put("score", "1");
        rankBallot.add(rank);
        LinkedHashMap<String, String> rank1 = new LinkedHashMap<>();
        rank1.put("col1", "CS2");
        rank1.put("col2", "name2");
        rank1.put("score", "2");
        rankBallot.add(rank1);
        LinkedHashMap<String, String> rank2 = new LinkedHashMap<>();
        rank2.put("col1", "CS3");
        rank2.put("col2", "name3");
        rank2.put("score", "3");
        rankBallot.add(rank2);

        RankBallots RB = new RankBallots();
        RB.updateBallot(rankBallot, "100");
        RB.updateBallot(rankBallot, "101");

        List<LinkedHashMap<String, String>> rankBallotLast = new ArrayList<>();
        LinkedHashMap<String, String> rank3 = new LinkedHashMap<>();
        rank3.put("col1", "CS4");
        rank3.put("col2", "name4");
        rank3.put("score", "4");
        rankBallotLast.add(rank3);
        LinkedHashMap<String, String> rank4 = new LinkedHashMap<>();
        rank4.put("col1", "CS5");
        rank4.put("col2", "name5");
        rank4.put("score", "5");
        rankBallotLast.add(rank4);
        LinkedHashMap<String, String> rank5 = new LinkedHashMap<>();
        rank5.put("col1", "CS6");
        rank5.put("col2", "name6");
        rank5.put("score", "6");
        rankBallotLast.add(rank5);
        RB.updateBallot(rankBallotLast, "102");

        RB.updateResultFile();
    }

    @Test
    public void updateBallot() {
        List<LinkedHashMap<String, String>> rankBallot = new ArrayList<>();
        LinkedHashMap<String, String> rank = new LinkedHashMap<>();
        rank.put("col1", "CS");
        rank.put("col2", "name");
        rank.put("score", "1");
        rankBallot.add(rank);
        LinkedHashMap<String, String> rank1 = new LinkedHashMap<>();
        rank1.put("col1", "CS");
        rank1.put("col2", "name1");
        rank1.put("score", "2");
        rankBallot.add(rank1);
        LinkedHashMap<String, String> rank2 = new LinkedHashMap<>();
        rank2.put("col1", "CS");
        rank2.put("col2", "name2");
        rank2.put("score", "3");
        rankBallot.add(rank2);

        RankBallots RB = new RankBallots();
        RB.updateBallot(rankBallot, "100");

        assertEquals("1", RB.getBallotList().get(0).getRankList().get(0).get("score"));
        assertEquals("2", RB.getBallotList().get(0).getRankList().get(1).get("score"));
        assertEquals("3", RB.getBallotList().get(0).getRankList().get(2).get("score"));
    }

    @Test
    public void modifyHTML() {
        RankBallots RB = new RankBallots();
        RB.ballotFile = "序位投票.xlsx";
        RB.modifyHTML();
    }
}