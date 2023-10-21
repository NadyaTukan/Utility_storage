package org.example;


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

    public UsefulMaterial() {

    }
    public UsefulMaterial(int identifier, String name, String description, String link){
        setId(identifier);
        setName(name);
        setDescription(description);
        setLink(link);
    }

    public String getInfo() {
        return ("ID: " + id + "\nName: " + name
                + "\nDescription: " + description + "\nLink: " + link + "\n");
    }
}
