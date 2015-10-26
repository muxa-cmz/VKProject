package entity;

public class FriendsAudio extends Entity {
    private int IDFriend;
    private int IDAudio;

    public FriendsAudio(int vkIDFriend, int IDAudio) {
        IDFriend = vkIDFriend;
        this.IDAudio = IDAudio;
    }

    public FriendsAudio(int ID, int IDFriend, int IDAudio) {
        this.setID(ID);
        this.IDFriend = IDFriend;
        this.IDAudio = IDAudio;
    }

    public int getIDAudio() {
        return IDAudio;
    }
    public void setIDAudio(int IDAudio) {
        this.IDAudio = IDAudio;
    }
    public int getIDFriend() {
        return IDFriend;
    }
    public void setIDFriend(int IDFriend) {
        this.IDFriend = IDFriend;
    }
}
