package by.teachmeskills.springbootproject.repositories.implementation;

import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.repositories.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final static String GET_ALL_ORDERS_QUERY = "select o from Order o";

    @PersistenceContext
    private final EntityManager manager;

    @Override
    public Order create(Order order) {
        Session session = manager.unwrap(Session.class);
        session.persist(order);
        return order;
    }

    @Override
    public List<Order> read() {
        Session session = manager.unwrap(Session.class);
        return session.createQuery(GET_ALL_ORDERS_QUERY, Order.class).getResultList();
    }

    @Override
    public Order update(Order order) {
        Session session = manager.unwrap(Session.class);
        return session.merge(order);
    }

    @Override
    public void delete(int id) {
        Session session = manager.unwrap(Session.class);
        Order order = session.get(Order.class, id);
        session.remove(order);
    }
}
