
import java.sql.*;

public class DBHandler {
    private Connection connection = null;

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public void openConnection(){

        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/vkproject", "root", "");
        }
        catch (SQLException ex){
            System.out.println("SQLException caught");
            System.out.println("---");
            while ( ex != null ){
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

    public void InsertToManTable(String idUser) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO man (id_man, vkIDUser) VALUES (NULL," + idUser + ")");
    }

}
