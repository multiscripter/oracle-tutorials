package oracle.processingsqlstatements;

import java.io.UnsupportedEncodingException;
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
            String sysEnc = Charset.defaultCharset().toString();
            System.out.println("Charset.defaultCharset(): " + sysEnc);
            String[] expected = new String[2];
            expected[0] = "[id: 1, name: Путин, login: president, email: putin@mail.gov, date: 1952-10-07]";
            expected[1] = "[id: 2, name: Медведев, login: prime, email: dimon@kremlin.gov, date: 1951-11-11]";
            ProcessingSqlStatements pss = new ProcessingSqlStatements("postgresql", "localhost", 5432, "oracle_jdbc", "postgres", "postgresrootpass");
            LinkedList<String> usersStrList = pss.getUsersAsString();
            String[] result = new String[usersStrList.size()];
            usersStrList.toArray(result);
            if (!expected[0].equals(result[0])) {
	            // Обнаруживаем кодировку строки из бд.
	            StrEncDetector sed = new StrEncDetector();
	            //Win: MACCYRILLIC, Linux: UTF-8
	            System.out.println("expected[0]: " + sed.getEncoding(expected[0]));
	            //Win: IBM866, Linux: UTF-8
	            System.out.println("result[0]: " + sed.getEncoding(result[0]));
	            String resEnc = sed.getEncoding(result[0]);
	            System.out.println("----------");
	            System.out.println(expected[0]);
	            System.out.println("----------");
	            String[] encodes = new String[] {"IBM866", "MACCYRILLIC", "windows-1251", "UTF-8"};
	            for (String e1 : encodes) {
	                for (String e2 : encodes) {
	                    System.out.println("e1: " + e1 + ", e2: " + e2);
	                    System.out.println(new String(result[0].getBytes(e1), e2));
	                    // Перекодируем строки, полученные из бд.
	                    result[0] = new String(result[0].getBytes(e1), e2);
	                    System.out.println(expected[0].equals(result[0]));
	                }
	            }
	            System.out.println("----------");
	        }
            assertArrayEquals(expected, result);
        } catch (SQLException | UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }
}