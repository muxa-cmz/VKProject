package entity;

/**
 * Created by Михаил on 13.12.2015.
 */
public class Token extends Entity {

    private int idUser;
    private String token;

    public Token(int idUser, String token) {
        this.idUser = idUser;
        this.token = token;
    }

    public Token(int id, int idUser, String token) {
        this.setID(id);
        this.idUser = idUser;
        this.token = token;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
