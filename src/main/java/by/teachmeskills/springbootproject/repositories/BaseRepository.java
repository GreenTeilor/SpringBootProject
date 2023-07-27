package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.BaseEntity;
import by.teachmeskills.springbootproject.exceptions.BadConnectionException;
import by.teachmeskills.springbootproject.exceptions.UserAlreadyExistsException;

import java.util.List;

public interface BaseRepository<T extends BaseEntity> {
    ConnectionPool pool = ConnectionPool.getInstance();

    T create(T entity) throws BadConnectionException, UserAlreadyExistsException;

    List<T> read() throws BadConnectionException;

    T update(T entity) throws BadConnectionException;

    void delete(int id) throws BadConnectionException;
}
