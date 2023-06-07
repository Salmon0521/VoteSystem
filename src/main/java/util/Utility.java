package util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class Utility {

    private Utility() {

    }
    public static String generateUUID(){
        return UUID.randomUUID().toString();
    }

    public static void deleteCandidateIMG(String servletPath, String imagePath){
        try{
            String targetFile = servletPath + "img/candidateIMG/" + imagePath;
            Path targetFilePath = Paths.get(targetFile);
            Files.deleteIfExists(targetFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setDefaultCandidateIMG(String servletPath, String imagePath){
        try {
            String sourcePath = servletPath + "img/candidate.png" ;
            File sourceFile = new File(sourcePath);
            File targetFile = new File(servletPath + "img/candidateIMG/" + imagePath);
            Files.copy(sourceFile.toPath(), targetFile.toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
