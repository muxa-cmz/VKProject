package project;

import InternetToDataBase.InsertToDataBase;
import api.Api;
import entity.*;
import mappers.FriendMapper;
import mappers.FriendsAudioMapper;
import mappers.UserMapper;
import mappers.UsersFriendsMapper;
import org.apache.http.HttpException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Михаил on 15.09.2015.
 */

public class VVProject {

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

        User user = Api.getUserInfo();
        InsertToDataBase IDB = new InsertToDataBase();
        switch (Integer.parseInt(args[2])){
            case 1:{
                FriendList friends = new FriendList();
                friends.setFriends(Api.getListFriend(user.getVkId()));

                IDB.InsertToUsersTable(user);
                IDB.InsertToFriendsTable(friends.getFriends());               // Работает
                IDB.InsertToUsersFriendsTable(user, friends.getFriends());    // Работает
                List<FriendsAudio> friendsAudioList = new ArrayList<FriendsAudio>();
                for (Friend friend : friends.getFriends()){
                    SongList songs = new SongList();
                    List<Song> songList = Api.getListSong(friend.getVkId());
                    songs.setSongs(songList);
                    IDB.InsertToArtistsTable(songs.getSongs());                // Работает
                    IDB.InsertToAudioTable(songs.getSongs());
                    friendsAudioList.add(new FriendsAudio(friend.getVkId(), songs.getSongs()));
                }
                IDB.InsertToFriendsAudioTable(friendsAudioList);
                break;
            }
            case 2:{
                String VkIDUser = user.getVkId();
                UserMapper userMapper = new UserMapper();
                int IDUser = userMapper.FindByVkId(VkIDUser).getID();

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
                List<FriendsAudio> newFriendsAudioList = new ArrayList<FriendsAudio>();
                for (Friend friend : newListFriends.getFriends()){
                    SongList songs = new SongList();
                    List<Song> songList = Api.getListSong(friend.getVkId());
                    songs.setSongs(songList);
                    newFriendsAudioList.add(new FriendsAudio(friend.getVkId(), songs.getSongs()));
                }

                /*Проверка на то что у нашего чувака не появилось новых друзей*/
                int i = 0;
                List<Friend> addedFriend = new ArrayList<Friend>();
                for (Friend newFriend : newListFriends.getFriends()){
                    String newVkIDFriend = newFriend.getVkId();
                    for (Friend oldFriend : oldListFriend){
                        String oldVkIDFriend = oldFriend.getVkId();
                        if (!oldVkIDFriend.equals(newVkIDFriend)){
                            i++;
                        }
                    }
                    if (i == oldListFriend.size()) {
                        addedFriend.add(newFriend);
                    }
                    i = 0;

                }

                /*Если есть кого добавить в БД, то добавляем*/
                if (addedFriend.size() != 0){
                    IDB.InsertToFriendsTable(addedFriend);
                }
                break;
            }
        }






        System.out.println(user.getFirstName() + " " + user.getLastName() + " id = " + user.getVkId());
    }
}
