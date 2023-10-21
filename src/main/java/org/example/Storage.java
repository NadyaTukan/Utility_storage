package org.example;

import java.util.ArrayList;
import java.util.Map;

public class Storage {

    //преполагается, что ключ Map совпадает с полем ID объекта класса UsefulMaterial
    Map<Integer, UsefulMaterial> materials;

    public Map<Integer, UsefulMaterial> getMaterials() {
        return materials;
    }

    public Storage() {
        materials = Reader.read("src/main/resources/data.json");
    }

    public Storage(Map<Integer, UsefulMaterial> materials) {
        this.materials = materials;
    }

    public Storage(String pathToData) {
        materials = Reader.read(pathToData);
    }

    public String searchByID(int id){
        if (materials.containsKey(id))
            return materials.get(id).getInfo();
        else
            return null;
    }

    public ArrayList<UsefulMaterial> searchByPartOfName(String partOfName) {
        ArrayList<UsefulMaterial> results = new ArrayList<>();
        for (Map.Entry<Integer, UsefulMaterial> material: materials.entrySet()) {
            if(material.getValue().getName().contains(partOfName.toLowerCase()))
                results.add(material.getValue());
        }
        return results;
    }
}
