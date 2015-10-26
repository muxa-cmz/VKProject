package mappers;

import entity.Audio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Михаил on 24.10.2015.
 */

/* ГОТОВО */

public class AudioMapper implements IEntityMapperBase<Audio> {

    @Override
    public void Update(Audio audio) {

    }
    @Override
    public void Insert(Audio audio) {
        // SQL
        //INSERT INTO Audio (ID_Artist, title) VALUES (audio.getTitle(), audio.getIDArtist());
        String SQL = "";
        try {
            this.dbHandler.openConnection();
            Statement statement = this.dbHandler.getConnection().createStatement();
            SQL = "INSERT INTO Audio (ID_Artist, title) VALUES ("
                    + "(SELECT id FROM Artists WHERE name = '" + audio.getArtistName() + "'), \'"
                    + audio.getTitle() + "\')";
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
    public void Delete(Audio audio) {

    }

    private List<Audio> FindBy(String SQL) throws SQLException {
        List<Audio> audioList = new ArrayList<Audio>();
        try {
            this.dbHandler.openConnection();
            Statement statement = this.dbHandler.getConnection().createStatement();
            ResultSet result = statement.executeQuery(SQL);
            while (result.next()) {
                audioList.add(new Audio(result.getInt("id"), result.getString("name"), result.getString("title")));
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
        return audioList;
    }
    @Override
    public Audio FindById(int id) throws SQLException {
        String SQL = "SELECT audio.id, artist_name.name, audio.title "
                + "FROM audio, "
                + "(SELECT name"
                + " FROM artists"
                + " WHERE artists.id = (SELECT a1.id_artist "
                + "FROM audio a1 "
                + "WHERE a1.id = " + id + ")) artist_name "
                + "WHERE audio.id = " + id;
        Audio audio = FindBy(SQL).get(0);
        return audio;
    }

    public List<Audio> FindByIDArtist(int idArtist) throws SQLException {
        String SQL = "SELECT id, id_artist as name, title  FROM Audio WHERE id_artist = " + idArtist;
        List<Audio> audioList = FindBy(SQL);
        return audioList;
    }

    public Audio FindByTitle(String title) throws SQLException {
        String SQL = "SELECT id, id_artist as name, title  FROM Audio WHERE title = '" + title + "'";
        Audio audio = FindBy(SQL).get(0);
        return audio;
    }
}

