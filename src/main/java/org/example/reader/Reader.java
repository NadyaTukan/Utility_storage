package org.example.reader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.example.model.UsefulMaterial;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Reader {
    public static Map<Long, UsefulMaterial> read(@NonNull String pathToData) {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<Long, UsefulMaterial>> itemsMapType = new TypeReference<>() {
        };
        Map<Long, UsefulMaterial> materials;
        try {
            materials = mapper.readValue(new FileReader(pathToData), itemsMapType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return materials;
    }

}
