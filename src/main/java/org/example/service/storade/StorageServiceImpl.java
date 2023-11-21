package org.example.service.storade;

import lombok.RequiredArgsConstructor;
import org.example.exception.NotFoundException;
import org.example.exception.NullPointerException;
import org.example.model.UsefulMaterial;
import org.example.repository.StorageUsefulMaterials;
import org.example.service.storade.argument.CreateMaterialArgument;
import org.example.service.storade.argument.UpdateMaterialArgument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private final StorageUsefulMaterials storageUsefulMaterials;

    @Override
    public UsefulMaterial create(CreateMaterialArgument argument) {
        long id = storageUsefulMaterials.getNextId();
        return storageUsefulMaterials.create(UsefulMaterial.builder()
                                                           .id(id)
                                                           .name(argument.getName())
                                                           .description(argument.getDescription())
                                                           .link(argument.getLink())
                                                           .build());
    }

    @Override
    public void deleteById(Long id) {
        Optional.ofNullable(storageUsefulMaterials.delete(id))
                .orElseThrow(() -> new NotFoundException("Материал не найден."));
    }

    @Override
    public UsefulMaterial updateByID(Long id, UpdateMaterialArgument argument) {
        return Optional.ofNullable(storageUsefulMaterials.update(id, UsefulMaterial.builder()
                                                                                   .id(id)
                                                                                   .name(argument.getName())
                                                                                   .description(argument.getDescription())
                                                                                   .link(argument.getLink())
                                                                                   .build()))
                       .orElseThrow(() -> new NullPointerException("Материал не найден."));
    }

    @Override
    public UsefulMaterial searchByID(Long id) {
        return Optional.ofNullable(storageUsefulMaterials.searchByID(id))
                       .orElseThrow(() -> new NotFoundException("Материал не найден."));
    }

    @Override
    public List<UsefulMaterial> searchByPartOfName(String partOfName) {
        return Optional.ofNullable(storageUsefulMaterials.searchByPartOfName(partOfName))
                       .orElseThrow(() -> new NotFoundException("Материал не найден."));
    }
}
