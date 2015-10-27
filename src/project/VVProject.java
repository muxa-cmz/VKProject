package project;

import api.Api;
import entity.Change;
import entity.Friend;
import entity.Song;
import entity.User;
import mappers.ChangeMapper;
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
        List<Friend> friends = new ArrayList<Friend>();
        friends.addAll(Api.getListFriend(user.getVkId()));

        List<Song> songs = new ArrayList<Song>();
        for (int i = 0; i < 5; i++) {
            songs.addAll(Api.getListSong(friends.get(i).getVkId()));
            songs.clear();
        }

        System.out.println(user.getFirstName() + " " + user.getLastName() + " id = " + user.getVkId());
    }
}
