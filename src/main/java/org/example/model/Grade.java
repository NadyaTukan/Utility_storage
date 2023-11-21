package org.example.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Grade {
    private long id;
    private long idUsefulMaterial;
    private int grade;
    private String comment;

    public String toString() {
        return ("ID: " + id + "\nIDUsefulMaterial: " + idUsefulMaterial +
                "\nGrade: " + grade + "\nComment: " + comment + "\n");
    }
}
