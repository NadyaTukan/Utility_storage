package org.example.api.storage;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.api.storage.dto.CreateMaterialDto;
import org.example.api.storage.dto.MaterialDto;
import org.example.api.storage.dto.UpdateMaterialDto;
import org.example.api.storage.mapper.MaterialMapper;
import org.example.model.UsefulMaterial;
import org.example.service.storade.StorageService;
import org.example.service.storade.argument.CreateMaterialArgument;
import org.example.service.storade.argument.UpdateMaterialArgument;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("materials")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;
    private final MaterialMapper mapper;

    @PostMapping("create")
    @Operation(description = "Создать запись")
    public MaterialDto create(@RequestBody CreateMaterialDto dto) {
        CreateMaterialArgument argument = mapper.toCreateArgument(dto);
        return mapper.toDto(storageService.create(argument));
    }

    @DeleteMapping("{id}")
    @Operation(description = "Удалить запись по ID")
    public void delete(@PathVariable("id") Long id) {
        storageService.deleteById(id);
    }

    @PostMapping("{id}/update")
    @Operation(description = "Изменить запись по ID")
    public MaterialDto updateByID(@PathVariable("id") Long id, @RequestBody UpdateMaterialDto dto) {
        UpdateMaterialArgument argument = mapper.toUpdateArgument(dto);
        return mapper.toDto(storageService.updateByID(id, argument));
    }

    @GetMapping("{id}")
    @Operation(description = "Получить запись по ID")
    public MaterialDto getByID(@PathVariable("id") Long id) {
        UsefulMaterial material = storageService.searchByID(id);
        return mapper.toDto(material);
    }

    @GetMapping("search/")
    @Operation(description = "Получить записи по части имени")
    public List<MaterialDto> searchByPartOfName(@RequestParam String partOfName) {
        List<UsefulMaterial> materials = storageService.searchByPartOfName(partOfName);
        return mapper.toDtoList(materials);
    }
}
