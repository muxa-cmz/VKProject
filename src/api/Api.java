package api;

import api.net.NetworkApi;
import entity.*;
import retrofit.Call;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valer on 26.10.2015.
 */
public class Api {
    private static final String VK_API_VERSION = "5.37";
    private static final int AUDIO_COUNT = 1000;

    public static String getVkToken() {
        String VALERA_TOKEN = "a53f98d05a4920a81d38256b0def5e20eb1fd04a02dc40c39be36b4632d4c3fe6a804794e510c92fb46e5";
        String MISHA_TOKEN = "f1cf73c377a83e9451f6e3e300db3b22b48d76871aab7d78fa64124f6c8ead942b4f1890e4d797f8f021f";
        return MISHA_TOKEN;
    }

    public static User getUserInfo() {
        Call<User> call = NetworkApi.getNetworkApi().getUserInfo(VK_API_VERSION, getVkToken());
        User user = null;

        try {
           user = call.execute().body();
        } catch (Exception e) {
            System.out.println("error:" + e);
        }
        return user;
    }

    public static List<Friend> getListFriend(String userId) {
        Call<FriendList> call = NetworkApi.getNetworkApi().getFriends(VK_API_VERSION, "sex, bdate", userId, getVkToken());
        FriendList friendList;
        List<Friend> friends = new ArrayList<Friend>();

        try {
            friendList = call.execute().body();
            friends.addAll(friendList.getFriends());
        } catch (Exception e) {
            System.out.println("error:" + e);
        }
        return friends;
    }

    public static List<Song> getListSong(String ownerId) {
        Call<SongList> call = NetworkApi.getNetworkApi().getSongs(ownerId, VK_API_VERSION, AUDIO_COUNT, getVkToken());
        SongList songList;
        List<Song> songs = new ArrayList<Song>();

        try {
            songList = call.execute().body();
            songs.addAll(songList.getSongs());
        } catch (Exception e) {
            System.out.println("error:" + e);
        }
        return songs;
    }



}
