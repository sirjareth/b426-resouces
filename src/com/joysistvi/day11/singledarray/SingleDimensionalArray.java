package com.joysistvi.day11.singledarray;

public class SingleDimensionalArray {

    public static void main(String[] args) {
        // syntax
        // age 24, 26, 30, 38, 17

        // indices:    0   1   2    3   4
        int[] ages = { 24, 26, 30, 38, 17, 50, 60 };

        // invoke an element
       // System.out.println(ages[5]);

        // get the length of an array
       // System.out.println(ages.length);

        int count = 0 ;

        // traverse an array using for-loop
        for (int i = 0; i < ages.length; i++) {
            count++;
        }

        // traverse an array using  for-each loop / enhance for-loop
        for (int age : ages) {
            System.out.println(age);
        }

    }
}
