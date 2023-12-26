package org.example.service.usefulMaterial;

import lombok.RequiredArgsConstructor;
import org.example.action.grade.CreateGradeActionArgument;
import org.example.exception.NotFoundException;
import org.example.exception.NullPointerException;
import org.example.model.UsefulMaterial;
import org.example.repository.UsefulMaterialRepository;
import org.example.service.usefulMaterial.argument.CreateUsefulMaterialArgument;
import org.example.service.usefulMaterial.argument.UpdateUsefulMaterialArgument;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UsefulMaterialServiceImpl implements UsefulMaterialService {

    private final UsefulMaterialRepository usefulMaterialRepository;

    @Override
    public UsefulMaterial create(CreateUsefulMaterialArgument argument) {
        return usefulMaterialRepository.create(UsefulMaterial.builder()
                                                             .name(argument.getName())
                                                             .description(argument.getDescription())
                                                             .link(argument.getLink())
                                                             .build());
    }

    @Override
    public void deleteById(Long id) {
        Optional.ofNullable(usefulMaterialRepository.delete(id))
                .orElseThrow(() -> new NotFoundException("Материал не найден."));
    }

    @Override
    public UsefulMaterial updateByID(Long id, UpdateUsefulMaterialArgument argument) {
        return Optional.ofNullable(usefulMaterialRepository.update(id, UsefulMaterial.builder()
                                                                                     .id(id)
                                                                                     .name(argument.getName())
                                                                                     .description(
                                                                                             argument.getDescription())
                                                                                     .link(argument.getLink())
                                                                                     .build()))
                       .orElseThrow(() -> new NullPointerException("Материал не найден."));
    }

    @Override
    public UsefulMaterial searchByID(Long id) {
        return Optional.ofNullable(usefulMaterialRepository.searchByID(id))
                       .orElseThrow(() -> new NotFoundException("Материал не найден."));
    }

    @Override
    public List<UsefulMaterial> searchByPartOfName(String partOfName) {
        return Optional.ofNullable(usefulMaterialRepository.searchByPartOfName(partOfName))
                       .orElseThrow(() -> new NotFoundException("Материал не найден."));
    }

    public void existsById(CreateGradeActionArgument argument) {
        searchByID(argument.getUsefulMaterialId());
    }
}
