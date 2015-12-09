package project;

import InternetToDataBase.InsertToDataBase;
import api.Api;
import api.IApi;
import entity.*;
import mappers.*;
import org.apache.commons.lang.time.StopWatch;
import org.apache.http.HttpException;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Михаил on 15.09.2015.
 */

public class VVProject {

    public static void createUser(User user, IApi Api) throws IOException, SQLException {
        StopWatch stopWatch = new StopWatch();
        FileWriter writer = new FileWriter("C:\\Users\\Михаил\\Desktop\\log_vk.txt", true);
        String text = "";
        InsertToDataBase IDB = new InsertToDataBase();
        FriendList friends = new FriendList();
        stopWatch.start();
        friends.setFriends(Api.getListFriend(user.getVkId()));
        stopWatch.stop();
        text = "---VkApi: getListFriend = \t" + stopWatch + "\r\n";
        writer.write(text);
        writer.flush();

        stopWatch.reset();
        stopWatch.start();
        IDB.InsertToUsersTable(user);
        stopWatch.stop();
        text = "---InsertToUsersTable = \t" + stopWatch + "\r\n";
        writer.write(text);
        writer.flush();

        stopWatch.reset();
        stopWatch.start();
        IDB.InsertToFriendsTable(friends.getFriends());               // Работает
        stopWatch.stop();
        text = "---InsertToFriendsTable = \t" + stopWatch + "\r\n";
        writer.write(text);
        writer.flush();

        stopWatch.reset();
        stopWatch.start();
        IDB.InsertToUsersFriendsTable(user, friends.getFriends());    // Работает
        stopWatch.stop();
        text = "---InsertToUsersFriendsTable = \t" + stopWatch + "\r\n";
        writer.write(text);
        writer.flush();

        List<FriendsAudio> friendsAudioList = new ArrayList<FriendsAudio>();
        int iteratorGetListSong = 0;
        int iteratorFriendsAudioTable = 0;
        for (Friend friend : friends.getFriends()){
            SongList songs = new SongList();
            stopWatch.reset();
            stopWatch.start();
            List<Song> songList = Api.getListSong(friend.getVkId());
            stopWatch.stop();
            text = "---VkApi:getListSong = \t\t" + stopWatch + "(" + ++iteratorGetListSong + " friend)\r\n";
            writer.write(text);
            writer.flush();

            songs.setSongs(songList);
            stopWatch.reset();
            stopWatch.start();
            IDB.InsertToArtistsTable(songs.getSongs());                // Работает
            stopWatch.stop();
            text = "---InsertToArtistsTable = \t" + stopWatch + "(" + songs.getSongs().size() + ")\r\n";
            writer.write(text);
            writer.flush();

            stopWatch.reset();
            stopWatch.start();
            IDB.InsertToAudioTable(songs.getSongs());
            stopWatch.stop();
            text = "---InsertToAudioTable = \t" + stopWatch + "(" + songs.getSongs().size() + ")\r\n\r\n\r\n";
            writer.write(text);
            writer.flush();

            friendsAudioList.add(new FriendsAudio(friend.getVkId(), songs.getSongs()));
        }
        stopWatch.reset();
        stopWatch.start();
        IDB.InsertToFriendsAudioTable(friendsAudioList);
        stopWatch.stop();
        text = "---InsertToFriendsAudioTable = " + stopWatch + "(" + ++iteratorFriendsAudioTable + ")\r\n";
        writer.write(text);
        writer.flush();

    }

    public static void update(User user, IApi Api) throws SQLException {
        UpdateHandler updateHandler = new UpdateHandler(Api);

        /* Получение информации о пользователе*/
        String VkIDUser = user.getVkId();
        UserMapper userMapper = new UserMapper();
        int IDUser = userMapper.FindByVkId(VkIDUser).getID();
        /*************************/
        updateHandler.getListOfOldFriends(IDUser);
        updateHandler.getListOfNewFriends(VkIDUser);
        updateHandler.checkForChangesFriendsList_addition(user);
        updateHandler.checkForChangesFriendsList_removal();
        updateHandler.checkForChangesListSongsOfOldFriends();
        updateHandler.insertChangeTable();
    }

    public static void getTop() throws SQLException {
        List<String> artists = new ArrayList<String>();
        DBHandler dbHandler = new DBHandler();
        dbHandler.openConnection();

        // запрос к базе
        String selectTableSQL = "SELECT ID, (SELECT NAME FROM ARTISTS WHERE ID = AUDIO.ID_ARTIST) ARTIST FROM AUDIO";
        Connection dbConnection;
        Statement statement;

        try {
            dbConnection = dbHandler.getConnection();
            statement = dbConnection.createStatement();

            // выбираем данные с БД
            ResultSet rs = statement.executeQuery(selectTableSQL);

            while (rs.next()) {
                String artist = rs.getString("ARTIST");
                //String title = rs.getString("TITLE");
                artists.add(artist);
            }
            dbConnection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        dbHandler.closeConnection();
        // здесь получили список всех артистов базы - artists

        // получаем топ исполнителей
        List<TopPosition> top = new ArrayList<TopPosition>();
        top.addAll(TopManager.getTop(artists));

        // записываем топ в базу (первые 100 позиций)
        dbHandler.openConnection();
        try {
            dbConnection = dbHandler.getConnection();
            statement = dbConnection.createStatement();
            for (int i = 0; i < 100; i++) {
                TopPosition topPosition = top.get(i);
                String insertTableSQL = "INSERT INTO TOPARTIST (NAME, COUNT) VALUES ('"+ topPosition.getTitle() +"'," + topPosition.getCount() + ")";
                statement.executeUpdate(insertTableSQL);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        dbHandler.closeConnection();
    }

    public static void main(String args[]) throws URISyntaxException, IOException, HttpException, ParseException, SQLException, java.text.ParseException {

        IApi Api = new Api();
        User user = Api.getUserInfo();
        switch (Integer.parseInt(args[2])){
            case 1:{
                createUser(user, Api);
                break;
            }
            case 2:{
                update(user, Api);
                break;
            }
            // записать в таблицу top artist топ 100 исполнителей среди всех данных базы
            case 3: {
                getTop();

                break;
            }
        }






        System.out.println(user.getFirstName() + " " + user.getLastName() + " id = " + user.getVkId());
    }

}
