package org.example.api.messages.mapper;

import org.example.api.messages.dto.CreateMaterialDto;
import org.example.api.messages.dto.MaterialDto;
import org.example.api.messages.dto.UpdateMaterialDto;
import org.example.model.UsefulMaterial;
import org.example.service.argument.CreateMaterialArgument;
import org.example.service.argument.UpdateMaterialArgument;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface MaterialMapper {

    CreateMaterialArgument toCreateArgument(CreateMaterialDto dto);

    UpdateMaterialArgument toUpdateArgument(UpdateMaterialDto dto);
    MaterialDto toDto(UsefulMaterial material);

    List<MaterialDto> toDtoList(List<UsefulMaterial> materials);


}
