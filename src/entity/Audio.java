package entity;

public class Audio extends Entity {
    private String ArtistName;
    private String title;

    public Audio(String ArtistName, String title) {
        this.ArtistName = ArtistName;
        this.title = title;
    }
    public Audio(int ID, String ArtistName, String title) {
        this.setID(ID);
        this.ArtistName = ArtistName;
        this.title = title;
    }

    public String getArtistName() {
        return ArtistName;
    }
    public void setArtistName(String ArtistName) {
        this.ArtistName = ArtistName;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
