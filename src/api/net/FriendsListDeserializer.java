package api.net;

import com.google.gson.*;
import entity.Friend;
import entity.FriendList;
import entity.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valer on 26.10.2015.
 */
public class FriendsListDeserializer implements JsonDeserializer<FriendList>{

    @Override
    public FriendList deserialize(JsonElement je, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        FriendList friendList = new FriendList();

        JsonObject response = (JsonObject) je.getAsJsonObject().get("response");
        JsonArray items = response.getAsJsonObject().getAsJsonArray("items");

        for (int i = 0; i < items.size(); i++) {
            Friend friend = context.deserialize(items.get(i), Friend.class);
            friendList.getFriends().add(friend);
        }
        return friendList;
    }
}
