package com.web;

import java.util.Random;

/**
 * Created by R500 on 20.8.2014 г..
 */
public class RandomGenerator {
//    generateCode generates random encrypted code
//    the code is used in the link which is sent to the user's email to reset his forgotten password
    public static String generateCode(){
        Random random = new Random();
        int randomNumber = random.nextInt((999999 - 222222) + 1) + 222222;

        String randomCode = "asd" + String.valueOf(randomNumber) + "rr@#";

        String code = Utility.toSHA1(randomCode.getBytes());

        return code;
    }
}
