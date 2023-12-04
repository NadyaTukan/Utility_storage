package org.example.repository;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import org.example.model.UsefulMaterial;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Component
public class UsefulMaterialRepository {

    private final Map<Long, UsefulMaterial> usefulMaterials = new HashMap<>();
    private Long nextId = 0L;


    public void clear() {
        this.usefulMaterials.clear();
        nextId = 0L;
    }

    public void putAll(Map<Long, UsefulMaterial> newUsefulMaterials) {
        this.usefulMaterials.putAll(newUsefulMaterials);
        nextId = getMaxId();
    }

    public UsefulMaterial create(UsefulMaterial usefulMaterial) {
        usefulMaterial.setId(getNextId());
        usefulMaterials.put(usefulMaterial.getId(), usefulMaterial);
        return usefulMaterial;
    }

    public UsefulMaterial update(@NonNull Long id, UsefulMaterial newUsefulMaterial) {
        if (usefulMaterials.containsKey(id)) {
            UsefulMaterial usefulMaterial = usefulMaterials.get(id);
            usefulMaterial.setId(newUsefulMaterial.getId());
            usefulMaterial.setName(newUsefulMaterial.getName());
            usefulMaterial.setDescription(newUsefulMaterial.getDescription());
            usefulMaterial.setLink(newUsefulMaterial.getLink());
            return usefulMaterial;
        } else
            return null;
    }

    public UsefulMaterial delete(@NonNull Long id) {
        return usefulMaterials.remove(id);
    }

    public UsefulMaterial searchByID(@NonNull Long id) {
        return usefulMaterials.get(id);
    }

    public List<UsefulMaterial> searchByPartOfName(@NotBlank String partOfName) {
        List<UsefulMaterial> results = usefulMaterials.values()
                                                      .stream()
                                                      .filter(material -> material.getName()
                                                                                  .toLowerCase()
                                                                                  .contains(partOfName.toLowerCase()))
                                                      .toList();
        if (!results.isEmpty()) {
            return results;
        } else {
            return null;
        }
    }

    private Long getMaxId() {
        return usefulMaterials.keySet()
                              .stream()
                              .max(Long::compareTo)
                              .orElse(0L);
    }

    private Long getNextId() {
        return ++nextId;
    }
}
