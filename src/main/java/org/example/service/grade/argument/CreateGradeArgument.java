package org.example.service.grade.argument;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateGradeArgument {
    @NotNull(message = "не указан ID записи")
    long idUsefulMaterial;

    @Min(value = 1, message = "оценка должна быть больше 1")
    @Max(value = 5, message = "оценка должна быть меньше 5")
    int grade;

    @NotBlank(message = "не указан комментарий")
    String comment;
}
