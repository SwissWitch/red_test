package red.test.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class DataSource {
    String className = "org.firebirdsql.jdbc.FBDriver";

    private String url;
    private String login;
    private String password;

    public DataSource() {
        ResourceBundle bundle = ResourceBundle.getBundle("config");
        login = bundle.getString("db_user");
        password = bundle.getString("db_password");
        url = bundle.getString("db_url");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(className);
        return DriverManager.getConnection(url, login, password);
    }

    public void closeConnection(Connection connection) {
        if (connection == null) return;
        try {
            connection.close();
        } catch (SQLException e) {
//            e.printStackTrace(); log
        }
    }
}
