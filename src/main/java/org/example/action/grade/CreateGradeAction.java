package org.example.action.grade;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.model.Grade;
import org.example.service.grade.GradeService;
import org.example.service.grade.argument.CreateGradeArgument;
import org.example.service.storade.StorageService;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@RequiredArgsConstructor
@Validated
public class CreateGradeAction {
    private final GradeService gradeService;
    private final StorageService storageService;

    public Grade create(@Valid CreateGradeActionArgument argument) {
        storageService.searchByID(argument.getIdUsefulMaterial());

        return gradeService.create(CreateGradeArgument.builder()
                                                      .idUsefulMaterial(argument.getIdUsefulMaterial())
                                                      .grade(argument.getGrade())
                                                      .comment(argument.getComment())
                                                      .build());
    }

}
