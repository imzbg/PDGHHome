package me.zbg.PDGHHomes.commands;

import org.bukkit.command.defaults.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.zbg.PDGHHomes.settings.*;
import me.zbg.PDGHHomes.database.*;

public class Loja extends BukkitCommand
{
    public Loja(final String home) {
        super(home);
    }
    
    public boolean execute(final CommandSender sender, final String alias, final String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        final Player p = (Player)sender;
        if (!p.hasPermission("pdgh.vip")) {
            p.sendMessage("§c» Apenas §VIP's §cpodem deixar suas homes públicas.");
            return false;
        }
        if (args.length != 1) {
            p.sendMessage(Messages.l);
            return false;
        }
        if (!dbManager.homeExists(p.getName(), args[0])) {
            p.sendMessage(Messages.b.replace("@home", args[0]));
            return false;
        }
        if (dbManager.homeIsPublic(p.getName(), args[0])) {
            p.sendMessage(Messages.n.replace("@home", args[0]));
            return false;
        }
        dbManager.setHome(p.getName(), args[0], dbManager.getHomeSerialized(p.getName(), args[0]), "1");
        p.sendMessage(Messages.o.replace("@home", args[0]));
        return false;
    }
}
