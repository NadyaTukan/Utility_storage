package org.example;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Menu {
    static Scanner in = new Scanner(System.in);
    int choice;

    String menuDescription = "1. Вывод записи по ID.\n2. Поиск записей по части имени.\n3. Выход\nВведите число: ";

    public void start(Storage storage) {
        System.out.print(menuDescription);
        choice = in.nextInt();
        while (true) {
            switch (choice){
                case 1: outputMaterialByID(storage);
                        start(storage);
                case 2: outputMaterialByPartOfName(storage);
                        start(storage);
                case 3: System.exit(1);
            }
            System.out.println("Неверное число\n");
            System.out.print(menuDescription);
            choice = in.nextInt();
        }
    }

    private void outputMaterialByID(Storage storage) {
        System.out.print("Введите ID: ");
        int id = in.nextInt();
        String result = storage.searchByID(id);
        System.out.println(Objects.requireNonNullElse(result, "Неверный ID\n"));
    }

    private void outputMaterialByPartOfName(Storage storage) {
        System.out.print("Введите часть записи: ");
        String partOfName = in.next();
        ArrayList<UsefulMaterial> results = storage.searchByPartOfName(partOfName);
        if (!results.isEmpty()) {
            for (UsefulMaterial result: results) {
                System.out.println(result.getInfo());
            }
        } else
            System.out.println("Не найдено результатов\n");
    }

}
