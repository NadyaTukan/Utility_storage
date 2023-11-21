package org.example.service.grade;

import lombok.RequiredArgsConstructor;
import org.example.exception.NotFoundException;
import org.example.model.Grade;
import org.example.repository.StorageGrades;
import org.example.service.grade.argument.CreateGradeArgument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GradeServiceImpl implements GradeService {
    @Autowired
    private final StorageGrades storageGrades;

    @Override
    public Grade create(CreateGradeArgument argument) {
        long id = storageGrades.getNextId();
        return storageGrades.create(Grade.builder()
                                         .id(id)
                                         .idUsefulMaterial(argument.getIdUsefulMaterial())
                                         .grade(argument.getGrade())
                                         .comment(argument.getComment())
                                         .build());
    }

    @Override
    public void deleteById(Long id) {
        Optional.ofNullable(storageGrades.delete(id))
                .orElseThrow(() -> new NotFoundException("Оценка не найдена."));
    }

    @Override
    public List<Grade> searchById(Long id) {
        return Optional.ofNullable(storageGrades.searchById(id))
                       .orElseThrow(() -> new NotFoundException("Материал не найден."));
    }

}
