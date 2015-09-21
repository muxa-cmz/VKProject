import org.json.simple.parser.ParseException;

import java.util.*;

//import org.apache.http.HttpResponse;



public class MyHTTPClient {

    private httpHandler httpObj = new httpHandler();
    private JSONHandler jsonHandlerObj = new JSONHandler();
    private StatisticsHandler statisticsHandlerObj = new StatisticsHandler();

    private String getNickname(String urlToRead){
        String result = "";
        StringBuffer id = new StringBuffer("");
        for (int i = urlToRead.length(); i != 0; i--) {
            if (urlToRead.charAt(i-1) != '/') {
                id.append(urlToRead.charAt(i-1));
            }
            else break;
        }
        result = id.toString();
        return result = new StringBuffer(result).reverse().toString();
    }

    private String getID(String urlToRead) throws ParseException {
        String id = "";
        String html = httpObj.getHTML("getProfiles" + "?", "uids=" + getNickname(urlToRead));
        String json = html;
        id = jsonHandlerObj.getIDOfJSON(json);
        return id;
    }

    private List<String> getListFriends(String id) throws ParseException {
        List<String> ListFriends = new ArrayList<String>();
        String html = httpObj.getHTML("friends.get" + "?", "user_id=" + id);
        String json = html;
        ListFriends = jsonHandlerObj.getListFriendsOfJSON(json);
        return ListFriends;
    }

    private List<String> getListAudioUsers(List<String> ListFriends) throws ParseException {
        List<String> ListAudioAllUser = new ArrayList<String>();
        String token = "786e9fadff70bee5e3c2e439ade63f88e77f3956be7ddba5c41b5050f36a77ae1dbf0d0f6de1808a8ff71";
        for (int i = 0; i < ListFriends.size(); i++) {
            String html = httpObj.getHTML("audio.get" + "?", "user_id=" + ListFriends.get(i) + "&v=5.37&access_token=" + token);
            String json = html;
            ListAudioAllUser.addAll(ParseAudioUser(html));
        }
        return ListAudioAllUser;
    }

    private List<String> ParseAudioUser(String html) throws ParseException {
        List<String> ListAudioUser = new ArrayList<String>();
        String json = html;
        ListAudioUser = jsonHandlerObj.getListAudioUserOfJSON(json);
        return ListAudioUser;
    }

    public void TopArtist(String urlToRead, int number) throws ParseException {
        statisticsHandlerObj.Top(getListAudioUsers(getListFriends(getID(urlToRead))), number);

    }
    // получение токена
    // https://oauth.vk.com/access_token?client_id=5072633&client_secret=PGtS9I7T1srL0ft27L2j&grant_type=client_credentials

    //        String html = "{\"response\":{\"count\":3,\"items\":[{\"id\":397071087,\"owner_id\":78543368,\"artist\":" +
//                "\"Океан Ельзи\",\"title\":\"Не твоя в йна\",\"duration\":268,\"date\":1441825092,\"url\":" +
//                "\"https:\\/\\/psv4.vk.me\\/c611931\\/u12520548\\/audios\\" +
//                "/6aa979e4af63.mp3?extra=QLcWZJhN9Zb4nCHgB6iaBj8b7OhEh683Qe4kzLKF3JjC7YU0g7px08l5KXJA7KCd5DR4eOMENIjj_t_7kABWfskVz-YD4g\"," +
//                "\"lyrics_id\":266312519,\"genre_id\":18},{\"id\":318647731,\"owner_id\":78543368,\"artist\":\"The PanHeads Band\",\"title\":" +
//                "\"Не будите меня (Skillet Cover)\",\"duration\":238,\"date\":1414447117,\"url\":\"https:\\/\\/cs1-50v4.vk-cdn.net\\/" +
//                "p3\\/c323699b7222dd.mp3?extra=n6yDyQyePemQXwqJbAzc69CawUqscZ8vKXqN_f_jJgnvTVHUadka5vP16jesTGTnTp6TUYWe1QOahW3iWEZ2RccPSD6-pw\"," +
//                "\"lyrics_id\":174327321,\"genre_id\":1},{\"id\":120592159,\"owner_id\":78543368,\"artist\":\"Неизвестный исполнитель\",\"title\":" +
//                "\"Дорожка 5\",\"duration\":228,\"date\":1316623222,\"url\":\"https:\\/\\/psv4.vk.me\\/c5810\\/u78543368\\/audios\\/0a4cf9569234.mp3" +
//                "?extra=fSE6vTx3CwcyFxvVASh1L8zItJYxRWYauKp56BZCfbI-35L3q1bIdkLYJYHnhXSsfRYjC9KuPCGDieAqENRkLOZWFatPwg\",\"genre_id\":18}]}}";

//        String html = "{\"error\":{\"error_code\":15,\"error_msg\":\"Access denied: user deactivated\"," +
//                "\"request_params\":[{\"key\":\"oauth\",\"value\":\"1\"},{\"key\":\"method\",\"value\":\"audio.get\"}" +
//                ",{\"key\":\"user_id\",\"value\":\"785433689v=5.37\"}]}}";

}
