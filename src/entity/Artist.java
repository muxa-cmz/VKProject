package entity;

/**
 * Created by Михаил on 24.10.2015.
 */

public class Artist extends Entity {
    private String name;

    public Artist(String name) {
        this.name = name;
    }
    public Artist(int ID, String name) {
        this.setID(ID);
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
