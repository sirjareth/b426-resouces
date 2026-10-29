package com.joysistvi.day13.string;

import java.util.Locale;

public class MiniActivity {

    public static void main(String[] args) {
        String country = "Philippines".toLowerCase();
        String vowels = "aeiou";
        int count = 0;

        for (int i = 0; i < country.length(); i++) {
            if (country.charAt(i) == 'P' || country.charAt(i) == 'p'){
                count++;
            }

        }
        System.out.println("Letter P/p Count: " + count);

        count = 0;
        for (int i = 0; i < country.length(); i++) {
            for (int j = 0; j < vowels.length(); j++) {
                //                  h == u
                if (country.charAt(i) == vowels.charAt(1)) {
                    count++;
                }
            }
        }

        System.out.println("Vowels Count: " + count);
    }
}
