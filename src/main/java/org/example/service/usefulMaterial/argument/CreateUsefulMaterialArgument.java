package org.example.service.usefulMaterial.argument;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUsefulMaterialArgument {
    String name;
    String description;
    String link;
}
