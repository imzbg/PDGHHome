package me.zbg.PDGHHomes.commands;

import org.bukkit.command.defaults.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.zbg.PDGHHomes.database.*;
import me.zbg.PDGHHomes.settings.*;
import java.sql.*;
import java.util.*;

public class ListHomes extends BukkitCommand
{
    public ListHomes(final String player) {
        super(player);
    }
    
    public boolean execute(final CommandSender sender, final String alias, final String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        final Player p = (Player)sender;
        try {
            final PreparedStatement ps = SQL.connection.prepareStatement("SELECT * FROM homes WHERE user=?;");
            ps.setString(1, p.getName().toLowerCase());
            final ResultSet rs = ps.executeQuery();
            final List<String> homes = new ArrayList<String>();
            while (rs.next()) {
                if (rs.getString("type").equals("0")) {
                    homes.add(String.valueOf(Messages.y) + rs.getString("home"));
                }
                if (rs.getString("type").equals("1")) {
                    homes.add(String.valueOf(Messages.z) + rs.getString("home"));
                }
            }
            if (homes.isEmpty()) {
                p.sendMessage(Messages.k);
                return false;
            }
            p.sendMessage(String.valueOf(Messages.v) + Arrays.toString(homes.toArray()).replace(",", Messages.w).replaceAll("\\[|\\]", ""));
            if (Arrays.toString(homes.toArray()).contains(Messages.z)) {
                p.sendMessage(Messages.x);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
