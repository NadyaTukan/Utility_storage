package org.example.service.argument;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateMaterialArgument {
    String name;
    String description;
    String link;
}

