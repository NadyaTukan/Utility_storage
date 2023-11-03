package org.example;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode

public class UsefulMaterial {
    private int id;
    private String name;
    private String description;
    private String link;


    @Override
    public String toString() {
        return ("ID: " + id + "\nName: " + name
                + "\nDescription: " + description + "\nLink: " + link + "\n");
    }


}
