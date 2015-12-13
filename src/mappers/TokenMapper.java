package mappers;

import entity.Token;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by Михаил on 13.12.2015.
 */
public class TokenMapper implements IEntityMapperBase<Token> {

    @Override
    public void Update(Token object) {

    }

    @Override
    public void Insert(List<Token> object) throws SQLException {

    }

    public void Insert(Token token) throws SQLException {
        String SQL = "";
        Statement statement = null;
        try {
            this.dbHandler.openConnection();
            statement = this.dbHandler.getConnection().createStatement();
        }catch (SQLException ex){
            System.out.println("Other Error in Main.");
        }catch (Exception ex) {
            System.out.println("Other Error in Main.");
        }


        try {
            PreparedStatement preparedStatement = this.dbHandler.getConnection().prepareStatement("INSERT INTO UsersTokens(id_user, token) VALUES (?,?)");
            preparedStatement.setInt(1, token.getIdUser());
            preparedStatement.setString(2, token.getToken());
            //SQL = "INSERT INTO UsersTokens(id_user, token) VALUES (" + token.getIdUser() + "\')";

            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("SQLException caught");
            System.out.println("---");
            while (ex != null) {
                System.out.println("Message   : " + ex.getMessage());
                System.out.println("SQLState  : " + ex.getSQLState());
                System.out.println("ErrorCode : " + ex.getErrorCode());
                System.out.println("---");
                ex = ex.getNextException();
            }
        } catch (Exception ex) {
            System.out.println("Other Error in Main.");
        }
        this.dbHandler.closeConnection();
    }

    @Override
    public void Delete(Token object) {

    }
    private Token FindBy(PreparedStatement preparedStatement) throws SQLException {
        Token token = null;
        try {
            //this.dbHandler.openConnection();
            //Statement statement = this.dbHandler.getConnection().createStatement();
            //ResultSet result = statement.executeQuery(SQL);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                token = new Token(result.getInt("id"), result.getInt("id_user"), result.getString("token"));
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
        return token;
    }

    @Override
    public Token FindById(int id) throws SQLException {
        return null;
    }

    public Token FindByIdUser(int idUser) throws SQLException {
        //SQL
        this.dbHandler.openConnection();
        PreparedStatement preparedStatement =
                this.dbHandler.getConnection()
                        .prepareStatement("SELECT * FROM UsersTokens WHERE id_user = ?");
        preparedStatement.setInt(1, idUser);
        Token token = FindBy(preparedStatement);
        this.dbHandler.closeConnection();
        return token;
    }

}
