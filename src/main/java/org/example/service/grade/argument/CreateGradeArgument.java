package org.example.service.grade.argument;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateGradeArgument {
    long usefulMaterialId;

    int grade;

    String comment;
}
