package oracle.gettingstarted;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс GettingStartedTest тестирует класс GettingStarted.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-11-27
 */
public class GettingStartedTest {
    /**
     * Тестирует getMessage(). Кастомное сообщение.
     */
    @Test
    public void testGetMessageWithCustomMessage() {
        GettingStarted gs = new GettingStarted("Learning Oracle tutorial JDBC Basecs.");
        String expected = "Learning Oracle tutorial JDBC Basecs.";
        assertEquals(expected, gs.getMessage());
    }
    /**
     * Тестирует getMessage(). Сообщение по умолчанию.
     */
    @Test
    public void testGetMessageWithDefaultMessage() {
        GettingStarted gs = new GettingStarted();
        String expected = "Сообщение по умолчанию.";
        assertEquals(expected, gs.getMessage());
    }
}