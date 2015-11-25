package mappers;

import entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Михаил on 23.10.2015.
 */

/* ГОТОВ */

public class UserMapper implements IVkEntityMapperBase<User> {
    @Override
    public void Update(User user) {

    }
    @Override
    public void Insert(User user) {
        String SQL = "";
        try {
            this.dbHandler.openConnection();
            Statement statement = this.dbHandler.getConnection().createStatement();
            SQL = "INSERT INTO Users (VkID, first_name, last_name) VALUES (\"" + user.getVkId()
                    //+ "\",\"" + user.getFirstName() + "\",\"" + user.getLastName() + "\")"
                    + "\",\"" + user.getFirstName() + "\",\"" + user.getLastName() + "\")";

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
    public void Delete(User user) {

    }

    private User FindBy(String SQL) throws SQLException {
        User user = null;
        try {
            this.dbHandler.openConnection();
            Statement statement = this.dbHandler.getConnection().createStatement();
            ResultSet result = statement.executeQuery(SQL);
            if (result.next()) {
                user = new User(result.getInt("id"), result.getString("vkid"), result.getString("last_name"), result.getString("first_name"));
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
        return user;
    }

    @Override
    public User FindById(int id) throws SQLException {
        String SQL = "SELECT * FROM Users WHERE id = " + id;
        User user = FindBy(SQL);
        return user;
    }
    @Override
    public User FindByVkId(String VkID) throws SQLException {
        String SQL = "SELECT * FROM Users WHERE vkid = " + VkID;
        User user = FindBy(SQL);
        return user;
    }
}
