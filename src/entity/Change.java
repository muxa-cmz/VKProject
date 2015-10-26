package entity;

/**
 * Created by Михаил on 24.10.2015.
 */
public class Change extends Entity {
    private String date;
    private int IDFriend;
    private int IDAudio;
    private boolean event;

    public Change(String date, int IDFriend, int IDAudio, boolean event) {
        this.date = date;
        this.IDFriend = IDFriend;
        this.IDAudio = IDAudio;
        this.event = event;
    }

    public Change(int ID, String date, int IDFriend, int IDAudio, boolean event) {
        this.setID(ID);
        this.date = date;
        this.IDFriend = IDFriend;
        this.IDAudio = IDAudio;
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
    public int getIDAudio() {
        return IDAudio;
    }
    public void setIDAudio(int IDAudio) {
        this.IDAudio = IDAudio;
    }
    public boolean getEvent() {
        return event;
    }
    public void setEvent(boolean event) {
        this.event = event;
    }
}
