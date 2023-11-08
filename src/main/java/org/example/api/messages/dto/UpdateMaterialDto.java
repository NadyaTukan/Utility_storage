package org.example.api.messages.dto;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMaterialDto {
    String name;
    String description;
    String link;
}

