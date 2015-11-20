package entity;

import java.util.List;

public class FriendsAudio extends Entity {
    private String VkIDFriend;
    //private String IDAudio;
    private List<Song> listAudio;

    public FriendsAudio(String vkIDFriend, List<Song> listAudio) {
        VkIDFriend = vkIDFriend;
        //this.IDAudio = IDAudio;
       this.listAudio = listAudio;
    }

//    public String getIDAudio() {
//        return IDAudio;
//    }
//    public void setIDAudio(String IDAudio) {
//        this.IDAudio = IDAudio;
//    }
    public String getVkIDFriend() {
        return VkIDFriend;
    }
    public void setVkIDFriend(String vkIDFriend) {
        VkIDFriend = vkIDFriend;
    }
    public List<Song> getListAudio() {
        return listAudio;
    }
    public void setListAudio(List<Song> listAudio) {
        this.listAudio = listAudio;
    }
}
