package api.net;

import com.google.gson.*;
import entity.Song;
import entity.SongList;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valer on 26.10.2015.
 */
public class SongListDeserializer implements JsonDeserializer<SongList>{

    @Override
    public SongList deserialize(JsonElement je, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        SongList songList = new SongList();

        JsonObject response = (JsonObject) je.getAsJsonObject().get("response");
        JsonArray items = response.getAsJsonObject().getAsJsonArray("items");

        for (int i = 0; i < items.size(); i++) {
            Song song = context.deserialize(items.get(i), Song.class);
            songList.getSongs().add(song);
        }
        return songList;
    }
}
