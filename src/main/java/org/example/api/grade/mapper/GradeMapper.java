package org.example.api.grade.mapper;

import org.example.action.grade.CreateGradeActionArgument;
import org.example.api.grade.dto.CreateGradeDto;
import org.example.api.grade.dto.GradeDto;
import org.example.model.Grade;
import org.example.service.grade.argument.CreateGradeArgument;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface GradeMapper {
    CreateGradeArgument toCreateArgument(CreateGradeDto dto);

    CreateGradeActionArgument toCreateActionArgument(CreateGradeDto dto);

    GradeDto toDto(Grade grade);

    List<GradeDto> toDtoList(List<Grade> grades);

}
