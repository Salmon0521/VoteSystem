package Model;

import util.Utility;

import java.util.LinkedHashMap;
import java.util.List;

public class AgreementBallot {
    private String userUUID = "";
    private List<LinkedHashMap<String, String>> approvalIssueList;

    public String getUserUUID() {
        return userUUID;
    }
    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public List<LinkedHashMap<String, String>> getApprovalIssueList() {
        return approvalIssueList;
    }

    public void setApprovalIssueList(List<LinkedHashMap<String, String>> approvalIssueList) {
        this.approvalIssueList = approvalIssueList;
    }
}
