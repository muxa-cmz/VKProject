package entity;

import java.util.List;

/**
 * Created by Михаил on 24.10.2015.
 */
public class Change extends Entity {
    private String date;
    private int IDFriend;
    private List<Song> listAudio;
    //private int IDAudio;
    private boolean event;

    public Change(int IDFriend, List<Song> listAudio, boolean event) {
        this.IDFriend = IDFriend;
        //this.IDAudio = IDAudio;
        this.listAudio = listAudio;
        this.event = event;
    }

    public Change(String date, int IDFriend, List<Song> listAudio, boolean event) {
        this.date = date;
        this.IDFriend = IDFriend;
        this.listAudio.addAll(listAudio);
        //this.IDAudio = IDAudio;
        this.event = event;
    }

    public Change(int ID, String date, int IDFriend, List<Song> listAudio, boolean event) {
        this.setID(ID);
        this.date = date;
        this.IDFriend = IDFriend;
        this.listAudio.addAll(listAudio);
        //this.IDAudio = IDAudio;
        this.event = event;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getIDFriend() {
        return IDFriend;
    }
    public void setIDFriend(int IDFriend) {
        this.IDFriend = IDFriend;
    }
    public List<Song> getIDAudio() {
        return listAudio;
    }
    //public void setIDAudio(int IDAudio) {
       // this.IDAudio = IDAudio;
    //}
    public boolean getEvent() {
        return event;
    }
    public void setEvent(boolean event) {
        this.event = event;
    }
}
