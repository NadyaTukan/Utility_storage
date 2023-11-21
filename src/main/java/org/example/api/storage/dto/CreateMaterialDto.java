package org.example.api.storage.dto;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CreateMaterialDto {
    String name;
    String description;
    String link;
}
