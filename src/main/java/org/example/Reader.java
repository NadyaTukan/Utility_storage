package org.example;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Map;

public class Reader {
    public static Map<Integer, UsefulMaterial> read(String pathToData) {
        Type itemsMapType = new TypeToken<Map<Integer, UsefulMaterial>>() {}.getType();
        Map<Integer, UsefulMaterial> materials;
        try {
            materials = new Gson().fromJson(new FileReader(pathToData), itemsMapType);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return materials;
    }

    public static boolean isRightPath (String pathToData) {
        File file = new File(pathToData);
        return file.exists() && pathToData.endsWith(".json");

    }
}
