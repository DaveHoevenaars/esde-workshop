package org.acme;

import java.util.Properties;


/**
 * @author Jörn Neumeyer <neumeyer@code-lake.com>
 * @author Maurits van der Zee <vanderzee@code-lake.com>
 * @version 1.0
 */
public class DBConnection {

    protected final String DB_URL;
    // = "jdbc:mariadb://code-lake.com:3306/esde";
    protected final String DB_USERNAME;
    protected final String DB_PASSWORD;
    protected final String DB_HOST;
    protected final int DB_PORT;
    protected final String DB_NAME;

    // Properties
    protected final Properties properties;

    public DBConnection(String username, String password, String host, int port, String databaseName) {
        this.DB_USERNAME = username;
        this.DB_PASSWORD = password;
        this.DB_HOST = host;
        this.DB_PORT = port;
        this.DB_NAME = databaseName;

        // TODO construct correct URL
        this.DB_URL = "";

        properties = new Properties();
        properties.setProperty("user", DB_USERNAME);
        properties.setProperty("password", DB_PASSWORD);
    }

}
