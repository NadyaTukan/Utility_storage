package org.example.service.usefulMaterial;

import org.example.model.UsefulMaterial;
import org.example.service.usefulMaterial.argument.CreateUsefulMaterialArgument;
import org.example.service.usefulMaterial.argument.UpdateUsefulMaterialArgument;

import java.util.List;

public interface UsefulMaterialService {
    UsefulMaterial create(CreateUsefulMaterialArgument argument);

    void deleteById(Long id);

    UsefulMaterial updateByID(Long id, UpdateUsefulMaterialArgument argument);

    UsefulMaterial searchByID(Long id);

    List<UsefulMaterial> searchByPartOfName(String partOfName);

}
