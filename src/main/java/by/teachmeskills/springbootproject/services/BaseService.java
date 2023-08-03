package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.BaseEntity;
import by.teachmeskills.springbootproject.exceptions.EntityOperationException;
import by.teachmeskills.springbootproject.exceptions.UserAlreadyExistsException;
import org.springframework.web.servlet.ModelAndView;

public interface BaseService<T extends BaseEntity> {
    ModelAndView create(T entity) throws EntityOperationException, UserAlreadyExistsException;

    ModelAndView read() throws EntityOperationException;

    T update(T entity) throws EntityOperationException;

    void delete(int id) throws EntityOperationException;
}
