package oracle.connecting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Класс Connecting демонстрирует установку соединения с СУБД.
 *
 * @see https://docs.oracle.com/javase/tutorial/jdbc/basics/connecting.html
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-11-29
 */
class Connecting {
    /**
     * Соединение с бд.
     */
	private Connection con = null;
    /**
     * Устанавливает соединение с СУБД.
     * @param url соединеия с СУБД.
     * @throws SQLException ошибка SQL.
     */
    public void setConnection(String url)  throws SQLException {
        this.con = DriverManager.getConnection(url);
    }
    /**
     * Устанавливает соединение с СУБД.
     * @param url соединеия с СУБД.
     * @param user имя пользователя СУБД.
     * @param pass пароль пользователя СУБД.
     * @throws SQLException ошибка SQL.
     */
    public void setConnection(String url, String user, String pass)  throws SQLException {
        this.con = DriverManager.getConnection(url, user, pass);
    }
    /**
     * Закрывает соединение с СУБД.
     * @throws SQLException ошибка SQL.
     */
    public void close() throws SQLException {
        if (this.con != null) {
            this.con.close();
        }
    }
    /**
     * Проверяет соединение на валидность.
     * @return true если соединение с СУБД валидное. Иначе false.
     * @throws SQLException ошибка SQL.
     */
    public boolean isValid()  throws SQLException {
        return this.con.isValid(0);
    }
}
