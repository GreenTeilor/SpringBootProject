package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.BaseEntity;
import by.teachmeskills.springbootproject.exceptions.UnableToExecuteQueryException;
import by.teachmeskills.springbootproject.exceptions.UserAlreadyExistsException;

import java.util.List;

public interface BaseRepository<T extends BaseEntity> {
    ConnectionPool pool = ConnectionPool.getInstance();

    T create(T entity) throws UnableToExecuteQueryException, UserAlreadyExistsException;

    List<T> read() throws UnableToExecuteQueryException;

    T update(T entity) throws UnableToExecuteQueryException;

    void delete(int id) throws UnableToExecuteQueryException;
}
