package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valer on 26.10.2015.
 */
public class FriendList {
    private int count;
    private List<Friend> friends;

    public FriendList() {
        friends = new ArrayList<Friend>();
    }
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public List<Friend> getFriends() {
        return friends;
    }
    public void setFriends(List<Friend> friends) {
        this.friends.addAll(friends);
    }
}
