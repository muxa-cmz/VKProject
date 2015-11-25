package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBHandler<T> {
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
            Properties properties=new Properties();
            properties.setProperty("user","root");
            properties.setProperty("password","");
            properties.setProperty("useUnicode","true");
            properties.setProperty("characterEncoding","utf8");
            //connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/vkproject", "root", "");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/vkproject_temp", properties);
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
}
