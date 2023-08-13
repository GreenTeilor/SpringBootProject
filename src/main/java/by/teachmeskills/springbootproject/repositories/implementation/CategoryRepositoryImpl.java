package by.teachmeskills.springbootproject.repositories.implementation;

import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.repositories.CategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {
    private final static String GET_CATEGORIES_QUERY = "select c from Category c";

    @PersistenceContext
    private final EntityManager manager;

    @Override
    public Category create(Category category) {
        Session session = manager.unwrap(Session.class);
        session.persist(category);
        return category;
    }

    @Override
    public List<Category> read() {
        Session session = manager.unwrap(Session.class);
        return session.createQuery(GET_CATEGORIES_QUERY, Category.class).getResultList();
    }

    @Override
    public Category update(Category category) {
        Session session = manager.unwrap(Session.class);
        return session.merge(category);
    }

    @Override
    public void delete(int id) {
        Session session = manager.unwrap(Session.class);
        Category category = session.get(Category.class, id);
        session.remove(category);
    }
}
