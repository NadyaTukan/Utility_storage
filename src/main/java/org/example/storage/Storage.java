package org.example.storage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NonNull;
import org.example.model.UsefulMaterial;
import org.example.reader.Reader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Component
public class Storage {

    private final Map<Long, UsefulMaterial> materials = new HashMap<>();
    private Long nextId = 0L;

    public Storage(@Value("${data.name}") String pathToData) {
        materials.putAll(Reader.read(pathToData));
        nextId = getMaxId();

    }

    public UsefulMaterial create(UsefulMaterial material) {
        materials.put(material.getId(), material);
        return material;
    }

    public UsefulMaterial update(@NonNull Long id, UsefulMaterial newMaterial) {
        if (materials.containsKey(id)) {
            UsefulMaterial material = materials.get(id);
            material.setId(newMaterial.getId());
            material.setName(newMaterial.getName());
            material.setDescription(newMaterial.getDescription());
            material.setLink(newMaterial.getLink());
            return material;
        } else
            return null;
    }

    public UsefulMaterial delete(@NonNull Long id) {
        return materials.remove(id);
    }

    public UsefulMaterial searchByID(@NonNull Long id) {
        return materials.get(id);
    }

    public List<UsefulMaterial> searchByPartOfName(@NotBlank String partOfName) {
        return materials.values()
                .stream()
                .filter(material -> material.getName().toLowerCase().contains(partOfName.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Long getMaxId() {
        long maxId =0L;
        for (Map.Entry<Long, UsefulMaterial> material : materials.entrySet()) {
            if (material.getValue().getId() > maxId)
                maxId = material.getValue().getId();
        }
        return maxId;
    }

    public Long getNextId() {
        return ++nextId;
    }
}
