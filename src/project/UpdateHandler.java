
package project;

import InternetToDataBase.InsertToDataBase;
import api.Api;
import entity.*;
import mappers.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Михаил on 01.12.2015.
 */
public class UpdateHandler {

    private List<Friend> oldListFriend;
    private FriendList newListFriends;
    private List<Change> changes;
    private InsertToDataBase IDB;

    public UpdateHandler() {
        this.oldListFriend = new ArrayList<Friend>();
        this.newListFriends = new FriendList();
        this.changes = new ArrayList<Change>();
        this.IDB = new InsertToDataBase();
    }

    private List<Friend> addedFriends(FriendList newListFriends, List<Friend> oldListFriends){
        int i = 0;
        List<Friend> addedFriend = new ArrayList<Friend>();
        for (Friend newFriend : newListFriends.getFriends()){
            String newVkIDFriend = newFriend.getVkId();
            for (Friend oldFriend : oldListFriends){
                String oldVkIDFriend = oldFriend.getVkId();
                if (!oldVkIDFriend.equals(newVkIDFriend)){
                    i++;
                }
            }
            if (i == oldListFriends.size()) {
                addedFriend.add(newFriend);
            }
            i = 0;
        }
        return addedFriend;
    }

    private static List<Friend> removedFriends(FriendList newListFriends, List<Friend> oldListFriends){
        int i = 0;
        List<Friend> removedFriend = new ArrayList<Friend>();
        for (Friend oldFriend : oldListFriends){
            String oldVkIDFriend = oldFriend.getVkId();
            for (Friend newFriend : newListFriends.getFriends()){
                String newVkIDFriend = newFriend.getVkId();
                if (!newVkIDFriend.equals(oldVkIDFriend)){
                    i++;
                }
            }
            if (i == newListFriends.getFriends().size()) {
                removedFriend.add(oldFriend);
            }
            i = 0;
        }
        return removedFriend;
    }

    private static List<Song> addedSongs(SongList newListSongs, SongList oldListSongs) throws SQLException {
        int i = 0;
        List<Song> addedSong = new ArrayList<Song>();
        for (Song newSong : newListSongs.getSongs()){
            String newTitleSong = newSong.getTitle();
            String newArtistNameSong = newSong.getArtistName();
            for (Song oldSong : oldListSongs.getSongs()){
                String oldTitleSong = oldSong.getTitle();
                String oldArtistNameSong = oldSong.getArtistName();
                if (  !(!newTitleSong.equals(oldTitleSong) &  (!newArtistNameSong.equals(oldArtistNameSong)))  ){
                    i++;
                }
            }
            if (i == 0) {
                addedSong.add(newSong);
            }
            i = 0;
        }
        return addedSong;
    }

    private static List<Song> removedSongs(SongList newListSongs, SongList oldListSongs) throws SQLException {
        int i = 0;
        List<Song> removedSong = new ArrayList<Song>();
        for (Song oldSong : oldListSongs.getSongs()){
            String oldTitleSong = oldSong.getTitle();
            String oldArtistNameSong = oldSong.getArtistName();
            for (Song newSong : newListSongs.getSongs()){
                String newTitleSong = newSong.getTitle();
                String newArtistNameSong = newSong.getArtistName();
                if (  !(!oldTitleSong.equals(newTitleSong) &  (!oldArtistNameSong.equals(newArtistNameSong)))  ){
                    i++;
                }
            }
            if (i == 0) {
                removedSong.add(oldSong);
            }
            i = 0;
        }
        return removedSong;
    }

    /*Получение начального списка друзей из БД*/
    public void getListOfOldFriends(int IDUser) throws SQLException {
        UsersFriendsMapper usersFriendsMapper = new UsersFriendsMapper();
        FriendMapper friendMapper = new FriendMapper();
        List<UsersFriends> oldUserFriends = new ArrayList<UsersFriends>();

        oldUserFriends.addAll(usersFriendsMapper.FindByIdUser(IDUser));
        for (UsersFriends usersFriends : oldUserFriends){
            this.oldListFriend.add(friendMapper.FindById(usersFriends.getIDFriend()));
        }
    }

    /*Получение нового списка друзей из VK*/
    public void getListOfNewFriends(String VkIDUser){
        this.newListFriends.setFriends(Api.getListFriend(VkIDUser));
    }

    /*Проверка на то что у пользователя появились новые друзья*/
        /*и если таковые есть то сразу заносим все изменения в БД*/
        /*заполняем список changes, для новых пользователей все аудиозаписи c event = 1 */
    public void checkForChangesFriendsList_addition(User user) throws SQLException {
        List<Friend> addedFriend = new ArrayList<Friend>();
        FriendMapper addedFriendMapper = new FriendMapper();

        addedFriend.addAll(this.addedFriends(this.newListFriends, this.oldListFriend));
        if (addedFriend.size() != 0){
            IDB.InsertToFriendsTable(addedFriend);
            IDB.InsertToUsersFriendsTable(user, addedFriend);
            for (Friend friend : addedFriend){
                SongList songs = new SongList();
                List<Song> songList = Api.getListSong(friend.getVkId());
                songs.setSongs(songList);
                IDB.InsertToArtistsTable(songs.getSongs());
                IDB.InsertToAudioTable(songs.getSongs());
                this.changes.add(new Change(addedFriendMapper.FindByVkId(friend.getVkId()).getID(), songs.getSongs(), true));
            }
        }
    }

    /*Проверка на то что пользователь удалил кого то из друзей*/
        /* Находим удалившихся(удаленных) друзей и все их аудиозаписи помечаем как удаленные */
        /* Так же необходимо пройти по таблице Change и проверить записи с event true, и так же пометить их как удаленные*/
    public void checkForChangesFriendsList_removal() throws SQLException {
        FriendMapper removedFriendMapper = new FriendMapper();
        FriendsAudioMapper friendsAudioMapper = new FriendsAudioMapper();
        SongMapper songMapper = new SongMapper();
        ChangeMapper changeMapper = new ChangeMapper();

        List<Friend> removedFriend = new ArrayList<Friend>();
        removedFriend.addAll(removedFriends(this.newListFriends, this.oldListFriend));

        if (removedFriend.size() != 0) {
            for (Friend friend : removedFriend) {
                int idFriend = removedFriendMapper.FindByVkId(friend.getVkId()).getID();
                List<Integer> idAudioForFriend = new ArrayList<Integer>();
                idAudioForFriend.addAll(friendsAudioMapper.getIDAudioForFriend(idFriend));
                SongList songs = new SongList();
                List<Song> songList = new ArrayList<Song>();
                for (int idSong : idAudioForFriend) {
                    songList.add(songMapper.FindById(idSong));
                }
                List<Integer> idSongList = new ArrayList<Integer>();
                idSongList.addAll(changeMapper.FindByEvent(friend.getID(), true));
                for (int idSong : idSongList) {
                    songList.add(songMapper.FindById(idSong));
                }
                songs.setSongs(songList);
                this.changes.add(new Change(removedFriendMapper.FindByVkId(friend.getVkId()).getID(), songs.getSongs(), false));
            }
        }
    }

    /*Проверка на то что у старых друзей не поменялся список аудиозаписей*/
    public void checkForChangesListSongsOfOldFriends() throws SQLException {
        FriendsAudioMapper friendsAudioMapper = new FriendsAudioMapper();
        SongMapper songMapper = new SongMapper();
        ChangeMapper changeMapper = new ChangeMapper();
        List<Song> addedFriendAudioList = new ArrayList<Song>();
        List<Song> removedFriendAudioList = new ArrayList<Song>();

        for (Friend friend : oldListFriend){
            SongList oldFriendAudioList = friendsAudioMapper.getSongListForFriend(friend.getID()); //.getIDAudioForFriend(friend.getID());
            SongList newFriendAudioList = new SongList();
            List<Integer> idSongsOfChangeTrue = new ArrayList<Integer>();
            idSongsOfChangeTrue.addAll(changeMapper.FindByEvent(friend.getID(), true));
            List<Song> tempSongListTrue = new ArrayList<Song>();
            for (Integer idSong : idSongsOfChangeTrue){
                tempSongListTrue.add( songMapper.FindById(idSong));
            }
            oldFriendAudioList.setSongs(tempSongListTrue);

            List<Integer> idSongsOfChangeFalse = new ArrayList<Integer>();
            idSongsOfChangeFalse.addAll(changeMapper.FindByEvent(friend.getID(), false));
            List<Song> tempSongListFalse = new ArrayList<Song>();
            for (Integer idSong : idSongsOfChangeFalse){
                tempSongListFalse.add(songMapper.FindById(idSong));
            }
            for (Song song : tempSongListFalse) {
                for (Song oldSong : oldFriendAudioList.getSongs()){
                    //if (  (oldSong.getTitle() == song.getTitle()) && (oldSong.getArtistName() == song.getArtistName())  ){
                    if (oldSong.getTitle().equals(song.getTitle())){
                        if (oldSong.getArtistName().equals(song.getArtistName())) {
                            oldFriendAudioList.getSongs().remove(oldSong);
                            break;
                        }
                    }
                }
            }

            SongList songs = new SongList();
            List<Song> songList = Api.getListSong(friend.getVkId());
            newFriendAudioList.setSongs(songList);
            IDB.InsertToArtistsTable(newFriendAudioList.getSongs());
            IDB.InsertToAudioTable(newFriendAudioList.getSongs());

            addedFriendAudioList.addAll(addedSongs(newFriendAudioList, oldFriendAudioList));
            if (addedFriendAudioList.size() != 0){
                List<Song> tempList = new ArrayList<Song>();
                tempList.addAll(addedFriendAudioList);
                this.changes.add(new Change(friend.getID(), tempList, true));
                addedFriendAudioList.clear();
            }
            removedFriendAudioList.addAll(removedSongs(newFriendAudioList, oldFriendAudioList));
            if (removedFriendAudioList.size() != 0){
                List<Song> tempList = new ArrayList<Song>();
                tempList.addAll(removedFriendAudioList);
                this.changes.add(new Change(friend.getID(), tempList, false));
                removedFriendAudioList.clear();
            }
        }
    }

    public void insertChangeTable(){
        this.IDB.InsertToChangeTable(changes);
    }

}
