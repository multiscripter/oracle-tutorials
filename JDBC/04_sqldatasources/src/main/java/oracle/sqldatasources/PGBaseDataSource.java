package oracle.sqldatasources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import org.postgresql.ds.PGSimpleDataSource;
/**
 * Класс PGBaseDataSource демонстрирует работу с объектом DataSource базовой
 * реализации на примере СУБД PostgreSQL.
 *
 * @see https://docs.oracle.com/javase/tutorial/jdbc/basics/sqldatasources.html
 * @see https://jdbc.postgresql.org/documentation/head/ds-ds.html
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-11-30
 */
class PGBaseDataSource {
    /**
     * Соединение с бд.
     */
	private Connection con;
    /**
     * DataSource.
     */
    private final PGSimpleDataSource ds;
    /**
     * Конструктор. Устанавливает свойства.
     * @param server url сервера (например localhost).
     * @param db имя базы данных.
     * @param user пользователь СУБД.
     * @param pass пароль пользователя СУБД.
     */
    PGBaseDataSource(String server, String db, String user, String pass) {
        this.ds = new PGSimpleDataSource();
        this.ds.setServerName(server);
        this.ds.setDatabaseName(db);
        this.ds.setUser(user);
        this.ds.setPassword(pass);
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
    /**
     * Выполняет select sql-запрос.
     * @param query sql-запрос.
     * @return список с результатом запроса к бд.
     * @throws SQLException ошибка SQL.
     */
    public LinkedList<HashMap<String, String>> select(String query) throws SQLException {
    	if (this.con == null) {
			this.setConnection();
    	}
    	LinkedList<HashMap<String, String>> rl = new LinkedList<>();
    	try (PreparedStatement pstmt = this.con.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
        	if (rs != null) {
            	ResultSetMetaData rsmd = rs.getMetaData();
            	while (rs.next()) {
            		HashMap<String, String> entry = new HashMap<>();
            		for (int a = 1; a < rsmd.getColumnCount() + 1; a++) {
	            		entry.put(rsmd.getColumnName(a), rs.getString(a));
	            	}
            		rl.add(entry);
            	}
        	}
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return rl;
    }
    /**
     * Устанавливает соединение с СУБД.
     * @throws SQLException ошибка SQL.
     */
    public void setConnection() throws SQLException {
        this.con = this.ds.getConnection();
    }
}