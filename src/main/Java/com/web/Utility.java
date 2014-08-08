package com.web;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by R500 on 18.7.2014 г..
 */
public class Utility {
    public static String byteArrayToHexString(byte[] b) {
        String result = "";
        for (int i=0; i < b.length; i++) {
            result +=
                    Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }

    public static String salt(String pass){
        String salt = "#ILikeToEncryptMyPasswordsBecauseIt'sFun*@#&$!%%#)((00319$$%";
        String unencryptedPass = pass + salt;
        return unencryptedPass;
    }

    public static String toSHA1(byte[] convertme) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        }
        catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return byteArrayToHexString(md.digest(convertme));
    }
}