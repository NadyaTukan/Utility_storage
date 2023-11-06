package org.example;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class Menu {

    private final Storage storage;
    private final Scanner in = new Scanner(System.in);
    String menuDescription = "1. Вывод записи по ID.\n2. Поиск записей по части имени.\n3. Выход\nВведите число: ";

    public void start() {
        int choice;
        do {
            System.out.print(menuDescription);
            choice = in.nextInt();
            switch (choice) {
                case 1 -> outputMaterialByID(storage);
                case 2 -> outputMaterialByPartOfName(storage);
                case 3 -> {
                    return;
                }
                default -> System.out.println("Неверное число!\n");
            }
        } while (true);
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
            for (UsefulMaterial result : results) {
                System.out.println(result.toString());
            }
        } else
            System.out.println("Не найдено результатов\n");
    }
}
