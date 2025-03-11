package me.zbg.PDGHHomes.settings;

import me.zbg.PDGHHomes.*;

public class Settings
{
    public static Integer cooldown;
    public static Integer maxhomes;
    
    static {
        Settings.cooldown = 3;
        Settings.maxhomes = 15;
    }
    
    public static void LoadSettings() {
        Settings.cooldown = Main.getInstance().getConfig().getInt("Cooldown");
        Settings.maxhomes = Main.getInstance().getConfig().getInt("MaxHomes");
    }
}
