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
        if (this.ArtistName.contains("\'")){
            this.ArtistName = this.ArtistName.replaceAll("'", "_");
        }
        return ArtistName;
    }
    public void setArtistName(String ArtistName) {
        this.ArtistName = ArtistName;
    }
    public String getTitle() {
        if (this.title.contains("\'") || this.title.contains(";")){
            this.title = this.title.replaceAll("'", "_");
            this.title = this.title.replaceAll(";", "_");
        }
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
