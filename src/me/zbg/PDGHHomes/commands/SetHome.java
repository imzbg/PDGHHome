package me.zbg.PDGHHomes.commands;

import org.bukkit.command.defaults.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.zbg.PDGHHomes.settings.*;
import me.zbg.PDGHHomes.database.*;

public class SetHome extends BukkitCommand
{
    public SetHome(final String home) {
        super(home);
    }
    
    public boolean execute(final CommandSender sender, final String alias, final String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        final Player p = (Player)sender;
        if (args.length != 1) {
            p.sendMessage(Messages.s);
            return false;
        }
        if (!p.hasPermission("pdgh.vip") && dbManager.homesByPlayer(p.getName()) >= Settings.maxhomes) {
            p.sendMessage(Messages.t.replace("@limite", String.valueOf(Settings.maxhomes)));
            return false;
        }
        dbManager.setHome(p.getName(), args[0], LocationSerializer.serializeLocation(p.getLocation()), "0");
        p.sendMessage(Messages.u.replace("@home", args[0]));
        return false;
    }
}
