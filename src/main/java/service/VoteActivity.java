package service;

import Model.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

    public String vote(Map<String, String> candidateData) {
        return null;
    }

    public void reviseCandidate(List<Map<String, String>> candidateData) {

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
