package project;

import InternetToDataBase.InsertToDataBase;
import api.Api;
import entity.*;
import mappers.*;
import org.apache.commons.lang.time.StopWatch;
import org.apache.http.HttpException;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Михаил on 15.09.2015.
 */

public class VVProject {


    public static List<Friend> addedFriends(FriendList newListFriends, List<Friend> oldListFriends){
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

    public static List<Friend> removedFriends(FriendList newListFriends, List<Friend> oldListFriends){
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


    public static List<Song> addedSongs(SongList newListSongs, List<Song> oldListSongs) throws SQLException {
        int i = 0;
        List<Song> addedSong = new ArrayList<Song>();
        for (Song newSong : newListSongs.getSongs()){
            String newTitleSong = newSong.getTitle();
            String newArtistNameSong = newSong.getArtistName();
            for (Song oldSong : oldListSongs){
                SongMapper songMapper = new SongMapper();
                Song tempSong = songMapper.FindById(oldSong.getID());
                String oldTitleSong = tempSong.getTitle();
                String oldArtistNameSong = tempSong.getArtistName();
                if ( !((!newTitleSong.equals(oldTitleSong)) && (!newArtistNameSong.equals(oldArtistNameSong))) ){
                    i++;
                }
            }
            if (i == oldListSongs.size()) {
                addedSong.add(newSong);
            }
            i = 0;
        }
        return addedSong;
    }

    public static List<Song> removedSongs(SongList newListSongs, List<Song> oldListSongs) throws SQLException {
        int i = 0;
        List<Song> addedSong = new ArrayList<Song>();
        for (Song oldSong : oldListSongs){
            SongMapper songMapper = new SongMapper();
            Song tempSong = songMapper.FindById(oldSong.getID());
            String oldTitleSong = tempSong.getTitle();
            String oldArtistNameSong = tempSong.getArtistName();
            for (Song newSong : newListSongs.getSongs()){
                String newTitleSong = newSong.getTitle();
                String newArtistNameSong = newSong.getArtistName();
                if ( !((!oldTitleSong.equals(newTitleSong)) && (!oldArtistNameSong.equals(newArtistNameSong))) ){
                    i++;
                }
            }
            if (i == newListSongs.getSongs().size()) {
                addedSong.add(oldSong);
            }
            i = 0;
        }
        return addedSong;
    }



    public static void main(String args[]) throws URISyntaxException, IOException, HttpException, ParseException, SQLException, java.text.ParseException {

//        Friend friend = new Friend("Привет", "12.12.1992", "ж");
//        Object mapper = null;
//        Change change = new Change("19.12.1992", 30, 1, true);
//        if (change instanceof Change){
//            mapper = new mappers.ChangeMapper();
//        }
//
////        IEntityMapperBase<Change> ivk = (IEntityMapperBase<Change>) mapper;
////        ivk.Insert(change);
//
//
//        //UsersFriends au = new UsersFriends(7, 30);
//        ChangeMapper aum = new ChangeMapper();
//        aum.FindByIdFriend(30);
////        aum.FindByIdFriend(30);
//
//        //VKClient vkClient = new VKClient();
////        vkClient.TopArtist(args[0], Integer.parseInt(args[1]));

//        Thread myThready = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Привет из побочного потока!");
//                User user = Api.getUserInfo();
//            }
//        });
//
//        myThready.start();	//Запуск потока
        StopWatch stopWatch = new StopWatch();
        FileWriter writer = new FileWriter("C:\\Users\\Михаил\\Desktop\\log_vk.txt", true);
        User user = Api.getUserInfo();
        InsertToDataBase IDB = new InsertToDataBase();
        String text = "";
        switch (Integer.parseInt(args[2])){
            case 1:{
                FriendList friends = new FriendList();
                stopWatch.start();
                friends.setFriends(Api.getListFriend(user.getVkId()));
                stopWatch.stop();
                text = "---VkApi: getListFriend = \t" + stopWatch + "\r\n";
                writer.write(text);
                writer.flush();

                stopWatch.reset();
                stopWatch.start();
                IDB.InsertToUsersTable(user);
                stopWatch.stop();
                text = "---InsertToUsersTable = \t" + stopWatch + "\r\n";
                writer.write(text);
                writer.flush();

                stopWatch.reset();
                stopWatch.start();
                IDB.InsertToFriendsTable(friends.getFriends());               // Работает
                stopWatch.stop();
                text = "---InsertToFriendsTable = \t" + stopWatch + "\r\n";
                writer.write(text);
                writer.flush();

                stopWatch.reset();
                stopWatch.start();
                IDB.InsertToUsersFriendsTable(user, friends.getFriends());    // Работает
                stopWatch.stop();
                text = "---InsertToUsersFriendsTable = \t" + stopWatch + "\r\n";
                writer.write(text);
                writer.flush();

                List<FriendsAudio> friendsAudioList = new ArrayList<FriendsAudio>();
                int iteratorGetListSong = 0;
                int iteratorFriendsAudioTable = 0;
                for (Friend friend : friends.getFriends()){
                    SongList songs = new SongList();
                    stopWatch.reset();
                    stopWatch.start();
                    List<Song> songList = Api.getListSong(friend.getVkId());
                    stopWatch.stop();
                    text = "---VkApi:getListSong = \t\t" + stopWatch + "(" + ++iteratorGetListSong + " friend)\r\n";
                    writer.write(text);
                    writer.flush();

                    songs.setSongs(songList);
                    stopWatch.reset();
                    stopWatch.start();
                    IDB.InsertToArtistsTable(songs.getSongs());                // Работает
                    stopWatch.stop();
                    text = "---InsertToArtistsTable = \t" + stopWatch + "(" + songs.getSongs().size() + ")\r\n";
                    writer.write(text);
                    writer.flush();

                    stopWatch.reset();
                    stopWatch.start();
                    IDB.InsertToAudioTable(songs.getSongs());
                    stopWatch.stop();
                    text = "---InsertToAudioTable = \t" + stopWatch + "(" + songs.getSongs().size() + ")\r\n\r\n\r\n";
                    writer.write(text);
                    writer.flush();

                    friendsAudioList.add(new FriendsAudio(friend.getVkId(), songs.getSongs()));
                }
                stopWatch.reset();
                stopWatch.start();
                IDB.InsertToFriendsAudioTable(friendsAudioList);
                stopWatch.stop();
                text = "---InsertToFriendsAudioTable = " + stopWatch + "(" + ++iteratorFriendsAudioTable + ")\r\n";
                writer.write(text);
                writer.flush();

                break;
            }
            case 2:{
                String VkIDUser = user.getVkId();
                UserMapper userMapper = new UserMapper();
                int IDUser = userMapper.FindByVkId(VkIDUser).getID();
                List<Change> changes = new ArrayList<Change>();
                /*Получаем данные из базы данных*/
                FriendsAudioMapper FAM = new FriendsAudioMapper();
                List<FriendsAudio> oldFriendsAudioList = new ArrayList<FriendsAudio>();
                oldFriendsAudioList.addAll(FAM.getAudiosForFriends(user)); // получили из базы список VkIDFriend и его список аудиозаписей
                UsersFriendsMapper usersFriendsMapper = new UsersFriendsMapper();
                List<UsersFriends> oldUserFriends = new ArrayList<UsersFriends>();
                oldUserFriends.addAll(usersFriendsMapper.FindByIdUser(IDUser));
                FriendMapper friendMapper = new FriendMapper();
                List<Friend> oldListFriend = new ArrayList<Friend>();
                for (UsersFriends usersFriends : oldUserFriends){
                    oldListFriend.add(friendMapper.FindById(usersFriends.getIDFriend()));
                }


                /*Получам свежие данные из vk*/
                FriendList newListFriends = new FriendList();
                newListFriends.setFriends(Api.getListFriend(VkIDUser)); // получаем новый список друзей, возможно отличный от базы
                //List<FriendsAudio> newFriendsAudioList = new ArrayList<FriendsAudio>();
//                for (Friend friend : newListFriends.getFriends()){
//                    SongList songs = new SongList();
//                    List<Song> songList = Api.getListSong(friend.getVkId());
//                    songs.setSongs(songList);
//                    newFriendsAudioList.add(new FriendsAudio(friend.getVkId(), songs.getSongs()));
//                }

                /*Проверка на то что у нашего чувака не появилось новых друзей*/
                List<Friend> addedFriend = new ArrayList<Friend>();
                addedFriend.addAll(addedFriends(newListFriends, oldListFriend));
//                int i = 0;
//                List<Friend> addedFriend = new ArrayList<Friend>();
//                for (Friend newFriend : newListFriends.getFriends()){
//                    String newVkIDFriend = newFriend.getVkId();
//                    for (Friend oldFriend : oldListFriend){
//                        String oldVkIDFriend = oldFriend.getVkId();
//                        if (!oldVkIDFriend.equals(newVkIDFriend)){
//                            i++;
//                        }
//                    }
//                    if (i == oldListFriend.size()) {
//                        addedFriend.add(newFriend);
//                    }
//                    i = 0;
//                }

                /*Если есть кого добавить в БД, то добавляем*/
                if (addedFriend.size() != 0){
                    IDB.InsertToFriendsTable(addedFriend);
                    IDB.InsertToUsersFriendsTable(user, addedFriend);
                    FriendMapper addedFriendMapper = new FriendMapper();

                    for (Friend friend : addedFriend){
                        SongList songs = new SongList();
                        List<Song> songList = Api.getListSong(friend.getVkId());
                        songs.setSongs(songList);
                        IDB.InsertToArtistsTable(songs.getSongs());
                        IDB.InsertToAudioTable(songs.getSongs());
                        changes.add(new Change(addedFriendMapper.FindByVkId(friend.getVkId()).getID(), songs.getSongs(), true));
                    }
                }


                /* Находим удалившихся(удаленных) друзей и все их аудиозаписи помечаем как удаленные */
                /* Так же необходимо пройти по таблице Change и проверить записи с event true, и так же пометить их как удаленные*/
                List<Friend> removedFriend = new ArrayList<Friend>();
                removedFriend.addAll(removedFriends(newListFriends, oldListFriend));

                if (removedFriend.size() != 0){
                    FriendMapper removedFriendMapper = new FriendMapper();
                    FriendsAudioMapper friendsAudioMapper = new FriendsAudioMapper();
                    ChangeMapper changeMapper = new ChangeMapper();
                    for (Friend friend : removedFriend){
                        int idFriend = removedFriendMapper.FindByVkId(friend.getVkId()).getID();
                        List<Integer> idAudioForFriend = new ArrayList<Integer>();
                        idAudioForFriend.addAll(friendsAudioMapper.getIDAudioForFriend(idFriend));
                        SongMapper songMapper = new SongMapper();
                        SongList songs = new SongList();
                        List<Song> songList = new ArrayList<Song>();
                        for (int idSong : idAudioForFriend){
                            songList.add(songMapper.FindById(idSong));
                        }
                        List<Integer> idSongList =new ArrayList<Integer>();
                        idSongList.addAll(changeMapper.FindByEvent(friend.getID(), true));
                        for (int idSong : idSongList){
                            songList.add(songMapper.FindById(idSong));
                        }
                        songs.setSongs(songList);
                        changes.add(new Change(removedFriendMapper.FindByVkId(friend.getVkId()).getID(), songs.getSongs(), false));
                    }
                }

                IDB.InsertToChangeTable(changes);
                /*Алгоритм вычисления изменений о первоначальном состоянии БД для пользователя*/




                break;
            }
        }






        System.out.println(user.getFirstName() + " " + user.getLastName() + " id = " + user.getVkId());
    }
}
