package org.example.api.usefulMaterial.dto;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UsefulMaterialDto {
    Long id;
    String name;
    String description;
    String link;


}
