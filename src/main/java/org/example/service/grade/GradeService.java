package org.example.service.grade;

import org.example.model.Grade;
import org.example.service.grade.argument.CreateGradeArgument;

import java.util.List;

public interface GradeService {
    Grade create(CreateGradeArgument argument);

    void deleteById(Long id);

    List<Grade> searchByUsefulMaterialId(Long id);
}
