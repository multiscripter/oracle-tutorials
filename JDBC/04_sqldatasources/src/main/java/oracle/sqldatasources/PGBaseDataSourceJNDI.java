package oracle.sqldatasources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Properties;
//import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.postgresql.ds.PGPoolingDataSource;
/**
 * Класс PGBaseDataSourceJNDI демонстрирует работу с объектом DataSource
 * базовой реализации с использованием JNDI на примере СУБД PostgreSQL.
 * !!! Для запуска необходим сервер приложений JAVA !!!
 *
 * @see https://docs.oracle.com/javase/tutorial/jdbc/basics/sqldatasources.html
 * @see https://jdbc.postgresql.org/documentation/head/ds-ds.html
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-12-01
 */
class PGBaseDataSourceJNDI {
    /**
     * Соединение с бд.
     */
	private Connection con;
    /**
     * Конструктор. Устанавливает свойства.
     * @param server url сервера (например localhost).
     * @param db имя базы данных.
     * @param user пользователь СУБД.
     * @param pass пароль пользователя СУБД.
     * @throws NamingException ошибка JNDI.
     */
    PGBaseDataSourceJNDI(String server, String db, String user, String pass) throws NamingException {
        PGPoolingDataSource ds = new PGPoolingDataSource();
        ds.setDataSourceName("A Data Source");
        ds.setServerName(server);
        ds.setDatabaseName(db);
        ds.setUser(user);
        ds.setPassword(pass);
        Properties props = new Properties();
        props.setProperty();
        new InitialContext(props).rebind("DataSource", ds);
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
     * @throws NamingException ошибка JNDI.
     */
    public LinkedList<HashMap<String, String>> select(String query) throws SQLException, NamingException {
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
     * @throws NamingException ошибка JNDI.
     */
    public void setConnection() throws SQLException, NamingException {
        DataSource source = (DataSource) new InitialContext().lookup("DataSource");
        this.con = source.getConnection();
    }
}