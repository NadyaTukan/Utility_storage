package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.NotFoundException;
import org.example.exception.NullPointerException;
import org.example.model.UsefulMaterial;
import org.example.service.argument.CreateMaterialArgument;
import org.example.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private final Storage storage;

    @Override
    public UsefulMaterial create(CreateMaterialArgument argument) {
        long id = storage.getNextId();
        return storage.create(UsefulMaterial.builder()
                                            .id(id)
                                            .name(argument.getName())
                                            .description(argument.getDescription())
                                            .link(argument.getLink())
                                            .build());
    }

    @Override
    public UsefulMaterial deleteById(Long id) {
        return Optional.ofNullable(storage.delete(id))
                .orElseThrow(() -> new NotFoundException("Материал не найден."));
    }

    @Override
    public UsefulMaterial updateByID(Long id, CreateMaterialArgument argument) {
        return Optional.ofNullable(storage.update(id, UsefulMaterial.builder()
                                                                    .id(id)
                                                                    .name(argument.getName())
                                                                    .description(argument.getDescription())
                                                                    .link(argument.getLink())
                                                                    .build()))
                .orElseThrow(() -> new NullPointerException("Материал не найден."));
    }

    @Override
    public UsefulMaterial searchByID(Long id) {
        return Optional.ofNullable(storage.searchByID(id))
                .orElseThrow(() -> new NotFoundException("Материал не найден."));
    }

    @Override
    public List<UsefulMaterial> searchByPartOfName(String partOfName) {
        return Optional.ofNullable(storage.searchByPartOfName(partOfName))
                .orElseThrow(() -> new NotFoundException("Материал не найден."));
    }
}
