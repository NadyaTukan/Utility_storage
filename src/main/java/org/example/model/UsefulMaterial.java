package org.example.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UsefulMaterial {
    private long id;
    private String name;
    private String description;
    private String link;

    @Override
    public String toString() {
        return ("ID: " + id + "\nName: " + name
                + "\nDescription: " + description + "\nLink: " + link + "\n");
    }


}
