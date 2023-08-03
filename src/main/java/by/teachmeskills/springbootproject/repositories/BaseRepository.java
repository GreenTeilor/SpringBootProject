package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.BaseEntity;
import by.teachmeskills.springbootproject.exceptions.EntityOperationException;
import by.teachmeskills.springbootproject.exceptions.UserAlreadyExistsException;

import java.util.List;

public interface BaseRepository<T extends BaseEntity> {
    ConnectionPool pool = ConnectionPool.getInstance();

    T create(T entity) throws EntityOperationException, UserAlreadyExistsException;

    List<T> read() throws EntityOperationException;

    T update(T entity) throws EntityOperationException;

    void delete(int id) throws EntityOperationException;
}
