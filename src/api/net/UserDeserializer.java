package api.net;

import com.google.gson.*;
import entity.User;

import java.lang.reflect.Type;

/**
 * Created by valer on 26.10.2015.
 */
public class UserDeserializer implements JsonDeserializer<User> {
    @Override
    public User deserialize(JsonElement je, Type type, JsonDeserializationContext context) throws JsonParseException {
        User user = new User();
        JsonArray array = je.getAsJsonObject().getAsJsonArray("response");
        JsonObject item = array.get(0).getAsJsonObject();

        user.setVkId(item.get("id").getAsString());
        user.setFirstName(item.get("first_name").getAsString());
        user.setLastName(item.get("last_name").getAsString());
        //user.setPhotoUrl(item.get("photo_200").getAsString());

        return user;
    }
}
