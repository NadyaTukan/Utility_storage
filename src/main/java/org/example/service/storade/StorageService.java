package org.example.service.storade;

import org.example.model.UsefulMaterial;
import org.example.service.storade.argument.CreateMaterialArgument;
import org.example.service.storade.argument.UpdateMaterialArgument;

import java.util.List;

public interface StorageService {
    UsefulMaterial create(CreateMaterialArgument argument);

    void deleteById(Long id);

    UsefulMaterial updateByID(Long id, UpdateMaterialArgument argument);

    UsefulMaterial searchByID(Long id);

    List<UsefulMaterial> searchByPartOfName(String partOfName);

}
