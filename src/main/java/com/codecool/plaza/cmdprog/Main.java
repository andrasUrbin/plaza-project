package com.codecool.plaza.api;

public class Main {

    public String getWelcomeString() {
        return "Hi!";
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.getWelcomeString());
    }
}
