package mappers;

import entity.UsersFriends;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Михаил on 24.10.2015.
 */

public class UsersFriendsMapper implements IEntityMapperBase<UsersFriends> {
    @Override
    public void Update(UsersFriends usersFriends) {

    }
    @Override
    public void Insert(UsersFriends usersFriends) {
        // SQL
        //INSERT INTO UsersFriends (VkID_User, VkID_Friend) VALUES (usersFriends.getVkIDUser(), usersFriends.getVkIDFriend());
        String SQL = "";
        try {
            this.dbHandler.openConnection();
            Statement statement = this.dbHandler.getConnection().createStatement();
            SQL = "INSERT INTO UsersFriends (VkID_User, VkID_Friend) VALUES (\"" + usersFriends.getVkIDUser()
                    + "\",\"" + usersFriends.getVkIDFriend() + "\")";
            statement.executeUpdate(SQL);
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
    public void Delete(UsersFriends usersFriends) {

    }

    private List<UsersFriends> FindBy(String SQL){
        List<UsersFriends> usersFriendsList = new ArrayList<UsersFriends>();
        try {
            this.dbHandler.openConnection();
            Statement statement = this.dbHandler.getConnection().createStatement();
            ResultSet result = statement.executeQuery(SQL);
            while (result.next()) {
                usersFriendsList.add(new UsersFriends(result.getInt("id"), result.getInt("id_user"), result.getInt("id_friend")));
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
        return usersFriendsList;
    }
    @Override
    public UsersFriends FindById(int id) {
        String SQL = "SELECT * FROM UsersFriends WHERE id = " + id;
        UsersFriends artist = FindBy(SQL).get(0);
        return artist;
    }

    public List<UsersFriends> FindByIdUser(int idUser) {
        String SQL = "SELECT * FROM UsersFriends WHERE id_user = " + idUser;
        List<UsersFriends> usersFriendsList = FindBy(SQL);
        return usersFriendsList;
    }

    public List<UsersFriends> FindByIdFriend(int idFriend) {
        String SQL = "SELECT * FROM UsersFriends WHERE id_friend = " + idFriend;
        List<UsersFriends> usersFriendsList = FindBy(SQL);
        return usersFriendsList;
    }

}
