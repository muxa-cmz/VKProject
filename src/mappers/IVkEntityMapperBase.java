package mappers;

import project.DBHandler;

import java.sql.SQLException;

/**
 * Created by Михаил on 21.10.2015.
 */
public interface IVkEntityMapperBase<T> {
    DBHandler dbHandler = new DBHandler();
    void Update(T object);
    void Insert(T object);
    void Delete(T object);

    T FindById(int id) throws SQLException;
    T FindByVkId(String VkID) throws SQLException;
}
