package me.zbg.PDGHHomes;

import org.bukkit.plugin.java.*;
import me.zbg.PDGHHomes.database.*;
import org.bukkit.plugin.*;
import org.bukkit.command.*;
import me.zbg.PDGHHomes.commands.*;
import me.zbg.PDGHHomes.settings.*;
import java.sql.*;
import java.io.*;

public class Main extends JavaPlugin
{
    public static SQL sql;
    public static Main instance;
    public static Plugin pl;
    
    public static Main getInstance() {
        return Main.instance;
    }
    
    public static Plugin getPlugin() {
        return Main.pl;
    }
    
    public void onEnable() {
        Main.instance = this;
        ((Main)(Main.pl = (Plugin)this)).saveDefaultConfig();
        CommandRegister.register((Command)new SetHome("sethome"), "sethome");
        CommandRegister.register((Command)new Home("Home"), "home");
        CommandRegister.register((Command)new DeleteHome("deletehome"), "deletehome");
        CommandRegister.register((Command)new DeleteHome("delhome"), "delhome");
        CommandRegister.register((Command)new ListHomes("listhomes"), "listhomes");
        CommandRegister.register((Command)new ListHomes("homes"), "homes");
        CommandRegister.register((Command)new Loja("loja"), "loja");
        CommandRegister.register((Command)new RemoveLoja("removeloja"), "removeloja");
        CommandRegister.register((Command)new RemoveLoja("unloja"), "unloja");
        CommandRegister.register((Command)new RemoveLoja("delloja"), "delloja");
        CommandRegister.register((Command)new HomeAdmin("homeadmin"), "homeadmin");
        Settings.LoadSettings();
        Messages.LoadMessages();
        this.setupSql();
        System.out.println("§3[PDGHHomes]§a Habilitado! §7- §bDesenvolvido por §6zBG§b!");
    }
    
    public void onDisable() {
        try {
            if (!SQL.connection.isClosed()) {
                Main.sql.closeConnection();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void setupSql() {
        if (!this.getConfig().getBoolean("MySQL.Ativar")) {
            final File db = new File(this.getDataFolder().getAbsolutePath(), "pdghhomes.db");
            if (!db.exists()) {
                this.saveResource("pdghhomes.db", true);
            }
        }
        if (this.getConfig().getBoolean("MySQL.Ativar")) {
            final String user = this.getConfig().getString("MySQL.User");
            final String password = this.getConfig().getString("MySQL.Password");
            final String host = this.getConfig().getString("MySQL.Host");
            final String database = this.getConfig().getString("MySQL.Database");
            (Main.sql = new SQL(user, password, host, database, SQL.SQLType.MySQL, this)).startConnection();
        }
        else {
            (Main.sql = new SQL("pdghhomes", this.getDataFolder(), SQL.SQLType.SQLite, this)).startConnection();
        }
    }
}
