package org.example.api.storage.dto;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MaterialDto {
    Long id;
    String name;
    String description;
    String link;


}
