package org.example.service.storade.argument;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateMaterialArgument {
    String name;
    String description;
    String link;
}

