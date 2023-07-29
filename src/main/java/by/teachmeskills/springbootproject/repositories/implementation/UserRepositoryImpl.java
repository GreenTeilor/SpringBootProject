package by.teachmeskills.springbootproject.repositories.implementation;

import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.UnableToExecuteQueryException;
import by.teachmeskills.springbootproject.exceptions.UserAlreadyExistsException;
import by.teachmeskills.springbootproject.repositories.UserRepository;
import by.teachmeskills.springbootproject.utils.HashUtils;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final static String ADD_USER_QUERY = "INSERT INTO users (name, lastName, email, birthDate, registrationDate, balance, password) VALUES (?, ?, ?, ?, ?, 0.0, ?)";
    private final static String GET_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private final static String DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";
    private final static String GET_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private final static String SEARCH_USER_QUERY = "SELECT * FROM users WHERE email = ? and password = ?";
    private final static String UPDATE_ADDRESS_AND_PHONE_NUMBER_QUERY = "UPDATE users SET address = ?, phoneNumber = ? WHERE email = ?";
    private final static String GET_ALL_USERS_QUERY = "SELECT * FROM users";

    @Override
    public User getUserByEmail(String email) throws UnableToExecuteQueryException {
        User user = null;
        Connection connection = pool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(GET_USER_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                user = User.builder().id(set.getInt("id")).name(set.getString("name")).lastName(set.getString("lastName")).
                        email(set.getString("email")).birthDate(set.getTimestamp("birthDate").toLocalDateTime().toLocalDate()).
                        balance(set.getBigDecimal("balance")).password(set.getString("password")).address(set.getString("address")).
                        phoneNumber(set.getString("phoneNumber")).build();
            }
            set.close();
            return user;
        } catch (SQLException e) {
            throw new UnableToExecuteQueryException("Unable to execute query GET_USER_BY_EMAIL_QUERY");
        } finally {
            pool.returnConnection(connection);
        }
    }
    @Override
    public User getUserById(int id) throws UnableToExecuteQueryException {
        User user = null;
        Connection connection = pool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(GET_USER_BY_ID)) {
            statement.setString(1, String.valueOf(id));
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                user = User.builder().id(set.getInt("id")).name(set.getString("name")).lastName(set.getString("lastName")).
                        email(set.getString("email")).birthDate(set.getTimestamp("birthDate").toLocalDateTime().toLocalDate()).
                        balance(set.getBigDecimal("balance")).password(set.getString("password")).address(set.getString("address")).
                        phoneNumber(set.getString("phoneNumber")).build();
            }
            set.close();
            return user;
        } catch (SQLException e) {
            throw new UnableToExecuteQueryException("Unable to execute query GET_USER_BY_EMAIL_QUERY");
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public User getUser(String email, String password) throws UnableToExecuteQueryException {
        User user = null;
        Connection connection = pool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SEARCH_USER_QUERY)) {
            statement.setString(1, email);
            statement.setString(2, HashUtils.getHash(password));
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                user = User.builder().id(set.getInt("id")).name(set.getString("name")).lastName(set.getString("lastName")).
                        email(set.getString("email")).birthDate(set.getTimestamp("birthDate").toLocalDateTime().toLocalDate()).registrationDate(set.getTimestamp("birthDate").toLocalDateTime().toLocalDate()).
                        balance(set.getBigDecimal("balance")).password(set.getString("password")).address(set.getString("address")).
                        phoneNumber(set.getString("phoneNumber")).build();
            }
            set.close();
            return user;
        } catch (SQLException e) {
            throw new UnableToExecuteQueryException("Unable to execute query SEARCH_USER_QUERY");
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public void updateAddressAndPhoneNumber(String address, String phoneNumber, String email) throws UnableToExecuteQueryException {
        Connection connection = pool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ADDRESS_AND_PHONE_NUMBER_QUERY)) {
            statement.setString(1, address);
            statement.setString(2, phoneNumber);
            statement.setString(3, email);
            statement.execute();
        } catch (SQLException e) {
            throw new UnableToExecuteQueryException("Unable to execute query UPDATE_ADDRESS_AND_PHONE_NUMBER_QUERY");
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public User create(User user) throws UnableToExecuteQueryException, UserAlreadyExistsException {
        Connection connection = pool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(ADD_USER_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            if (getUserByEmail(user.getEmail()) != null) {
                throw new UserAlreadyExistsException("Такой пользователь уже существует");
            }
            statement.setString(1, user.getName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setTimestamp(4, Timestamp.valueOf(user.getBirthDate().atStartOfDay()));
            statement.setTimestamp(5, Timestamp.valueOf(user.getRegistrationDate().atStartOfDay()));
            statement.setString(6, HashUtils.getHash(user.getPassword()));
            statement.execute();
            ResultSet autoGenerated = statement.getGeneratedKeys();
            if (autoGenerated.next()) {
                user.setId(autoGenerated.getInt(1));
            }
            autoGenerated.close();
            return user;
        } catch (SQLException e) {
            throw new UnableToExecuteQueryException("Unable to execute query ADD_USER_QUERY");
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public List<User> read() throws UnableToExecuteQueryException {
        List<User> result = new ArrayList<>();
        Connection connection = pool.getConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(GET_ALL_USERS_QUERY);
            while (set.next()) {
                result.add(User.builder().id(set.getInt("id")).name(set.getString("name")).lastName(set.getString("lastName")).
                        email(set.getString("email")).birthDate(set.getTimestamp("birthDate").toLocalDateTime().toLocalDate()).registrationDate(set.getTimestamp("birthDate").toLocalDateTime().toLocalDate()).
                        balance(set.getBigDecimal("balance")).password(set.getString("password")).address(set.getString("address")).
                        phoneNumber(set.getString("phoneNumber")).build());
            }
            set.close();
            return result;
        } catch (SQLException e) {
            throw new UnableToExecuteQueryException("Unable to execute query GET_ALL_USERS_QUERY");
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public User update(User user) throws UnableToExecuteQueryException {
        return getUserById(user.getId());
    }

    @Override
    public void delete(int id) throws UnableToExecuteQueryException {
        Connection connection = pool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_USER_BY_ID)) {
            statement.setString(1, String.valueOf(id));
            statement.execute();
        } catch (SQLException e) {
            throw new UnableToExecuteQueryException("Unable to execute query DELETE_USER_BY_ID_QUERY");
        } finally {
            pool.returnConnection(connection);
        }
    }
}
