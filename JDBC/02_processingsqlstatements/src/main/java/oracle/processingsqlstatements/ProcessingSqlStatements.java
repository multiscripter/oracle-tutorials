package oracle.processingsqlstatements;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.GregorianCalendar;
import java.util.LinkedList;
/**
 * Класс ProcessingSqlStatements демонстрирует обработку SQL-выражений с помощью JDBC.
 *
 * @see https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-11-27
 */
class ProcessingSqlStatements {
    /**
     * Соединение с бд.
     */
	private Connection con = null;
    /**
     * База данных.
     */
    private String db;
    /**
     * Пароль пользователя СУБД.
     */
    private String pass;
    /**
     * Порт.
     */
    private int port;
    /**
     * Протокол.
     */
    private String protocol;
    /**
     * Источник.
     */
    private String src;
    /**
     * Пользователь СУБД.
     */
    private String user;
    /**
     * Конструктор.
     * @param protocol протокол (например: postgresql).
     * @param src источник (например: localhost).
     * @param port порт (например: 5432).
     * @param db база данных (например: jpack2p8ch4task2).
     * @param user пользователь СУБД (например: postgres).
     * @param pass пароль пользователя СУБД (например: postgresrootpass).
     */
    ProcessingSqlStatements(String protocol, String src, int port, String db, String user, String pass) {
        this.db = db;
        this.pass = pass;
        this.port = port;
        this.protocol = protocol;
        this.src = src;
        this.user = user;
    }
    /**
     * Устанавливает соединение с бд.
     * @throws SQLException ошибка SQL.
     */
    public void setConnection()  throws SQLException {
        StringBuilder str =  new StringBuilder();
        str.append("jdbc:");
        str.append(protocol);
        str.append("://");
        str.append(src);
        if (port != 0) {
            str.append(":" + port);
        }
        str.append("/");
        if (db != null) {
            str.append(db);
        }
        this.con = DriverManager.getConnection(str.toString(), user, pass);
    }
    /**
     * Выполняет select-запрос к бд.
     * @return Связный список с результатом запроса к бд.
     * @throws SQLException ошибка SQL.
     */
    public LinkedList<String> getUsersAsString() throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        LinkedList<String> result = new LinkedList<>();
        try {
            if (this.con == null) {
                this.setConnection();
            }
            stmt = this.con.createStatement();
            rs = stmt.executeQuery("select * from users order by id limit 2");
            //System.out.println("rs.getFetchSize(): " + rs.getFetchSize());//Выводит: 0
            while (rs.next()) {
                GregorianCalendar date = new GregorianCalendar();
                date.setTime(rs.getDate("createDate"));
                result.add(String.format("[id: %d, name: %s, login: %s, email: %s, date: %5$tY-%5$tm-%5$td]", rs.getInt("id"), rs.getString("name"), rs.getString("login"), rs.getString("email"), date));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return result;
    }
    /**
     * Закрывает соединение с бд.
     * @throws SQLException ошибка SQL.
     */
    public void closeConnection() throws SQLException {
        if (this.con != null) {
            this.con.close();
        }
    }
}