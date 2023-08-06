package by.teachmeskills.springbootproject.repositories.implementation;

import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.UserAlreadyExistsException;
import by.teachmeskills.springbootproject.repositories.UserRepository;
import by.teachmeskills.springbootproject.utils.HashUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final static String GET_USER_BY_EMAIL = "select u from User u where u.email=:email";
    private final static String SEARCH_USER_QUERY = "select u from User u where u.email=:email and u.password=:password";
    private final static String UPDATE_ADDRESS_AND_PHONE_NUMBER_QUERY = "update User set address=:address, phoneNumber=:phoneNumber where email=:email";
    private final static String GET_ALL_USERS_QUERY = "select u from User u";

    @PersistenceContext
    private final EntityManager manager;

    @Override
    public User getUserByEmail(String email) {
        Session session = manager.unwrap(Session.class);
        Query<User> query = session.createQuery(GET_USER_BY_EMAIL, User.class);
        query.setParameter("email", email);
        return query.uniqueResult();
    }

    @Override
    public User getUserById(int id) {
        Session session = manager.unwrap(Session.class);
        return session.get(User.class, id);
    }

    @Override
    public User getUser(String email, String password) {
        Session session = manager.unwrap(Session.class);
        Query<User> query = session.createQuery(SEARCH_USER_QUERY, User.class);
        query.setParameter("email", email);
        query.setParameter("password", HashUtils.getHash(password));
        return query.uniqueResult();
    }

    @Override
    public void updateAddressAndPhoneNumber(String address, String phoneNumber, String email) {
        Session session = manager.unwrap(Session.class);
        MutationQuery query = session.createMutationQuery(UPDATE_ADDRESS_AND_PHONE_NUMBER_QUERY);
        query.setParameter("address", address);
        query.setParameter("phoneNumber", phoneNumber);
        query.setParameter("email", email);
        query.executeUpdate();
    }

    @Override
    public User create(User user) throws UserAlreadyExistsException {
        if (getUserByEmail(user.getEmail()) != null) {
            throw new UserAlreadyExistsException("Такой пользователь уже существует");
        }
        Session session = manager.unwrap(Session.class);
        user.setPassword(HashUtils.getHash(user.getPassword()));
        session.persist(user);
        return user;
    }

    @Override
    public List<User> read() {
        Session session = manager.unwrap(Session.class);
        return session.createQuery(GET_ALL_USERS_QUERY, User.class).getResultList();
    }

    @Override
    public User update(User user) {
        Session session = manager.unwrap(Session.class);
        return session.merge(user);
    }

    @Override
    public void delete(int id) {
        Session session = manager.unwrap(Session.class);
        User user = session.get(User.class, id);
        session.remove(user);
    }
}
