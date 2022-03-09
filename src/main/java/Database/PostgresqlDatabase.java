package Database;

// Java Imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Executors;

// Third Party Imports
import org.postgresql.ds.PGSimpleDataSource;

/**
 * Class for interfacing with a database
 */
public class PostgresqlDatabase {

    // Data Members
    private static final int TIMEOUT = 10;
    private static final int NETWORK_TIMEOUT = 1000;
    private PGSimpleDataSource dataSource;
    private Connection connection;

    // Constructors
    /**
     * Constructor to configure database connection
     * @param dbName    Name of the database to connect to
     * @param host      Address of the server the database is host on
     * @param port      Port on which to connect
     * @param user      Username to access the database
     * @param pass      Password to access the database
     */
    public PostgresqlDatabase(String dbName, String host, int port, String user, String pass) {
        this.dataSource = new PGSimpleDataSource();
        this.dataSource.setDatabaseName(dbName);
        this.dataSource.setServerName(host);
        this.dataSource.setPortNumber(port);
        this.dataSource.setUser(user);
        this.dataSource.setPassword(pass);
        this.dataSource.setConnectTimeout(TIMEOUT);
        this.dataSource.setSocketTimeout(TIMEOUT);
        this.dataSource.setLoginTimeout(TIMEOUT);
        try {
            this.connection = this.dataSource.getConnection();
            this.connection.setNetworkTimeout(Executors.newFixedThreadPool(3), NETWORK_TIMEOUT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Connection Methods
    /**
     * Checks if the current connection is valid and establishes a new connection if it is not
     * @param connection    Connection to check
     * @param numRetries    Number of times to retry after an initial failure
     * @return              A valid database connection
     */
    public Connection connect(Connection connection, int numRetries) {
        try {
            while (!connection.isValid(TIMEOUT)) {
                if (numRetries-- <= 0) {
                    this.closeResources();
                    connection = this.dataSource.getConnection();
                    connection.setNetworkTimeout(Executors.newFixedThreadPool(3), NETWORK_TIMEOUT);
                    return connection;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    /**
     * Closes the connection and resources that may be open
     */
    public void closeResources() {
        try {
            if (this.connection != null) {
                this.connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Public Methods
    /**
     * Gets the version details of the database
     * @return  String details of database
     */
    public String getVersion() {
        String result = null;
        try (PreparedStatement prepStatement = this.connection.prepareCall("SELECT version()")) {
            prepStatement.setQueryTimeout(TIMEOUT);
            try (ResultSet resultSet = prepStatement.executeQuery()) {
                while (resultSet.next()) {
                    result = resultSet.getString(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    // Getters & Setters
    /**
     * Returns the datasource
     * @return  The datasource from the PG database
     */
    public PGSimpleDataSource getDataSource() {
        return dataSource;
    }
    /**
     * Sets the datasource
     * @param dataSource    The datasource from the PG database
     */
    public void setDataSource(PGSimpleDataSource dataSource) {
        this.dataSource = dataSource;
    }
    /**
     * Returns the connection to the database
     * @return  The connection to the database
     */
    public Connection getConnection() {
        return connection;
    }
    /**
     * Sets the connection to the database
     * @param connection    The connection to the database
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
