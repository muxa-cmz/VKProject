package mappers;

import entity.FriendsAudio;

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
    public void Insert(FriendsAudio friendsAudio) {
        // SQL
        //INSERT INTO FriendsAudio (ID_Friend, IDAudio) VALUES (friendsAudio.getVkIDFriend(), friendsAudio.getIDAudio());
        String SQL = "";
        try {
            this.dbHandler.openConnection();
            Statement statement = this.dbHandler.getConnection().createStatement();
            SQL = "INSERT INTO FriendsAudio (ID_Friend, ID_Audio) VALUES (\""
                    + friendsAudio.getIDFriend() + "\", \"" + friendsAudio.getIDAudio() + "\")";
            statement.execute(SQL);
        }catch (SQLException ex){
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
    }
    @Override
    public void Delete(FriendsAudio friendsAudio) {

    }

    private List<FriendsAudio> FindBy(String SQL) throws SQLException {
        List<FriendsAudio> friendsAudio = new ArrayList<FriendsAudio>();
        try {
            this.dbHandler.openConnection();
            Statement statement = this.dbHandler.getConnection().createStatement();
            ResultSet result = statement.executeQuery(SQL);
            if (result.next()) {
                friendsAudio.add(new FriendsAudio(result.getInt("id"), result.getInt("id_friend"), result.getInt("id_audio")));
            }
            else System.out.println("Записи с данными параметрами не существует");
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
        return friendsAudio;
    }
    @Override
    public FriendsAudio FindById(int id) throws SQLException {
        String SQL = "SELECT * FROM FriendsAudio WHERE id = " + id;
        FriendsAudio friendsAudio = FindBy(SQL).get(0);
        return friendsAudio;
    }

    public List<FriendsAudio> FindByIdFriend(int idFriend) throws SQLException {
        String SQL = "SELECT * FROM FriendsAudio WHERE id_friend = " + idFriend;
        List<FriendsAudio> friendsAudioList = FindBy(SQL);
        return friendsAudioList;
    }
}
