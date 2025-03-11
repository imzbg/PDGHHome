package me.zbg.PDGHHomes.commands;

import org.bukkit.command.defaults.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.zbg.PDGHHomes.settings.*;
import me.zbg.PDGHHomes.database.*;

public class DeleteHome extends BukkitCommand
{
    public DeleteHome(final String home) {
        super(home);
    }
    
    public boolean execute(final CommandSender sender, final String alias, final String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        final Player p = (Player)sender;
        if (args.length != 1) {
            p.sendMessage(Messages.a);
            return false;
        }
        if (!dbManager.homeExists(p.getName(), args[0])) {
            p.sendMessage(Messages.b.replace("@home", args[0]));
            return false;
        }
        dbManager.deleteHome(p.getName(), args[0]);
        p.sendMessage(Messages.c.replace("@home", args[0]));
        return false;
    }
}
