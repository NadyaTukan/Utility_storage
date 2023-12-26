package org.example.action.grade;

import lombok.Builder;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

@Value
@Builder
@Validated
public class CreateGradeActionArgument {
    long usefulMaterialId;
    int grade;
    String comment;
}
