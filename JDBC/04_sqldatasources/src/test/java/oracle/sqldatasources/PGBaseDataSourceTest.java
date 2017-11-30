package oracle.sqldatasources;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
/**
 * Класс PGBaseDataSourceTest тестирует работу класса PGBaseDataSource.
 *
 * @see https://docs.oracle.com/javase/tutorial/jdbc/basics/sqldatasources.html
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-11-30
 */
public class PGBaseDataSourceTest {
    /**
     * Проверяет установку, валидность и закрытие соединения с СУБД PostgreSQL к бд oracle_jdbc.
     */
    @Test
    public void checkConnectionToPostgreSQL() {
        try {
            PGBaseDataSource pgds = new PGBaseDataSource("localhost", "oracle_jdbc", "postgres", "postgresrootpass");
            pgds.setConnection();
            assertTrue(pgds.isValid());
            pgds.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует LinkedList<HashMap<String, String>> select(String query).
     */
    @Test
    public void testSelect() {
        try {
            PGBaseDataSource pgds = new PGBaseDataSource("localhost", "oracle_jdbc", "postgres", "postgresrootpass");
            pgds.setConnection();
            LinkedList<HashMap<String, String>> rl = pgds.select("select * from users");
            System.out.println("size: " + rl.size());
            if (!rl.isEmpty()) {
                for (HashMap<String, String> entry : rl) {
                    GregorianCalendar cal = new GregorianCalendar();
                    SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
                    String strDate = entry.get("createdate");
                    Date date = sdf.parse(strDate);
                    cal.setTime(date);
                    System.out.println(String.format("user[id: %d, name: %s, login: %s, email: %s, date: %5$tY-%5$tm-%5$td]", Integer.parseInt(entry.get("id")), entry.get("name"), entry.get("login"), entry.get("email"), cal));
                }
            }
            pgds.close();
        } catch (SQLException | ParseException ex) {
            ex.printStackTrace();
        }
    }
}