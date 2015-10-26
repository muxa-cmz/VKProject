package api.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.*;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.Converter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valer on 26.10.2015.
 */
public class NetworkApi {
    private static final String URL = "https://api.vk.com";
    private static VKApiInterface service;

    public static VKApiInterface getNetworkApi() {
        if (service == null) {
            Gson gson = new GsonBuilder()
//                    .registerTypeAdapter(GroupResponse.class, new GroupDeserializer())
//                    .registerTypeAdapter(PostResponse.class, new PostDeserializer())
//                    .registerTypeAdapter(Post.class, new OnePostDeserializer())
                    .registerTypeAdapter(User.class, new UserDeserializer())
                    .registerTypeHierarchyAdapter(FriendList.class, new FriendsListDeserializer())
                    .registerTypeHierarchyAdapter(SongList.class, new SongListDeserializer())
                    .create();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            service = retrofit.create(VKApiInterface.class);
        }
        return service;
    }
}
