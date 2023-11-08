package org.example.service;

import org.example.model.UsefulMaterial;
import org.example.service.argument.CreateMaterialArgument;
import org.example.service.argument.UpdateMaterialArgument;

import java.util.List;

public interface StorageService {
    UsefulMaterial create(CreateMaterialArgument argument);

    void deleteById(Long id);

    UsefulMaterial updateByID(Long id, UpdateMaterialArgument argument);

    UsefulMaterial searchByID(Long id);

    List<UsefulMaterial> searchByPartOfName(String partOfName);

}
