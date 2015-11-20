package mappers;
import entity.Friend;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Михаил on 23.10.2015.
 */

/* ГОТОВ */

public class FriendMapper implements IVkEntityMapperBase<Friend> {
    @Override
    public void Update(Friend friend) {

    }
    @Override
    public void Insert(Friend friend) {
        String SQL = "";
        try {
            this.dbHandler.openConnection();
            Statement statement = this.dbHandler.getConnection().createStatement();
            SQL = "INSERT INTO Friends (VkID, birthday, sex) VALUES (\""
                    + friend.getVkId() + "\", \"" + friend.getBirthday() + "\", " + friend.getSex() + ")";
                   //+ "\", (SELECT id FROM Sex WHERE sex= " + friend.getSex() + "))";
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
    public void Delete(Friend friend) {

    }

    private Friend FindBy(String SQL) throws SQLException {
        Friend friend = null;
        try {
            this.dbHandler.openConnection();
            Statement statement = this.dbHandler.getConnection().createStatement();
            ResultSet result = statement.executeQuery(SQL);
            if (result.next()) {
                friend = new Friend(result.getInt("id"), result.getString("vkid"), result.getString("birthday"), result.getString("sex"));
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
        return friend;
    }

    @Override
    public Friend FindById(int id) throws SQLException {
        String SQL = "SELECT * FROM Friends WHERE id = " + id;
        Friend friend = FindBy(SQL);
        return friend;
    }
    @Override
    public Friend FindByVkId(String VkID) throws SQLException {
        String SQL = "SELECT * FROM Friends WHERE vkid = '" + VkID + "'";
        Friend friend = FindBy(SQL);
        return friend;
    }
}
