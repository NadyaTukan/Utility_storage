package org.example.api.usefulMaterial;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api.usefulMaterial.dto.CreateUsefulMaterialDto;
import org.example.api.usefulMaterial.dto.UsefulMaterialDto;
import org.example.api.usefulMaterial.dto.UpdateUsefulMaterialDto;
import org.example.api.usefulMaterial.mapper.UsefulMaterialMapper;
import org.example.model.UsefulMaterial;
import org.example.service.usefulMaterial.UsefulMaterialService;
import org.example.service.usefulMaterial.argument.CreateUsefulMaterialArgument;
import org.example.service.usefulMaterial.argument.UpdateUsefulMaterialArgument;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("materials")
@RequiredArgsConstructor
@Validated
public class UsefulMaterialController {

    private final UsefulMaterialService usefulMaterialService;
    private final UsefulMaterialMapper mapper;

    @PostMapping("create")
    @Operation(description = "Создать запись")
    public UsefulMaterialDto create(@Valid @RequestBody CreateUsefulMaterialDto dto) {
        CreateUsefulMaterialArgument argument = mapper.toCreateArgument(dto);
        return mapper.toDto(usefulMaterialService.create(argument));
    }

    @DeleteMapping("{id}")
    @Operation(description = "Удалить запись по ID")
    public void delete(@PathVariable("id") Long id) {
        usefulMaterialService.deleteById(id);
    }

    @PostMapping("{id}/update")
    @Operation(description = "Изменить запись по ID")
    public UsefulMaterialDto updateByID(@PathVariable("id") Long id,@Valid  @RequestBody UpdateUsefulMaterialDto dto) {
        UpdateUsefulMaterialArgument argument = mapper.toUpdateArgument(dto);
        return mapper.toDto(usefulMaterialService.updateByID(id, argument));
    }

    @GetMapping("{id}")
    @Operation(description = "Получить запись по ID")
    public UsefulMaterialDto getByID(@PathVariable("id") Long id) {
        UsefulMaterial material = usefulMaterialService.searchByID(id);
        return mapper.toDto(material);
    }

    @GetMapping("search/")
    @Operation(description = "Получить записи по части имени")
    public List<UsefulMaterialDto> searchByPartOfName(@RequestParam String partOfName) {
        List<UsefulMaterial> materials = usefulMaterialService.searchByPartOfName(partOfName);
        return mapper.toDtoList(materials);
    }
}
