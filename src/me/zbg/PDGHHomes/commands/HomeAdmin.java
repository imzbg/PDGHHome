package me.zbg.PDGHHomes.commands;

import org.bukkit.command.defaults.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.zbg.PDGHHomes.settings.*;
import me.zbg.PDGHHomes.database.*;
import java.sql.*;
import java.util.*;

public class HomeAdmin extends BukkitCommand
{
    public HomeAdmin(final String cmd) {
        super(cmd);
    }
    
    public boolean execute(final CommandSender sender, final String alias, final String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        final Player p = (Player)sender;
        if (!p.hasPermission("pdgh.admin")) {
            return false;
        }
        if (args.length == 0) {
            p.sendMessage("§e»§6 Uso correto do comando §f/homeadmin§6:");
            p.sendMessage(" ");
            p.sendMessage("§7-§f /homeadmin reload");
            p.sendMessage("§e§o>§e Recarrega as mensagens e as configurações.");
            p.sendMessage(" ");
            p.sendMessage("§7-§f /homeadmin list <player>");
            p.sendMessage("§e§o>§e Mostra as homes de um jogador.");
            p.sendMessage(" ");
            p.sendMessage("§7-§f /homeadmin deletehome <player> <home>");
            p.sendMessage("§e§o>§e Deleta uma home de um jogador.");
            p.sendMessage(" ");
            p.sendMessage("§7- §f/homeadmin teleport <player> <home>");
            p.sendMessage("§e§o>§e Vai até a home de um jogador.");
            p.sendMessage(" ");
            p.sendMessage("§7-§f /homeadmin removeloja <player> <home>");
            p.sendMessage("§e§o>§e Força a home de um jogador a ficar privada.");
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            Settings.LoadSettings();
            Messages.LoadMessages();
            p.sendMessage("§3[PDGHHomes]§b Plugin recarregado.");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("list")) {
            try {
                final PreparedStatement ps = SQL.connection.prepareStatement("SELECT * FROM homes WHERE user=?;");
                ps.setString(1, args[1].toLowerCase());
                final ResultSet rs = ps.executeQuery();
                final List<String> homes = new ArrayList<String>();
                while (rs.next()) {
                    if (rs.getString("type").equals("0")) {
                        homes.add("§f" + rs.getString("home"));
                    }
                    if (rs.getString("type").equals("1")) {
                        homes.add("§a" + rs.getString("home"));
                    }
                }
                if (homes.isEmpty()) {
                    p.sendMessage("§4»§c O jogador §f" + args[1] + "§c não possuí nenhuma home.");
                    return false;
                }
                p.sendMessage("§e»§6 Homes do jogador " + args[1] + ":§f " + Arrays.toString(homes.toArray()).replace(",", "§6,").replace("\\[|\\]", "") + "§6.");
                if (Arrays.toString(homes.toArray()).contains("§a")) {
                    p.sendMessage("§e»§6 Homes em §a*VERDE*§6 estão atualmente públicas.");
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("deletehome")) {
                final String n = args[1].toLowerCase();
                final String h = args[2].toLowerCase();
                if (dbManager.homeExists(n, h)) {
                    dbManager.deleteHome(n, h);
                    p.sendMessage("§e»§6 Você deletou a home §f" + args[2] + "§6 do jogador §f" + args[1] + "§6.");
                }
                else {
                    p.sendMessage("§4»§c O jogador §f" + args[1] + " §cnão tem uma home chamada §f" + args[2] + "§c.");
                }
            }
            if (args[0].equalsIgnoreCase("teleport")) {
                final String n = args[1].toLowerCase();
                final String h = args[2].toLowerCase();
                if (dbManager.homeExists(n, h)) {
                    p.teleport(LocationSerializer.deserializeLocation(dbManager.getHomeSerialized(n, h)));
                    p.sendMessage("§e»§6 Teletransportado até a home §f" + args[2] + "§6 do jogador §f" + args[1] + "§6.");
                }
                else {
                    p.sendMessage("§4»§c O jogador §f" + args[1] + " §cnão tem uma home chamada §f" + args[2] + "§c.");
                }
            }
            if (args[0].equalsIgnoreCase("removeloja")) {
                final String n = args[1].toLowerCase();
                final String h = args[2].toLowerCase();
                if (dbManager.homeExists(n, h)) {
                    if (dbManager.homeIsPublic(n, h)) {
                        dbManager.setHome(n, h, dbManager.getHomeSerialized(n, h), "0");
                        p.sendMessage("§e»§6 Você força a home §f" + args[2] + "§6 do jogador §f" + args[1] + "§6 a ser privada novamente.");
                    }
                    else {
                        p.sendMessage("§4»§c A home §f" + args[2] + "§c do jogador §f" + args[1] + "§c já é privada.");
                    }
                }
                else {
                    p.sendMessage("§4»§c O jogador §f" + args[1] + " §cnão tem uma home chamada §f" + args[2] + "§c.");
                }
            }
        }
        return false;
    }
}
