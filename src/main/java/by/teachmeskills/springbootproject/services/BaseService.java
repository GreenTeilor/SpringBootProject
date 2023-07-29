package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.BaseEntity;
import by.teachmeskills.springbootproject.exceptions.UnableToExecuteQueryException;
import by.teachmeskills.springbootproject.exceptions.UserAlreadyExistsException;
import org.springframework.web.servlet.ModelAndView;

public interface BaseService<T extends BaseEntity> {
    ModelAndView create(T entity) throws UnableToExecuteQueryException, UserAlreadyExistsException;

    ModelAndView read() throws UnableToExecuteQueryException;

    T update(T entity) throws UnableToExecuteQueryException;

    void delete(int id) throws UnableToExecuteQueryException;
}
