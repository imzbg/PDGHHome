package me.zbg.PDGHHomes.settings;

import me.zbg.PDGHHomes.*;
import java.io.*;
import org.bukkit.configuration.file.*;

public class Messages
{
    public static String a;
    public static String b;
    public static String c;
    public static String d;
    public static String e;
    public static String g;
    public static String h;
    public static String i;
    public static String j;
    public static String k;
    public static String l;
    public static String n;
    public static String o;
    public static String p;
    public static String q;
    public static String r;
    public static String s;
    public static String t;
    public static String u;
    public static String v;
    public static String w;
    public static String x;
    public static String y;
    public static String z;
    
    public static void LoadMessages() {
        final File f = new File(Main.getInstance().getDataFolder().getAbsolutePath(), "mensagens.yml");
        if (!f.exists()) {
            Main.getInstance().saveResource("mensagens.yml", true);
        }
        final YamlConfiguration m = YamlConfiguration.loadConfiguration(f);
        Messages.a = format(m.getString("CommandUse.DeleteHome"));
        Messages.b = format(m.getString("Player.NaoTem"));
        Messages.c = format(m.getString("CommandSucessful.DeleteHome"));
        Messages.d = format(m.getString("CommandUse.Home"));
        Messages.e = format(m.getString("Player.HomeTeleport"));
        Messages.g = format(m.getString("Player.HomeTeleportDelay"));
        Messages.h = format(m.getString("OtherPlayer.NaoTem"));
        Messages.i = format(m.getString("OtherPlayer.NaoPublica"));
        Messages.j = format(m.getString("OtherPlayer.HomeTeleport"));
        Messages.k = format(m.getString("Player.NenhumaHome"));
        Messages.l = format(m.getString("CommandUse.Loja"));
        Messages.n = format(m.getString("CommandLoja.JaEsta"));
        Messages.o = format(m.getString("CommandLoja.Sucesso"));
        Messages.p = format(m.getString("CommandUse.RemoveLoja"));
        Messages.q = format(m.getString("CommandRemoveLoja.JaEsta"));
        Messages.r = format(m.getString("CommandRemoveLoja.Sucesso"));
        Messages.s = format(m.getString("CommandUse.SetHome"));
        Messages.t = format(m.getString("SetHome.Maxhomes"));
        Messages.u = format(m.getString("SetHome.Sucesso"));
        Messages.v = format(m.getString("ListHomes.Prefixo"));
        Messages.w = format(m.getString("ListHomes.Separator"));
        Messages.x = format(m.getString("ListHomes.ObsMessage"));
        Messages.y = format(m.getString("ListHomes.NormalHomesColor"));
        Messages.z = format(m.getString("ListHomes.PublicHomesColor"));
    }
    
    public static String format(final String a) {
        return a.replace("&", "§");
    }
}
