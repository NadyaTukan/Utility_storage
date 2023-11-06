package org.example;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AppRunner implements CommandLineRunner {

    private final Menu menu;

    @Override
    public void run(String... args) {
        menu.start();
    }
}
