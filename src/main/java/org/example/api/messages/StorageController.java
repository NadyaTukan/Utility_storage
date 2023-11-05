package org.example.api.messages;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.example.api.messages.dto.CreateMaterialDto;
import org.example.api.messages.dto.MaterialDto;
import org.example.api.messages.mapper.MaterialMapper;
import org.example.model.UsefulMaterial;
import org.example.service.StorageService;
import org.example.service.argument.CreateMaterialArgument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("messages")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;
    private final MaterialMapper mapper;

    @PostMapping("create")
    public MaterialDto create(@RequestBody CreateMaterialDto dto) {
        CreateMaterialArgument argument = mapper.toCreateArgument(dto);
        return mapper.toDto(storageService.create(argument));
    }

    @DeleteMapping("{id}")
    @ApiResponse(description = "Запись не найдена", responseCode = "404")
    public MaterialDto delete(@PathVariable("id") Long id) {
        return mapper.toDto(storageService.deleteById(id));
    }

    @PutMapping("{id}")
    public MaterialDto updateByID(@PathVariable("id")Long id, @RequestBody CreateMaterialDto dto) {
        CreateMaterialArgument argument = mapper.toCreateArgument(dto);
        return mapper.toDto(storageService.updateByID(id, argument));
    }

    @GetMapping("{id}")
    @ApiResponse(description = "Сообщение не найдено", responseCode = "404")
    public MaterialDto getByID(@PathVariable("id") Long id){
        UsefulMaterial material = storageService.searchByID(id);
        return  mapper.toDto(material);
    }

    @GetMapping("search/{partOfName}")
    public List<MaterialDto> searchByPartOfName(@PathVariable("partOfName") String partOfName) {
        List<UsefulMaterial> materials = storageService.searchByPartOfName(partOfName);
        return mapper.toDtoList(materials);
    }
}
