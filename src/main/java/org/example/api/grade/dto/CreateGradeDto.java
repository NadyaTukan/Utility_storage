package org.example.api.grade.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CreateGradeDto {
    @NotNull(message = "не указан ID материала")
    long usefulMaterialId;

    @Min(value = 1, message = "оценка должна быть больше 1")
    @Max(value = 5, message = "оценка должна быть меньше 5")
    int grade;

    @NotBlank(message = "не указан комментарий оценки")
    String comment;
}
