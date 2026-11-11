package com.joysistvi.day23.polymorphism.methodoverloading;

public class Printer {

    public static void main(String[] args) {


        User user = new User("Winter", "winter@gmail.com", "winter123");
        User user2 = new User("Winter", "Bu", "Ronquillo", "winter@gmail.com", "winter123");
        User user3 = new User("Winter", "Bu", "Ronquillo", "winter@gmail.com", "winter123", "Malabon City");
        User user4 = new User("Summer", "Bu", "Ronquillo", "winter@gmail.com", "winter123",  1234566);
        User user5 = new User();
    }
}
