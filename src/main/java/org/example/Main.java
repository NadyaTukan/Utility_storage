package org.example;

public class Main {
    public static void main(String[] args) {
        Storage storage = new Storage("src/main/resources/data.json");
        Menu menu = new Menu();
        menu.start(storage);
    }
}