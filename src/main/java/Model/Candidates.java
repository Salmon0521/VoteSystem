package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Candidates {
    private List<Candidate> candidateList = new ArrayList<>();

    public List<Candidate> getCandidates() {
        return candidateList;
    }

    public void addCandidate(Map<String, String> candidateData) {
        Candidate candidate = new Candidate(candidateData);
        candidateList.add(candidate);
    }

    public void deleteCandidate(String candidateUUID) {
        for (Candidate candidate : candidateList) {
            if (candidate.getUUID().equals(candidateUUID)) {
                candidateList.remove(candidate);
                break;
            }
        }
    }

    public void removeAllCandidate() {
        candidateList.clear();
    }
}
