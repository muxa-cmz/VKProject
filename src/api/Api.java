package api;

import api.net.NetworkApi;
import entity.*;
import retrofit.Call;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valer on 26.10.2015.
 */
public class Api implements IApi{
    private static final String VK_API_VERSION = "5.37";
    private static final int AUDIO_COUNT = 1000;

    public String getVkToken() {
        String VALERA_TOKEN = "a53f98d05a4920a81d38256b0def5e20eb1fd04a02dc40c39be36b4632d4c3fe6a804794e510c92fb46e5";
        String MISHA_TOKEN = "f1cf73c377a83e9451f6e3e300db3b22b48d76871aab7d78fa64124f6c8ead942b4f1890e4d797f8f021f";
        String VERA_TOKEN = "e88932500d5d4762544c8cc7761061f6f8e14a417af119a4fa604a6176c3dab48e477e7fc9b6b60ad4f60";
        return VERA_TOKEN;
    }

    public User getUserInfo() {
        Call<User> call = NetworkApi.getNetworkApi().getUserInfo(VK_API_VERSION, getVkToken());
        User user = null;

        try {
           user = call.execute().body();
        } catch (Exception e) {
            System.out.println("error:" + e);
        }
        return user;
    }

    public List<Friend> getListFriend(String userVkId) {
        Call<FriendList> call = NetworkApi.getNetworkApi().getFriends(VK_API_VERSION, "sex, bdate", userVkId, getVkToken());
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

    public List<Song> getListSong(String ownerId) {
        Call<SongList> call = NetworkApi.getNetworkApi().getSongs(ownerId, VK_API_VERSION, AUDIO_COUNT, getVkToken());
        SongList songList;
        List<Song> songs = new ArrayList<Song>();
        try {
            songList = call.execute().body();
            songs.addAll(songList.getSongs());
        } catch (Exception e) {
            System.out.println("getListSong  error:" + e);
        }
        return songs;
    }



}
