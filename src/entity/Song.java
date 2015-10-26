package entity;

import com.google.gson.annotations.SerializedName;

public class Song extends Entity {
    @SerializedName("artist")
    private String ArtistName;
    private String title;

    public Song(String ArtistName, String title) {
        this.ArtistName = ArtistName;
        this.title = title;
    }
    public Song(int ID, String ArtistName, String title) {
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
