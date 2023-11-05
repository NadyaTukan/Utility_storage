package org.example.service;

import org.example.model.UsefulMaterial;
import org.example.service.argument.CreateMaterialArgument;

import java.util.List;

public interface StorageService {
    UsefulMaterial create(CreateMaterialArgument argument);

    UsefulMaterial deleteById(Long id);

    UsefulMaterial updateByID(Long id, CreateMaterialArgument argument);

    UsefulMaterial searchByID(Long id);

    List<UsefulMaterial> searchByPartOfName(String partOfName);

}
