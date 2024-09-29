package com.example.studenthelpapp;

import java.util.Random;

public class oneTimeCode {
	
    public static void main(String[] args) {
    	
        Random random = new Random();
        int code = 10000 + random.nextInt(90000);

        // Randomly decide whether to add a letter
        boolean addLetter = random.nextBoolean();
        String result = String.valueOf(code);

        if (addLetter) {
            char randomLetter = (char) ('A' + random.nextInt(26)); // Generate a random uppercase letter
            result += randomLetter;
        }

        System.out.println("Code: " + result);
    }
}
