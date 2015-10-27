package mappers;

import entity.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Михаил on 24.10.2015.
 */

/* ГОТОВО */

public class AudioMapper implements IEntityMapperBase<Song> {

    @Override
    public void Update(Song song) {

    }
    @Override
    public void Insert(Song song) {
        // SQL
        //INSERT INTO Song (ID_Artist, title) VALUES (song.getTitle(), song.getIDArtist());
        String SQL = "";
        try {
            this.dbHandler.openConnection();
            Statement statement = this.dbHandler.getConnection().createStatement();
            SQL = "INSERT INTO Song (ID_Artist, title) VALUES ("
                    + "(SELECT id FROM Artists WHERE name = '" + song.getArtistName() + "'), \'"
                    + song.getTitle() + "\')";
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
    public void Delete(Song song) {

    }

    private List<Song> FindBy(String SQL) throws SQLException {
        List<Song> songList = new ArrayList<Song>();
        try {
            this.dbHandler.openConnection();
            Statement statement = this.dbHandler.getConnection().createStatement();
            ResultSet result = statement.executeQuery(SQL);
            while (result.next()) {
                songList.add(new Song(result.getInt("id"), result.getString("name"), result.getString("title")));
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
        return songList;
    }
    @Override
    public Song FindById(int id) throws SQLException {
        String SQL = "SELECT song.id, artist_name.name, song.title "
                + "FROM song, "
                + "(SELECT name"
                + " FROM artists"
                + " WHERE artists.id = (SELECT a1.id_artist "
                + "FROM song a1 "
                + "WHERE a1.id = " + id + ")) artist_name "
                + "WHERE song.id = " + id;
        Song song = FindBy(SQL).get(0);
        return song;
    }

    public List<Song> FindByIDArtist(int idArtist) throws SQLException {
        String SQL = "SELECT id, id_artist as name, title  FROM Song WHERE id_artist = " + idArtist;
        List<Song> songList = FindBy(SQL);
        return songList;
    }

    public Song FindByTitle(String title) throws SQLException {
        String SQL = "SELECT id, id_artist as name, title  FROM Song WHERE title = '" + title + "'";
        Song song = FindBy(SQL).get(0);
        return song;
    }
}

