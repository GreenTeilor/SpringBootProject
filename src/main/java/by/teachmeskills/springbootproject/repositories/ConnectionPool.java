package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.exceptions.UnableToExecuteQueryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
    //Singleton instance
    private static volatile ConnectionPool instance;

    //Configuration properties
    private static final String DB_PROPERTY_FILE = "application";
    private static final String DB_URL = "db.url";
    private static final String DB_LOGIN = "db.login";
    private static final String DB_PASS = "db.pass";
    private static final int MAX_CONNECTION_COUNT = 10;
    private static final int MIN_CONNECTION_COUNT = 5;

    private static final String url;
    private static final String login;
    private static final String pass;

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DB_PROPERTY_FILE);
        url = resourceBundle.getString(DB_URL);
        login = resourceBundle.getString(DB_LOGIN);
        pass = resourceBundle.getString(DB_PASS);
    }

    private volatile int currentConnectionNumber = MIN_CONNECTION_COUNT;
    private final Integer currentConnectionNumberLock = Integer.valueOf(0); //Used just for synchronization
    private final BlockingQueue<Connection> pool = new ArrayBlockingQueue<>(MAX_CONNECTION_COUNT, true);

    //Singleton
    public static ConnectionPool getInstance() {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }

        return instance;
    }

    //Add new connection to queue in constructor
    private ConnectionPool() {

        for (int i = 0; i < MIN_CONNECTION_COUNT; i++) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                pool.add(DriverManager.getConnection(url, login, pass));
            } catch (SQLException | ClassNotFoundException e) {
                logger.error(e.getMessage());
            }
        }

    }

    private void openAdditionalConnection() throws UnableToExecuteQueryException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            pool.add(DriverManager.getConnection(url, login, pass));
            synchronized (currentConnectionNumberLock) {
                currentConnectionNumber++;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new UnableToExecuteQueryException("New connection wasn't added in the connection pool");
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            if (pool.isEmpty() && currentConnectionNumber < MAX_CONNECTION_COUNT) {
                openAdditionalConnection();
            }
            connection = pool.take();
        } catch (InterruptedException | UnableToExecuteQueryException e) {
            logger.error(e.getMessage());
        }
        return connection;
    }

    public void returnConnection(Connection connection) {
        if (connection != null) {
            if (currentConnectionNumber > MIN_CONNECTION_COUNT) {
                synchronized (currentConnectionNumberLock) {
                    currentConnectionNumber--;
                }
            }
            try {
                pool.put(connection);
            } catch (InterruptedException e) {
                logger.error("Connection wasn't successfully returned to the pool");
            }
        }
    }

    public void closeConnections() {
        pool.forEach(connection -> {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        });
    }

}
