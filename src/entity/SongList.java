package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valer on 26.10.2015.
 */
public class SongList {
    private int count;
    private List<Song> songs;

    public SongList() {
        songs = new ArrayList<Song>();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs.addAll(songs);
    }
}
