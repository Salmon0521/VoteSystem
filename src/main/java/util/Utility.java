package util;

import java.io.File;

public class Utility {
    private static int uuidRecord = 100;

    public static String generateUUID(){
        return String.valueOf(uuidRecord++);
    }

    public static void makeTemplateDir(String servletPath) {
        File templateDir = new File(servletPath + "template/");
        if (!templateDir.exists()) {
            templateDir.mkdir();
        }

        String[] ballotName = {"Agreement", "Assessment", "Rank"};
        for (String ballot : ballotName) {
            File ballotDir = new File(templateDir + "/" + ballot + "/");
            if (!ballotDir.exists()) {
                ballotDir.mkdir();
            }
        }
    }

    public static void makeTemplateDir(String servletPath, String dir) {
        File templateDir = new File(servletPath + "template/");
        if (!templateDir.exists()) {
            templateDir.mkdir();
        }

        File ballotDir = new File(templateDir + "/" + dir + "/");
        if (!ballotDir.exists()) {
            ballotDir.mkdir();
        }
    }
}
