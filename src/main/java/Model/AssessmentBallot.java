package Model;

import util.Utility;

import java.util.HashMap;
import java.util.List;

public class AssessmentBallot {
    private String userUUID = "";
    private Double writingScore;
    private Double researchScore;
    private Double educationScore;
    private Double serviceScore;
    private HashMap<String, Boolean> reasonList;
    private String otherReason;

    public String getUserUUID() {
        return userUUID;
    }
    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public Double getWritingScore() {
        return writingScore;
    }

    public void setWritingScore(Double writingScore) {
        this.writingScore = writingScore;
    }

    public Double getResearchScore() {
        return researchScore;
    }

    public void setResearchScore(Double researchScore) {
        this.researchScore = researchScore;
    }

    public Double getEducationScore() {
        return educationScore;
    }

    public void setEducationScore(Double educationScore) {
        this.educationScore = educationScore;
    }

    public Double getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(Double serviceScore) {
        this.serviceScore = serviceScore;
    }

    public HashMap<String, Boolean> getReasonList() {
        return reasonList;
    }

    public void setReasonList(HashMap<String, Boolean> reasonList) {
        this.reasonList = reasonList;
    }

    public String getOtherReason() {
        return otherReason;
    }

    public void setOtherReason(String otherReason) {
        this.otherReason = otherReason;
    }
}
