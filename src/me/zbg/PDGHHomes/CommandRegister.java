package me.zbg.PDGHHomes;

import org.bukkit.*;
import org.bukkit.command.*;
import java.lang.reflect.*;

public class CommandRegister
{
    public static void register(final Command c, final String commandname) {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            final CommandMap commandMap = (CommandMap)bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(commandname, c);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
