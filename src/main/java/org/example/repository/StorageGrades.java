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
public class StorageGrades {
    private final Map<Long, Grade> grades = new HashMap<>();
    private Long nextId = 0L;


    public Grade create(Grade grade) {
        grades.put(grade.getId(), grade);
        return grade;
    }

    public Grade delete(@NonNull Long id) {
        return grades.remove(id);
    }

    public List<Grade> searchById(@NotBlank Long id) {
        List<Grade> results = grades.values()
                                    .stream()
                                    .filter(grade -> grade.getIdUsefulMaterial() == id)
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
