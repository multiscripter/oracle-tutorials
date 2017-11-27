package oracle.processingsqlstatements;

import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.LinkedList;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
/**
 * Класс ProcessingSqlStatementsTest тестирует класс ProcessingSqlStatements.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-11-27
 */
public class ProcessingSqlStatementsTest {
    /**
     * Тестирует getUsersAsString().
     */
    @Test
    public void testGetUsersAsString() {
        try {
            String enc = Charset.defaultCharset().toString();
            System.out.println("Charset.defaultCharset(): " + enc);
            String[] expected = new String[2];
            expected[0] = "[id: 1, name: Путин, login: president, email: putin@mail.gov, date: 1952-10-07]";
            expected[1] = "[id: 2, name: Медведев, login: prime, email: dimon@kremlin.gov, date: 1951-11-11]";
            ProcessingSqlStatements pss = new ProcessingSqlStatements("postgresql", "localhost", 5432, "oracle_jdbc", "postgres", "postgresrootpass");
            LinkedList<String> usersStrList = pss.getUsersAsString();
            String[] result = new String[usersStrList.size()];
            usersStrList.toArray(result);
            assertArrayEquals(expected, result);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}