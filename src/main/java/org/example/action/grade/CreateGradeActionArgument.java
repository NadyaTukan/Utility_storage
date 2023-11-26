package org.example.action.grade;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

@Value
@Builder
@Validated
public class CreateGradeActionArgument {
    @NotNull(message = "не указан id записи")
    long usefulMaterialId;
    @Min(value = 1, message = "оценка не должна быть меньше 1")
    @Max(value = 5, message = "оценка не должна быть больше 5")
    int grade;
    @NotBlank(message = "не указан комментарий оценки")
    String comment;
}
