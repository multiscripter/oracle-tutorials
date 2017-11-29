package oracle.connecting;

import java.sql.SQLException;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
/**
 * Класс ConnectingTest тестирует работу класса Connecting.
 *
 * @see https://docs.oracle.com/javase/tutorial/jdbc/basics/connecting.html
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-11-29
 */
public class ConnectingTest {
    /**
     * Тестирует setConnection(). Установка соединения с MySQL к бд shop.
     */
    @Test
    public void testSetConnectionToMySQL() {
        try {
            Connecting c = new Connecting();
            String url = "jdbc:mysql://localhost:3306/shop";
            c.setConnection(url, "root", "mysqlrootpass");
            assertTrue(c.isValid());
            c.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует setConnection(). Установка соединения с PostgreSQL к бд oracle_jdbc.
     */
    @Test
    public void testSetConnectionToPostgreSQL() {
        try {
            Connecting c = new Connecting();
            String url = "jdbc:postgresql://localhost:5432/oracle_jdbc";
            c.setConnection(url, "postgres", "postgresrootpass");
            assertTrue(c.isValid());
            c.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует setConnection(). Установка соединения с Java DB (Derby) и создание бд testdb.
     */
    @Test
    public void testSetConnectionToJavaDB() {
        try {
            Connecting c = new Connecting();
            String url = "jdbc:derby:testdb;create=true";
            c.setConnection(url);
            assertTrue(c.isValid());
            c.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует setConnection(). Установка соединения с SQLite.
     * jdbc:sqlite:local_filename.db
     * jdbc:sqlite:D:/absolute/file/name.db
     * jdbc:sqlite::memory
     */
    @Test
    public void testSetConnectionToSQLite() {
        try {
            Connecting c = new Connecting();
            String url = "jdbc:sqlite:local_filename.db";
            c.setConnection(url);
            assertTrue(c.isValid());
            c.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}