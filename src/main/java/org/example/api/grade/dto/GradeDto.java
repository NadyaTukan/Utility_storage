package org.example.api.grade.dto;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GradeDto {
    long id;
    long usefulMaterialId;
    int grade;
    String comment;
}
