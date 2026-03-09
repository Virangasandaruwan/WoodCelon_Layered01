package lk.ijse.woodceylon.dao;

import java.util.ArrayList;

public interface CrudDAO<T, ID> extends SuperDAO {

    ArrayList<T> getAll() throws Exception;
    boolean save(T entity) throws Exception;
    boolean update(T entity) throws Exception;
    boolean delete(ID id) throws Exception;
    boolean exist(ID id) throws Exception;
    T search(ID id) throws Exception;
}
