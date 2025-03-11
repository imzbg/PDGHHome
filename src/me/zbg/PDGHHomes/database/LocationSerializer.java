package me.zbg.PDGHHomes.database;

import org.bukkit.*;

public class LocationSerializer
{
    public static String serializeLocation(final Location loc) {
        return String.valueOf(loc.getWorld().getName()) + "@" + loc.getX() + "@" + loc.getY() + "@" + loc.getZ() + "@" + loc.getYaw() + "@" + loc.getPitch();
    }
    
    public static Location deserializeLocation(final String s) {
        final String[] parts = s.split("@");
        final String world = parts[0];
        final double x = Double.parseDouble(parts[1]);
        final double y = Double.parseDouble(parts[2]);
        final double z = Double.parseDouble(parts[3]);
        final float yaw = Float.parseFloat(parts[4]);
        final float pitch = Float.parseFloat(parts[5]);
        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }
}
