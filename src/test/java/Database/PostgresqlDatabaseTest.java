package Database;

// Java Imports
import java.sql.Connection;
import java.sql.SQLException;

// Third Party Imports
import static org.junit.Assert.assertNotNull;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for database functionality
 */
public class PostgresqlDatabaseTest {

    // Data Members
    private static PostgresqlDatabase pgDB;
    private static Connection connection;

    // Set-up Methods
    /**
     * Connects to database to run tests
     * @throws SQLException SQL Exception for error handling
     */
    @BeforeClass
    public static void setUp() throws SQLException {
        pgDB = new PostgresqlDatabase("store_database", "localhost", 5432, "postgres", "letmein");
        connection = pgDB.getDataSource().getConnection();
    }

    // Test Methods

    /**
     * Basic test to check connection
     */
    @Test
    public void testGetVersion() {
        System.out.println("PostgresqlDatabaseTest: testGetVersion");
        String result = pgDB.getVersion();
        System.out.println(result);
        assertNotNull(result);
    }

    // Clean-up Methods
    /**
     * Closes resources
     * @throws SQLException SQL Exception for error handling
     */
    @AfterClass
    public static void tearDown() throws SQLException {
        connection.close();
    }
}
