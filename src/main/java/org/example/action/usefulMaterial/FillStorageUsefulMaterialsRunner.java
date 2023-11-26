package org.example.action.usefulMaterial;

import lombok.RequiredArgsConstructor;
import org.example.action.reader.Reader;
import org.example.repository.UsefulMaterialRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FillStorageUsefulMaterialsRunner implements CommandLineRunner {

    @Value("${data.name}")
    private String pathToData;

    private final UsefulMaterialRepository usefulMaterialRepository;

    public void create() {
        usefulMaterialRepository.putAllInUsefulMaterials(Reader.read(pathToData));
    }

    @Override
    public void run(String... args) throws Exception {
        create();
    }
}
