package org.example.service.grade;

import lombok.RequiredArgsConstructor;
import org.example.exception.NotFoundException;
import org.example.model.Grade;
import org.example.repository.GradeRepository;
import org.example.service.grade.argument.CreateGradeArgument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GradeServiceImpl implements GradeService {
    @Autowired
    private final GradeRepository gradeRepository;

    @Override
    public Grade create(CreateGradeArgument argument) {
        long id = gradeRepository.getNextId();
        return gradeRepository.create(Grade.builder()
                                           .id(id)
                                           .usefulMaterialId(argument.getUsefulMaterialId())
                                           .grade(argument.getGrade())
                                           .comment(argument.getComment())
                                           .build());
    }

    @Override
    public void deleteById(Long id) {
        Optional.ofNullable(gradeRepository.delete(id))
                .orElseThrow(() -> new NotFoundException("Оценка не найдена."));
    }

    @Override
    public List<Grade> searchByUsefulMaterialId(Long id) {
        return Optional.ofNullable(gradeRepository.searchByUsefulMaterialId(id))
                       .orElseThrow(() -> new NotFoundException("Материал не найден."));
    }

}
