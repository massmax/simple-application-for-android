package ru.app.devkit.test_ic720.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilMethods {
    public static boolean validityCheck (String address) {
        return checkURL(address) || checkIP(address);
    }

    private static boolean checkURL(String userNameString){
        Pattern p1 = Pattern.compile("https?://([a-z1-9]+.)?[a-z1-9\\-]+(\\.[a-z]+){1,}/?");
        Pattern p2 = Pattern.compile("([a-z1-9]+.)?[a-z1-9\\-]+(\\.[a-z]+){1,}/?");
        Matcher m1 = p1.matcher(userNameString);
        Matcher m2 = p2.matcher(userNameString);
        return m1.matches() || m2.matches();
    }

    private static boolean checkIP(String userNameString){
        Pattern p = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
        Matcher m = p.matcher(userNameString);
        return m.matches();
    }
}
