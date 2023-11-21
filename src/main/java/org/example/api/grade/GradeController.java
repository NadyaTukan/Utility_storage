package org.example.api.grade;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.action.grade.CreateGradeAction;
import org.example.api.grade.dto.CreateGradeDto;
import org.example.api.grade.dto.GradeDto;
import org.example.api.grade.mapper.GradeMapper;
import org.example.model.Grade;
import org.example.service.grade.GradeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;
    private final GradeMapper mapper;
    private final CreateGradeAction createGradeAction;

    @PostMapping("create")
    @Operation(description = "Создать оценку")
    public GradeDto create(@RequestBody CreateGradeDto dto) {
        Grade grade = createGradeAction.create(mapper.toCreateActionArgument(dto));
        return mapper.toDto(grade);
    }

    @DeleteMapping("{id}")
    @Operation(description = "Удалить оценку по ID")
    public void delete(@PathVariable("id") Long id) {
        gradeService.deleteById(id);
    }

    @GetMapping("search/")
    @Operation(description = "Получить оценки по ID записи")
    public List<GradeDto> searchByPartOfName(@RequestParam long idMatirial) {
        List<Grade> grades = gradeService.searchById(idMatirial);
        return mapper.toDtoList(grades);
    }
}
