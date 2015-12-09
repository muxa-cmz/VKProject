package api;

import entity.Friend;
import entity.Song;
import entity.User;

import java.util.List;

/**
 * Created by Михаил on 08.12.2015.
 */
public interface IApi {

    String getVkToken();

    User getUserInfo();

    List<Friend> getListFriend(String userId);

    List<Song> getListSong(String ownerId);

}
