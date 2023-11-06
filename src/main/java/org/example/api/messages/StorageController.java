package org.example.api.messages;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.api.messages.dto.CreateMaterialDto;
import org.example.api.messages.dto.MaterialDto;
import org.example.api.messages.mapper.MaterialMapper;
import org.example.model.UsefulMaterial;
import org.example.service.StorageService;
import org.example.service.argument.CreateMaterialArgument;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
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
    public MaterialDto delete(@PathVariable("id") Long id) {
        return mapper.toDto(storageService.deleteById(id));
    }

    @PutMapping("{id}")
    @Operation(description = "Изменить запись по ID")
    public MaterialDto updateByID(@PathVariable("id") Long id, @RequestBody CreateMaterialDto dto) {
        CreateMaterialArgument argument = mapper.toCreateArgument(dto);
        return mapper.toDto(storageService.updateByID(id, argument));
    }

    @GetMapping("{id}")
    @Operation(description = "Получить запись по ID")
    public MaterialDto getByID(@PathVariable("id") Long id) {
        UsefulMaterial material = storageService.searchByID(id);
        return mapper.toDto(material);
    }

    @GetMapping("search/{partOfName}")
    @Operation(description = "Получить записи по части имени")
    public List<MaterialDto> searchByPartOfName(@PathVariable("partOfName") String partOfName) {
        List<UsefulMaterial> materials = storageService.searchByPartOfName(partOfName);
        return mapper.toDtoList(materials);
    }
}
