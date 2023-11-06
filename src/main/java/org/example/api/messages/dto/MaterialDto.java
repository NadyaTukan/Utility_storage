package org.example.api.messages.dto;

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
