package org.example;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AppRunner implements CommandLineRunner {

    private Storage storage = new Storage();

    @Override
    public void run(String... args) {
        Menu menu = new Menu();
        menu.start(storage);
    }
}
