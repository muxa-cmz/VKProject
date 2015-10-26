package entity;

/**
 * Created by Михаил on 24.10.2015.
 */
public class UsersFriends extends Entity {
    private int VkIDUser;
    private int VkIDFriend;

    public UsersFriends(int vkIDUser, int vkIDFriend) {
        VkIDUser = vkIDUser;
        VkIDFriend = vkIDFriend;
    }

    public UsersFriends(int ID,int vkIDUser, int vkIDFriend) {
        this.setID(ID);
        VkIDUser = vkIDUser;
        VkIDFriend = vkIDFriend;
    }

    public int getVkIDUser() {
        return VkIDUser;
    }
    public void setVkIDUser(int vkIDUser) {
        VkIDUser = vkIDUser;
    }
    public int getVkIDFriend() {
        return VkIDFriend;
    }
    public void setVkIDFriend(int vkIDFriend) {
        VkIDFriend = vkIDFriend;
    }
}