package project;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Михаил on 24.10.2015.
 */
class httpHandler {

    public String getHTML(String apiFunction, String param) {
        //StringBuffer urlSearch = new StringBuffer("https://api.vkontakte.ru/method/getProfiles?uids=");
        StringBuffer urlSearch = new StringBuffer("https://api.vkontakte.ru/method/");
        String urlToRead = urlSearch.append(apiFunction).append(param).toString();
        //urlToRead = urlSearch.append(getNickname(urlToRead)).toString();
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URL(urlToRead);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}