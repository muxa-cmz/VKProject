package project;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Михаил on 20.09.2015.
 */
public class JSONHandler {

    private JSONParser parser = new JSONParser();
    private JSONArray getJSONArray(String json) throws ParseException {
        Object obj = parser.parse(json);
        JSONObject jsonObj = (JSONObject) obj;
        JSONArray jsonArray = (JSONArray) jsonObj.get("response");
        return jsonArray;
    }

    public String getIDOfJSON(String json) throws ParseException {
        JSONArray jsonArray = getJSONArray(json);
        JSONObject jsonObject = (JSONObject) jsonArray.get(0);
        String id = jsonObject.get("uid").toString();
        return id;
    }

    public List<String> getListFriendsOfJSON(String json) throws ParseException, SQLException {
        List<String> ListFriends = new ArrayList<String>();
        JSONArray jsonArray = getJSONArray(json);
        for (int i = 0; i < jsonArray.size(); i++){
            ListFriends.add(jsonArray.get(i).toString());
        }
        return ListFriends;
    }

    public List<String> getListAudioUserOfJSON(String json) throws ParseException {
        List<String> ListAudioUser = new ArrayList<String>();
        Object obj = parser.parse(json);
        JSONObject jsonObj = (JSONObject) obj;
        try {
            JSONObject jsonObjRes = (JSONObject) jsonObj.get("response");
            JSONArray jsonArray = (JSONArray) jsonObjRes.get("items");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonArtist = (JSONObject) jsonArray.get(i);
                ListAudioUser.add(jsonArtist.get("artist").toString().toLowerCase());
            }
        }
        catch (NullPointerException ex) {}
        return ListAudioUser;
    }
}
