package org.example;

import java.util.Objects;

public class UsefulMaterial {
    private int id;
    private String name;
    private String description;
    private String link;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public UsefulMaterial(int id, String name, String description, String link){
        setId(id);
        setName(name);
        setDescription(description);
        setLink(link);
    }

    public String getInfo() {
        return ("ID: " + id + "\nName: " + name
                + "\nDescription: " + description + "\nLink: " + link + "\n");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        UsefulMaterial material = (UsefulMaterial) obj;
        return id == material.id && name.equals(material.name)
                && description.equals(material.description) && link.equals(material.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, link);
    }

}
