package mappers;
import entity.Artist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Михаил on 24.10.2015.
 */

/* ГОТОВ */

public class ArtistMapper implements IEntityMapperBase<Artist> {
    @Override
    public void Update(Artist artist) {

    }
    @Override
    public void Insert(Artist artist) {
        String SQL = "";
        try {
            this.dbHandler.openConnection();
            Statement statement = this.dbHandler.getConnection().createStatement();
            SQL = "INSERT INTO Artists(name) VALUES (\'" + artist.getName() + "\')";
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
    public void Delete(Artist artist) {

    }

    private Artist FindBy(String SQL) throws SQLException {
        Artist artist = null;
        try {
            this.dbHandler.openConnection();
            Statement statement = this.dbHandler.getConnection().createStatement();
            ResultSet result = statement.executeQuery(SQL);
            if (result.next()) {
                artist = new Artist(result.getInt("id"), result.getString("name"));
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
        return artist;
    }

    @Override
    public Artist FindById(int id) throws SQLException {
        //SQL
        String SQL = "SELECT * FROM Artists WHERE id = " + id;
        Artist artist = FindBy(SQL);
        return artist;
    }
    public Artist FindByName(String name) throws SQLException {
        //SQL
        String SQL = "SELECT * FROM Artists WHERE name = '" + name + "'";
        Artist artist = FindBy(SQL);
        return artist;
    }
}
