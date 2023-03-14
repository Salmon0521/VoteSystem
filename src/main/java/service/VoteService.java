package service;

import Model.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.*;

public class VoteService {
    private RankBallots rankBallots = new RankBallots();
    private AgreementBallots agreementBallots = new AgreementBallots();
    private AssessmentBallots assessmentBallots = new AssessmentBallots();
    private LinkedHashMap<String, String> votingUUID = new LinkedHashMap<String, String>(){{
        put("Assessment", "");
        put("Agreement", "");
        put("Rank", "");
    }};

    public List<XSSFWorkbook> Invoicing(String MeetingType){
        List<XSSFWorkbook> workbooks = null;
        switch (MeetingType){
            case "Rank":
                workbooks = rankBallots.getResult();
                break;
            case "Assessment":
                workbooks = assessmentBallots.getResult();
                break;
            case "Agreement":
                workbooks = agreementBallots.getResult();
                break;
        }
        return workbooks;
    }

    public void Vote(String MeetingType, List<LinkedHashMap<String, String>> voteData, String userUUID){
        switch (MeetingType){
            case "Rank":
                rankBallots.updateBallot(voteData, userUUID);
                break;
            case "Assessment":
                assessmentBallots.updateBallot(voteData, userUUID);
                break;
            case "Agreement":
                agreementBallots.updateBallot(voteData, userUUID);
                break;
        }
    }

    public boolean checkResultFile(String MeetingType) {
        AddTestBallot(MeetingType);
        try {
            List<XSSFWorkbook> workbooks = Invoicing(MeetingType);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void AddTestBallot(String MeetingType) {
        List<LinkedHashMap<String, String>> voteData = new ArrayList<>();
        LinkedHashMap<String , String> ballot = new LinkedHashMap<>();
        String userUUID = "999";
        switch (MeetingType){
            case "Rank":
                ballot = new LinkedHashMap<String, String>(){{
                    put("col1", "test_col1");
                    put("col2", "test_col2");
                    put("score", "1");
                }};
                voteData.add(ballot);
                rankBallots.updateBallot(voteData, userUUID);
                break;
            case "Assessment":
                ballot = new LinkedHashMap<String, String>(){{
                    put("writingScore", "10");
                    put("researchScore", "20");
                    put("educationScore", "30");
                    put("serviceScore", "40");
                    put("checkbox0", "true");
                    put("checkbox1", "true");
                    put("checkbox2", "true");
                    put("checkbox3", "true");
                    put("checkbox4", "true");
                    put("checkbox5", "true");
                    put("checkbox6", "false");
                    put("checkbox7", "false");
                    put("checkbox8", "false");
                    put("checkbox9", "false");
                    put("checkbox10", "false");
                    put("otherReason", "");
                }};
                voteData.add(ballot);
                assessmentBallots.updateBallot(voteData, userUUID);
                break;
            case "Agreement":
                ballot = new LinkedHashMap<String, String>(){{
                    put("col1", "test_col1");
                    put("col2", "test_col2");
                    put("agreement", "agree");
                }};
                voteData.add(ballot);
                agreementBallots.updateBallot(voteData, userUUID);
                break;
        }
    }

    public void Upload(String meetingType, String basePath, String ballotFile, String resultFile) {
        switch (meetingType){
            case "Rank":
                rankBallots = new RankBallots();
                rankBallots.setBasePath(basePath);
                rankBallots.uploadBallotFile(ballotFile);
                rankBallots.uploadResultFile(resultFile);
                break;
            case "Assessment":
                assessmentBallots = new AssessmentBallots();
                assessmentBallots.setBasePath(basePath);
                assessmentBallots.uploadBallotFile(ballotFile);
                assessmentBallots.uploadResultFile(resultFile);
                break;
            case "Agreement":
                agreementBallots = new AgreementBallots();
                agreementBallots.setBasePath(basePath);
                agreementBallots.uploadBallotFile(ballotFile);
                agreementBallots.uploadResultFile(resultFile);
                break;
        }
    }

    public void Reset(String meetingType) {
        votingUUID.put(meetingType, "");
        switch (meetingType){
            case "Rank":
                rankBallots.ResetUploadFile();
                rankBallots.ResetVotedBallots();
            case "Assessment":
                assessmentBallots.ResetUploadFile();
                assessmentBallots.ResetVotedBallots();
                assessmentBallots.ResetBallotInfo();
            case "Agreement":
                agreementBallots.ResetUploadFile();
                agreementBallots.ResetVotedBallots();
        }
    }

    public void ResetVotedBallots(String meetingType) {
        switch (meetingType){
            case "Rank":
                rankBallots.ResetVotedBallots();
            case "Assessment":
                assessmentBallots.ResetVotedBallots();
            case "Agreement":
                agreementBallots.ResetVotedBallots();
        }
    }

    public String[] checkUpload(String meetingType) {
        switch (meetingType){
            case "Rank":
                return new String[]{rankBallots.getBallotFile(), rankBallots.getResultFile()};
            case "Assessment":
                return new String[]{assessmentBallots.getBallotFile(), assessmentBallots.getResultFile()};
            case "Agreement":
                return new String[]{agreementBallots.getBallotFile(), agreementBallots.getResultFile()};
        }
        return new String[]{"", ""};
    }

    public int countVotedBallots(String meetingType) {
        switch (meetingType){
            case "Rank":
                return rankBallots.getBallotList().size();
            case "Assessment":
                return assessmentBallots.getBallotList().size();
            case "Agreement":
                return agreementBallots.getBallotList().size();
        }
        return 0;
    }

    public void setVotingUUID(String meetingType) {
        String uuid;
        List<String> existUUIDList = new ArrayList<>(votingUUID.values());
        do{
            uuid = UUID.randomUUID().toString().substring(0, 4);
        }while (existUUIDList.contains(uuid));
        votingUUID.put(meetingType, uuid);
    }

    public String getVotingUUID(String meetingType) {
        return votingUUID.get(meetingType);
    }

    public boolean checkVotingUUID(Object votingSession, String meetingType)  {
        if (votingSession == null) {
            return true;
        } else return !votingSession.toString().equals(votingUUID.get(meetingType));
    }

    public boolean checkVotingStatus(String meetingType) {
        boolean status = false;
        switch (meetingType){
            case "Rank":
                status = rankBallots.getStatus();
                break;
            case "Assessment":
                status = assessmentBallots.getStatus();
                break;
            case "Agreement":
                status = agreementBallots.getStatus();
                break;
        }
        return status;
    }

    public void changeStatus(String meetingType) {
        switch (meetingType){
            case "Rank":
                rankBallots.changeToCantVote();
            case "Assessment":
                assessmentBallots.changeToCantVote();
            case "Agreement":
                agreementBallots.changeToCantVote();
        }
    }

    public String getName(String meetingType) {
        switch (meetingType){
            case "Rank":
                return rankBallots.getName();
            case "Assessment":
                return assessmentBallots.getName();
            case "Agreement":
                return agreementBallots.getName();
        }
        return "";
    }

    public boolean checkIsVoted(String userUUID, String meetingType) {
        boolean isVoted = false;
        switch (meetingType){
            case "Rank":
                for (RankBallot rankBallot : rankBallots.getBallotList()) {
                    if (rankBallot.getUserUUID().equals(userUUID)) {
                        isVoted = true;
                        break;
                    }
                }
                break;
            case "Assessment":
                for (AssessmentBallot assessmentBallot : assessmentBallots.getBallotList()) {
                    if (assessmentBallot.getUserUUID().equals(userUUID)) {
                        isVoted = true;
                        break;
                    }
                }
                break;
            case "Agreement":
                for (AgreementBallot agreementBallot : agreementBallots.getBallotList()) {
                    if (agreementBallot.getUserUUID().equals(userUUID)) {
                        isVoted = true;
                        break;
                    }
                }
                break;
        }
        return isVoted;
    }

    public String getExampleFileName(String meetingType, int fileType) {
        String[] fileName = {};
        switch (meetingType){
            case "Rank":
                fileName = new String[]{"序位投票_選票檔.xlsx", "", "序位投票_結果檔.xlsx"};
                break;
            case "Assessment":
                fileName = new String[]{"升等評分_教授_投票檔.xlsx", "升等評分_兼任教師_投票檔.xlsx", "升等評分_結果檔.xlsx"};
                break;
            case "Agreement":
                fileName = new String[]{"同意票_選票檔.xlsx", "", "同意票_結果檔.xlsx"};
                break;
        }
        return fileName[fileType];
    }

    public String getInvoicingFileName(String meetingType, int downloadType) {
        String uploadFileName = "";
        switch (meetingType){
            case "Rank":
                uploadFileName = rankBallots.getResultFileName();
                break;
            case "Assessment":
                uploadFileName = assessmentBallots.getResultFileName();
                break;
            case "Agreement":
                uploadFileName = agreementBallots.getResultFileName();
                break;
        }
        return downloadType == 0? uploadFileName+"_統計結果.xlsx" : uploadFileName+"_各別結果.xlsx";
    }
}
