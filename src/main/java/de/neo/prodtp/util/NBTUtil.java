package de.neo.prodtp.util;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.UUID;

public class NBTUtil {

    public static Path getPlayerDataFile(UUID playerUUID) {
        String worldName = "world";
        try {
            InputStream is = Files.newInputStream(Paths.get("server.properties"));
            Properties properties = new Properties();
            properties.load(is);
            worldName = properties.getProperty("level-name");
            is.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return Paths.get(Bukkit.getWorldContainer().getAbsolutePath(), worldName, "playerdata", playerUUID.toString() + ".dat");
    }

    public static Location getLastLocation(UUID playerUUID) {
        NBTFile nbtFile = null;
        try {
            nbtFile = new NBTFile(getPlayerDataFile(playerUUID).toFile());
        }catch (Exception e) {
            e.printStackTrace();
        }
        if(nbtFile == null) throw new RuntimeException("Could not load player data file for player " + playerUUID.toString());
        String world = nbtFile.getString("Dimension");
        NBTCompound location = nbtFile.getCompound("Pos");
        double x = location.getDouble("0");
        double y = location.getDouble("1");
        double z = location.getDouble("2");
        return new Location(Bukkit.getWorld(getWorldName(world)), x, y, z);
    }

    public static String getWorldName(String nbtValue) {
        nbtValue = nbtValue.replace("minecraft:", "");
        if(nbtValue.equalsIgnoreCase("overworld")) return "world";
        if(nbtValue.equalsIgnoreCase("the_nether")) return "world_nether";
        if(nbtValue.equalsIgnoreCase("the_end")) return "world_the_end";
        return nbtValue;
    }

}
