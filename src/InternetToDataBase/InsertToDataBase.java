package InternetToDataBase;

import entity.*;
import mappers.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Михаил on 18.11.2015.
 */
public class InsertToDataBase<T> {

//    private IVkEntityMapperBase<T> iVkEntityMapperBase;
//    private IEntityMapperBase<T> iEntityMapperBase;
    //Object mapper = null;

    public void InsertToUsersTable(User user){
        UserMapper mapper = new UserMapper();
        mapper.Insert(user);
    }

    public void InsertToFriendsTable(List<Friend> friends){
        FriendMapper mapper = new FriendMapper();
        for (Friend friend : friends){
            mapper.Insert(friend);
        }
    }

    public void InsertToUsersFriendsTable(User user, List<Friend> friends) throws SQLException {
        UsersFriends usersFriends;
        UserMapper userMapper = new UserMapper();
        FriendMapper friendMapper = new FriendMapper();
        UsersFriendsMapper mapper = new UsersFriendsMapper();
        List<UsersFriends> usersFriendsList = new ArrayList<UsersFriends>();
        int idUser = userMapper.FindByVkId(user.getVkId()).getID();
        int idFriend = -1;
        for (Friend friend : friends){
            idFriend = friendMapper.FindByVkId(friend.getVkId()).getID();
            usersFriends = new UsersFriends(idUser, idFriend);
            usersFriendsList.add(usersFriends);
        }
        mapper.Insert(usersFriendsList);
    }

    public void InsertToArtistsTable(List<Song> songs){
        ArtistMapper artistMapper = new ArtistMapper();
        Artist artist;
        List<Artist> artists = new ArrayList<Artist>();
        for (Song song : songs){
            artist = new Artist(song.getArtistName());
            artists.add(artist);
        }
        artistMapper.Insert(artists);
    }

    public void InsertToAudioTable(List<Song> songs) throws SQLException {
        SongMapper songMapper = new SongMapper();
        songMapper.Insert(songs);
    }

    public void InsertToFriendsAudioTable(List<FriendsAudio> friendsAudio) throws SQLException {
        FriendsAudioMapper friendsAudioMapper = new FriendsAudioMapper();
        friendsAudioMapper.Insert(friendsAudio);
    }

    public void InsertToChangeTable(List<Change> changes){
        ChangeMapper changeMapper = new ChangeMapper();
        changeMapper.Insert(changes);
    }

}
