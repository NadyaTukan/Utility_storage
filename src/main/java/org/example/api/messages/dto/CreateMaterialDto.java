package org.example.api.messages.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMaterialDto {
    String name;
    String description;
    String link;
}
