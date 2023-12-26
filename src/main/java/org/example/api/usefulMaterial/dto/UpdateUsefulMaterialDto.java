package org.example.api.usefulMaterial.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUsefulMaterialDto {
    @NotBlank(message = "не указано имя материала")
    String name;

    @NotBlank(message = "не указано описание материала")
    String description;

    @NotBlank(message = "не указана ссылка материала")
    String link;
}

