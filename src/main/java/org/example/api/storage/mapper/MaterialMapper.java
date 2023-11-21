package org.example.api.storage.mapper;

import org.example.api.storage.dto.CreateMaterialDto;
import org.example.api.storage.dto.MaterialDto;
import org.example.api.storage.dto.UpdateMaterialDto;
import org.example.model.UsefulMaterial;
import org.example.service.storade.argument.CreateMaterialArgument;
import org.example.service.storade.argument.UpdateMaterialArgument;
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
