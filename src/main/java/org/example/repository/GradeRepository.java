package org.example.repository;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import org.example.model.Grade;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Component
public class GradeRepository {
    private final Map<Long, Grade> grades = new HashMap<>();
    private Long nextId = 0L;

    public void clear() {
        this.grades.clear();
        nextId = 0L;
    }
    public void putAll(Map<Long, Grade> newGrades) {
        this.grades.putAll(newGrades);
        nextId = getMaxId();
    }

    public Grade create(Grade grade) {
        grade.setId(getNextId());
        grades.put(grade.getId(), grade);
        return grade;
    }

    public Grade delete(@NonNull Long id) {
        return grades.remove(id);
    }

    public List<Grade> searchByUsefulMaterialId(@NotBlank Long id) {
        List<Grade> results = grades.values()
                                    .stream()
                                    .filter(grade -> grade.getUsefulMaterialId() == id)
                                    .toList();
        if (!results.isEmpty()) {
            return results;
        } else {
            return null;
        }
    }

    public Long getMaxId() {
        return grades.keySet()
                     .stream()
                     .max(Long::compareTo)
                     .orElse(0L);
    }

    public Long getNextId() {
        return ++nextId;
    }
}
