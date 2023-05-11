package util;

import java.util.UUID;

public class Utility {

    private Utility() {

    }
    public static String generateUUID(){
        return UUID.randomUUID().toString();
    }
}
