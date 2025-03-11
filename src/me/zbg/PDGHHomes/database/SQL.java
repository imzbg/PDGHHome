package me.zbg.PDGHHomes.database;

import java.io.*;
import me.zbg.PDGHHomes.*;
import java.sql.*;

public class SQL
{
    private String user;
    private String host;
    private String database;
    private String password;
    public static Connection connection;
    static Statement statement;
    private SQLType sqlType;
    private File db;
    
    public SQL(final String user, final String password, final String host, final String database, final SQLType sqlType, final Main plugin) {
        this.user = user;
        this.password = password;
        this.host = host;
        this.database = database;
        this.sqlType = sqlType;
    }
    
    public SQL(final String database, final File folder, final SQLType sqlType, final Main plugin) {
        this.db = folder;
        this.database = database;
        this.sqlType = sqlType;
    }
    
    public void startConnection() {
        if (this.getType()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                SQL.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + "/" + this.database, this.user, this.password);
                (SQL.statement = SQL.connection.createStatement()).execute("CREATE TABLE IF NOT EXISTS homes (user VARCHAR(16) NOT NULL, home VARCHAR(16) NOT NULL, loc VARCHAR(16) NOT NULL, type VARCHAR(16) NOT NULL)");
            }
            catch (SQLException | ClassNotFoundException ex3) {
                final Exception ex = null;
                final Exception e = ex;
                e.printStackTrace();
            }
        }
        else {
            try {
                Class.forName("org.sqlite.JDBC");
                SQL.connection = DriverManager.getConnection("jdbc:sqlite:" + this.db.getAbsolutePath() + File.separator + this.database + ".db");
                (SQL.statement = SQL.connection.createStatement()).execute("CREATE TABLE IF NOT EXISTS homes (user VARCHAR(16) NOT NULL, home VARCHAR(16) NOT NULL, loc VARCHAR(16) NOT NULL, type VARCHAR(16) NOT NULL)");
            }
            catch (ClassNotFoundException | SQLException ex4) {
                final Exception ex2 = null;
                final Exception e = ex2;
                e.printStackTrace();
            }
        }
    }
    
    public void closeConnection() {
        try {
            SQL.statement.close();
            SQL.connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean getType() {
        return this.sqlType == SQLType.MySQL;
    }
    
    public enum SQLType
    {
        MySQL("MySQL", 0), 
        SQLite("SQLite", 1);
        
        private SQLType(final String s, final int n) {
        }
    }
}
