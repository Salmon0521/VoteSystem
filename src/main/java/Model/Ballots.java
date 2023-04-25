package Model;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.LinkedHashMap;
import java.util.List;

public abstract class Ballots {

    protected String ballotFile = "";
    protected String resultFile = "";
    protected String basePath = "";
    private Boolean status = true;
    protected String name = "";

    public String getResultFileName() {
        return this.resultFile.replace(".xlsx", "");
    }
    public boolean getStatus() {
        return this.status;
    }
    public List<XSSFWorkbook> getResult(){
        return updateResultFile();
    }

    public void uploadBallotFile(String ballotFile){
        this.ballotFile = ballotFile;
        this.modifyHTML();
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public void uploadResultFile(String resultFile){
        this.resultFile = resultFile;
    }

    public String getBallotFile() {
        return this.ballotFile;
    }
    public String getResultFile() {
        return this.resultFile;
    }

    public void ResetUploadFile() {
        this.ballotFile = "";
        this.resultFile = "";
        this.basePath = "";
    }

    public void changeToCantVote(){
        status = false;
    }
    public abstract void ResetVotedBallots();
    protected abstract List<XSSFWorkbook> updateResultFile();
    public abstract void updateBallot(List<LinkedHashMap<String, String>> ballot, String userUUID);
    protected abstract void modifyHTML();

    public String getName(){
        return name;
    }
}
