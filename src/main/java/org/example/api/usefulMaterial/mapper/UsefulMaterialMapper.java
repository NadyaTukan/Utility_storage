package org.example.api.usefulMaterial.mapper;

import org.example.api.usefulMaterial.dto.CreateUsefulMaterialDto;
import org.example.api.usefulMaterial.dto.UpdateUsefulMaterialDto;
import org.example.api.usefulMaterial.dto.UsefulMaterialDto;
import org.example.model.UsefulMaterial;
import org.example.service.usefulMaterial.argument.CreateUsefulMaterialArgument;
import org.example.service.usefulMaterial.argument.UpdateUsefulMaterialArgument;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UsefulMaterialMapper {

    CreateUsefulMaterialArgument toCreateArgument(CreateUsefulMaterialDto dto);

    UpdateUsefulMaterialArgument toUpdateArgument(UpdateUsefulMaterialDto dto);

    UsefulMaterialDto toDto(UsefulMaterial material);

    List<UsefulMaterialDto> toDtoList(List<UsefulMaterial> materials);


}
