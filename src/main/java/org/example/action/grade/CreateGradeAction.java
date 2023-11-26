package org.example.action.grade;

import lombok.RequiredArgsConstructor;
import org.example.model.Grade;
import org.example.service.grade.GradeService;
import org.example.service.grade.argument.CreateGradeArgument;
import org.example.service.usefulMaterial.UsefulMaterialService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateGradeAction {
    private final GradeService gradeService;
    private final UsefulMaterialService usefulMaterialService;

    public Grade create(CreateGradeActionArgument argument) {
        existsById(argument);

        return gradeService.create(CreateGradeArgument.builder()
                                                      .usefulMaterialId(argument.getUsefulMaterialId())
                                                      .grade(argument.getGrade())
                                                      .comment(argument.getComment())
                                                      .build());
    }

    private void existsById(CreateGradeActionArgument argument) {
        usefulMaterialService.searchByID(argument.getUsefulMaterialId());
    }

}
