package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Candidates {
    private List<Candidate> candidateList = new ArrayList<>();

    public void updateCandidate(List<Map<String, String>> candidateData) {
        deleteCandidate();
        for (Map<String, String> candidate : candidateData) {
            addCandidate(candidate);
        }
    }

    public void addCandidate(Map<String, String> candidateData) {
        Candidate candidate = new Candidate(candidateData.get("id"), candidateData.get("name"), candidateData.get("introduction"), candidateData.get("image"));
        candidateList.add(candidate);
    }

    public void deleteCandidate() {
        candidateList.clear();
    }

}
