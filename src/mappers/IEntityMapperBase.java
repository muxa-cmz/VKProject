package mappers;

import project.DBHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Михаил on 21.10.2015.
 */
public interface IEntityMapperBase<T>{
    public DBHandler dbHandler = new DBHandler();
    void Update(T object);
    void Insert(List<T> object) throws SQLException;
    void Delete(T object);

    T FindById(int id) throws SQLException;
}






