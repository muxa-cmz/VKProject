package mappers;

import entity.Change;
import entity.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Михаил on 24.10.2015.
 */
public class ChangeMapper implements IEntityMapperBase<Change> {
    @Override
   public void Update(Change change) {    }
    @Override
    public void Insert(List<Change> changes) {
        // SQL
        //INSERT INTO Changes (date_change, IDFriend, IDAudio, event) VALUES (change.getDate(), change.getIDFriend(), change.getIDAudio(), change.getChange());
        String SQL = "";
        try {
            this.dbHandler.openConnection();
            Statement statement = this.dbHandler.getConnection().createStatement();
            SongMapper songMapper = new SongMapper();
            for (Change change : changes) {
                int event = (change.getEvent()) ? 1 : 0;  // true/false перевод в int 1/0
                for (Song song : change.getIDAudio()){
                    int idAudio = songMapper.FindByTitle(song.getTitle()).getID();
                    SQL = "INSERT INTO Changes (date_change, id_friend, id_audio, event) VALUES ( DATE_FORMAT(CURRENT_DATE(), '%Y-%m-%d')"
                            + ", \"" + change.getIDFriend() + "\",\"" + idAudio + "\",\"" + event + "\")";
                    statement.executeUpdate(SQL);
                }
            }
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
    public void Delete(Change change) {

    }

    private List<Integer> FindBy(String SQL){
        List<Integer> songList = new ArrayList<Integer>();
        try {
            this.dbHandler.openConnection();
            Statement statement = this.dbHandler.getConnection().createStatement();
            ResultSet result = statement.executeQuery(SQL);
            while (result.next()) {
                songList.add(result.getInt("id_audio"));
//                changeList.add(new Change(result.getInt("id"), result.getString("date_change")
//                        , result.getInt("id_friend"), result.getInt("id_audio"), result.getBoolean("event")));
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
    public Change FindById(int id) {
//        String SQL = "SELECT * FROM Changes WHERE id = " + id;
//        Change change = FindBy(SQL).get(0);
        return null;
    }
//    public List<Change> FindByIdFriend(int idFriend) {
//        String SQL = "SELECT * FROM Changes WHERE id_Friend = " + idFriend;
//        List<Change> change = FindBy(SQL);
//        return change;
//    }

    public List<Integer> FindByEvent(int idFriend, boolean event) {
        int eventInt = event ? 1 : 0;
        String SQL = "SELECT * FROM Changes WHERE event = " + eventInt + " AND id_friend = " + idFriend;
        List<Integer> change = FindBy(SQL);
        return change;
    }

}

