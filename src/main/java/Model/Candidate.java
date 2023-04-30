package Model;

import java.util.Map;
import java.util.UUID;

public class Candidate {
    private String uuid = "";
    private String name = "";
    private String introduction = "";
    private String image = "";

    public Candidate(Map<String, String> candidateData) {
        setUUID(candidateData.get("uuid")) ;
        setName(candidateData.get("name"));
        setIntroduction(candidateData.get("introduction"));
        setImage(candidateData.get("image"));
    }
    public String getUUID() {
        return uuid.toString();
    }
    public void setUUID(String uuid) {
        this.uuid = uuid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIntroduction() {
        return introduction;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
