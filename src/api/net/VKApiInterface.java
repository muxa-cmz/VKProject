package api.net;

import entity.*;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

import java.util.List;

/**
 * Created by valer on 26.10.2015.
 */
public interface VKApiInterface {

    @GET("/method/users.get?")
    Call<User> getUserInfo(
            @Query("v") String version,
            @Query("access_token") String token
    );

    @GET("/method/friends.get?")
    Call<FriendList> getFriends(
            @Query("v") String version,
            @Query("fields") String fields,
            @Query("user_id") String user_id,
            @Query("access_token") String token
    );

    @GET("/method/audio.get?")
    Call<SongList> getSongs(
            @Query("owner_id") String owner_id,
            @Query("v") String version,
            @Query("count") int count,
            @Query("access_token") String token

    );
}
