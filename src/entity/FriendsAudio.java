package entity;

public class FriendsAudio extends Entity {
    private String VkIDFriend;
    private String IDAudio;

    public FriendsAudio(String vkIDFriend, String IDAudio) {
        VkIDFriend = vkIDFriend;
        this.IDAudio = IDAudio;
    }

    public String getIDAudio() {
        return IDAudio;
    }
    public void setIDAudio(String IDAudio) {
        this.IDAudio = IDAudio;
    }
    public String getVkIDFriend() {
        return VkIDFriend;
    }
    public void setVkIDFriend(String vkIDFriend) {
        VkIDFriend = vkIDFriend;
    }
}
