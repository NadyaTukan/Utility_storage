package org.example;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class Storage {

    //преполагается, что ключ Map совпадает с полем ID объекта класса UsefulMaterial
    private final Map<Integer, UsefulMaterial> materials = new HashMap<>();

    private final String pathToData;

    public Storage(@Value("${data.name}") String pathToData) {
        this.pathToData = pathToData;
    }

    @PostConstruct
    public void init() {
        materials.putAll(Reader.read(pathToData));
    }

    public String searchByID(@NonNull Integer id) {
        if (materials.containsKey(id))
            return materials.get(id).toString();
        else
            return null;
    }

    public ArrayList<UsefulMaterial> searchByPartOfName(@NonNull String partOfName) {
        ArrayList<UsefulMaterial> results = new ArrayList<>();
        for (Map.Entry<Integer, UsefulMaterial> material : materials.entrySet()) {
            if (material.getValue().getName().toLowerCase().contains(partOfName.toLowerCase()))
                results.add(material.getValue());
        }
        return results;
    }
}
