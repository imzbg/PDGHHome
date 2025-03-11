package me.zbg.PDGHHomes.database;

import java.sql.*;

public class dbManager
{
    public static void setHome(final String a, final String b, final String c, final String d) {
        if (homeExists(a, b)) {
            deleteHome(a, b);
        }
        try {
            final PreparedStatement ps = SQL.connection.prepareStatement("INSERT INTO homes (user,home,loc,type) VALUES (?,?,?,?);");
            ps.setString(1, a.toLowerCase());
            ps.setString(2, b.toLowerCase());
            ps.setString(3, c);
            ps.setString(4, d);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteHome(final String a, final String b) {
        try {
            final PreparedStatement ps = SQL.connection.prepareStatement("DELETE FROM homes WHERE user=? AND home=?");
            ps.setString(1, a.toLowerCase());
            ps.setString(2, b.toLowerCase());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean homeExists(final String a, final String b) {
        try {
            final PreparedStatement ps = SQL.connection.prepareStatement("SELECT * FROM homes WHERE user=? AND home=?");
            ps.setString(1, a.toLowerCase());
            ps.setString(2, b.toLowerCase());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean homeIsPublic(final String a, final String b) {
        try {
            final PreparedStatement ps = SQL.connection.prepareStatement("SELECT * FROM homes WHERE user=? AND home=?");
            ps.setString(1, a.toLowerCase());
            ps.setString(2, b.toLowerCase());
            final ResultSet rs = ps.executeQuery();
            if (rs.getString("type").equals("1")) {
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static String getHomeSerialized(final String a, final String b) {
        try {
            final PreparedStatement ps = SQL.connection.prepareStatement("SELECT * FROM homes WHERE user=? AND home=?");
            ps.setString(1, a.toLowerCase());
            ps.setString(2, b.toLowerCase());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("loc");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static int homesByPlayer(final String a) {
        int i = 0;
        try {
            final PreparedStatement ps = SQL.connection.prepareStatement("SELECT * FROM homes WHERE user=?;");
            ps.setString(1, a.toLowerCase());
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ++i;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
}
