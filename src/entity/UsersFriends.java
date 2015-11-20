package entity;

/**
 * Created by Михаил on 24.10.2015.
 */
public class UsersFriends extends Entity {
    private int IDUser;
    private int IDFriend;

    public UsersFriends(int IDUser, int IDFriend) {
        this.IDUser = IDUser;
        this.IDFriend = IDFriend;
    }

    public UsersFriends(int ID,int IDUser, int IDFriend) {
        this.setID(ID);
        this.IDUser = IDUser;
        this.IDFriend = IDFriend;
    }

    public int getIDUser() {
        return IDUser;
    }
    public void setIDUser(int IDUser) {
        IDUser = IDUser;
    }
    public int getIDFriend() {
        return IDFriend;
    }
    public void setIDFriend(int vkIDFriend) {
        this.IDFriend = IDFriend;
    }
}