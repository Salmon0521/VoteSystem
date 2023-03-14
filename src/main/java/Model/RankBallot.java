package Model;

import util.Utility;

import java.util.LinkedHashMap;
import java.util.List;

public class RankBallot {
    private String userUUID = "";
    private List<LinkedHashMap<String, String>> rankList;

    public String getUserUUID() {
        return userUUID;
    }
    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public List<LinkedHashMap<String, String>> getRankList() {
        return rankList;
    }

    public void setRankList(List<LinkedHashMap<String, String>> rankList) {
        this.rankList = rankList;
    }
}
