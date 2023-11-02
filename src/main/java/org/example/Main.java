package org.example;

public class Main {
    public static void main(String[] args) {
        if (args.length != 0){
            if (Reader.isRightPath(args[0])){
                Storage storage = new Storage(args[0]);
                Menu menu = new Menu();
                menu.start(storage);
            } else {
                System.out.println("Неправильный путь!");
            }
        } else {
            System.out.println("Пустой путь!");
        }
    }
}