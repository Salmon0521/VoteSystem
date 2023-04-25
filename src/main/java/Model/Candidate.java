package Model;

public class Candidate {
    private String id = "";
    private String name = "";
    private String introduction = "";
    private String image = "";

    public Candidate(String id, String name, String introduction, String image) {
        setId(id);
        setName(name);
        setIntroduction(introduction);
        setImage(image);
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
