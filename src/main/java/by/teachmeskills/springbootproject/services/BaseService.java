package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.BaseEntity;
import by.teachmeskills.springbootproject.exceptions.BadConnectionException;
import org.springframework.web.servlet.ModelAndView;

public interface BaseService<T extends BaseEntity> {
    ModelAndView create(T entity);

    ModelAndView read();

    T update(T entity) throws BadConnectionException;

    void delete(int id) throws BadConnectionException;
}
