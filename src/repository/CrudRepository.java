package repository;
import java.util.List;

interface CrudRepository<T, ID> {
    void create(T entity);
    T read(ID id);
    List<T> readAll();
    void update(ID id, T entity);
    void delete(ID id);
}