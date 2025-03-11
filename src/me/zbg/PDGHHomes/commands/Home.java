package me.zbg.PDGHHomes.commands;

import org.bukkit.command.defaults.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.zbg.PDGHHomes.database.*;
import me.zbg.PDGHHomes.settings.*;
import org.bukkit.*;
import me.zbg.PDGHHomes.*;
import org.bukkit.plugin.*;

public class Home extends BukkitCommand
{
    public Home(final String home) {
        super(home);
    }
    
    public boolean execute(final CommandSender sender, final String alias, final String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        final Player p = (Player)sender;
        if (args.length == 0) {
            p.sendMessage(Messages.d);
            p.sendMessage("§e»§f /home <nome>§7 - §6Vai at\u00e9 uma de suas homes.");
            p.sendMessage("§e»§f /home <jogador> <nome>§7 - §6Vai at\u00e9 a home de um jogador.");
            return false;
        }
        if (args.length > 2) {
            p.sendMessage(Messages.d);
            p.sendMessage("§e»§f /home <nome>§7 - §6Vai at\u00e9 uma de suas homes.");
            p.sendMessage("§e»§f /home <jogador> <nome>§7 - §6Vai at\u00e9 a home de um jogador.");
            return false;
        }
        if (args.length == 1) {
            if (!dbManager.homeExists(p.getName(), args[0])) {
                p.sendMessage(Messages.b.replace("@home", args[0]));
                return false;
            }
            if (p.hasPermission("pdgh.vip")) {
                p.teleport(LocationSerializer.deserializeLocation(dbManager.getHomeSerialized(p.getName(), args[0])));
                p.sendMessage(Messages.e.replace("@home", args[0]));
            }
            else {
                p.sendMessage(Messages.g.replace("@s", String.valueOf(Settings.cooldown)));
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)Main.getInstance(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        p.teleport(LocationSerializer.deserializeLocation(dbManager.getHomeSerialized(p.getName(), args[0])));
                        p.sendMessage(Messages.e.replace("@home", args[0]));
                    }
                }, (long)(20 * Settings.cooldown));
            }
        }
        if (args.length == 2) {
            if (!dbManager.homeExists(args[0], args[1])) {
                p.sendMessage(Messages.h.replace("@player", args[0]).replace("@home", args[1]));
                return false;
            }
            if (!dbManager.homeIsPublic(args[0], args[1])) {
                p.sendMessage(Messages.i.replace("@home", args[1]).replace("@player", args[0]));
                return false;
            }
            if (p.hasPermission("pdgh.vip")) {
                p.teleport(LocationSerializer.deserializeLocation(dbManager.getHomeSerialized(args[0], args[1])));
                p.sendMessage(Messages.j.replace("@home", args[1]).replace("@player", args[0]));
            }
            else {
                p.sendMessage(Messages.g.replace("@s", String.valueOf(Settings.cooldown)));
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)Main.getInstance(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        p.teleport(LocationSerializer.deserializeLocation(dbManager.getHomeSerialized(args[0], args[1])));
                        p.sendMessage(Messages.j.replace("@home", args[1]).replace("@player", args[0]));
                    }
                }, (long)(20 * Settings.cooldown));
            }
        }
        return false;
    }
}
