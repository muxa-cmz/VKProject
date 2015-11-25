package mappers;

import entity.FriendsAudio;
import entity.Song;
import entity.User;
import entity.UsersFriends;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Михаил on 24.10.2015.
 */
public class FriendsAudioMapper implements IEntityMapperBase<FriendsAudio> {
    @Override
    public void Update(FriendsAudio friendsAudio) {

    }
    @Override
    public void Insert(List<FriendsAudio> friendsAudios) throws SQLException {
        // SQL
        //INSERT INTO FriendsAudio (VkID_Friend, IDAudio) VALUES (friendsAudio.getVkIDFriend(), friendsAudio.getIDAudio());
        String SQL = "";
        Statement statement = null;
        try {
            this.dbHandler.openConnection();
            statement = this.dbHandler.getConnection().createStatement();
        } catch (SQLException ex) {
            System.out.println("Other Error in Main.");
        } catch (Exception ex) {
            System.out.println("Other Error in Main.");
        }

        for (FriendsAudio friendsAudio : friendsAudios) {
            String vkIdFriend = friendsAudio.getVkIDFriend();
            FriendMapper friendMapper = new FriendMapper();
            int idFriend = friendMapper.FindByVkId(vkIdFriend).getID();
            for (Song song : friendsAudio.getListAudio()) {
                try {
                    SQL = "INSERT INTO FriendsAudio (id_friend, id_audio) VALUES ("
                    + idFriend + ","
                    + "(SELECT id FROM Audio WHERE title = \'" + song.getTitle()
                    + "\' AND id_artist = (SELECT id FROM Artists WHERE name = \'" + song.getArtistName() + "\')))";
                    statement.executeUpdate(SQL);
                } catch (SQLException ex) {
                    System.out.println("SQLException caught");
                    System.out.println("---");
                    while (ex != null) {
                        System.out.println("Message   : " + ex.getMessage());
                        System.out.println("SQLState  : " + ex.getSQLState());
                        System.out.println("ErrorCode : " + ex.getErrorCode());
                        System.out.println("---");
                        ex = ex.getNextException();
                    }
                } catch (Exception ex) {
                    System.out.println("Other Error in Main.");
                }
            }
        }
    }
    @Override
    public void Delete(FriendsAudio friendsAudio) {

    }
    //@Override
    private List<Integer> FindBy(String SQL){
        List<Integer> listIDAudio = new ArrayList<Integer>();
        try {
            this.dbHandler.openConnection();
            Statement statement = this.dbHandler.getConnection().createStatement();
            ResultSet result = statement.executeQuery(SQL);
            while (result.next()) {
                listIDAudio.add(result.getInt("id_audio"));
            }
            //else System.out.println("Записи с данными параметрами не существует");
        } catch (SQLException ex){
            System.out.println("SQLException caught");
            System.out.println("---");
            while ( ex != null ) {
                System.out.println("Message   : " + ex.getMessage());
                System.out.println("SQLState  : " + ex.getSQLState());
                System.out.println("ErrorCode : " + ex.getErrorCode());
                System.out.println("---");
                ex = ex.getNextException();
            }
        }
        catch (Exception ex) {
            System.out.println("Other Error in Main.");
        }
        return listIDAudio;
    }

    public FriendsAudio FindById(int id) {
//        String SQL = "SELECT * FROM UsersFriends WHERE id = " + id;
//        UsersFriends artist = FindBy(SQL).get(0);
        return null;
    }

    public List<Integer> getIDAudioForFriend(int IDFriend){
        String SQL = "SELECT id_audio FROM friendsaudio WHERE id_friend = " + IDFriend;
        List<Integer> listIDAudio = new ArrayList<Integer>();
        listIDAudio.addAll(this.FindBy(SQL));
        return listIDAudio;
    }

    public List<FriendsAudio> getAudiosForFriends(User user) throws SQLException {
        UserMapper userMapper = new UserMapper();
        user = userMapper.FindByVkId(user.getVkId());
        UsersFriendsMapper usersFriendsMapper = new UsersFriendsMapper();
        List<UsersFriends> usersFriendsList= new ArrayList<UsersFriends>();
        usersFriendsList.addAll(usersFriendsMapper.FindByIdUser(user.getID()));
        List<FriendsAudio> friendsAudio = new ArrayList<FriendsAudio>();
        FriendMapper friendMapper = new FriendMapper();
        SongMapper songMapper = new SongMapper();
        for (UsersFriends usersFriends : usersFriendsList){
            int IDFriend = usersFriends.getIDFriend();
            List<Song> songs = new ArrayList<Song>();
            for (Integer IDAudio : this.getIDAudioForFriend(IDFriend)){
                songs.add(songMapper.FindById(IDAudio));
            }
            friendsAudio.add(new FriendsAudio(friendMapper.FindById(IDFriend).getVkId(), songs));
        }
        return friendsAudio;
    }
}
