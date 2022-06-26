// 
// Decompiled by Procyon v0.5.36
// 

package de.kinglol12345.Messages;

import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;
import de.kinglol12345.main.main;
import org.bukkit.configuration.file.FileConfiguration;
import java.io.File;

public class Messages
{
    public static File f;
    public static FileConfiguration config;
    public static String prefix;
    public static String displayname;
    public static String fuel;
    public static String fillInventory;
    public static String noPermissions;
    public static String onUpdate;
    public static String noElytra;
    public static String unknownCmd;
    public static String fuelDisabled;
    public static String onsetFuel;
    public static String noElytraPlus;
    public static String selectInventory;
    public static String newParticle;
    public static String notOnline;
    public static String particleOther;
    public static String unknownParticle;
    
    public void create() {
        Messages.f = new File(main.plugin.getDataFolder(), "messages.yml");
        if (Messages.f.exists()) {
            Messages.config = (FileConfiguration)YamlConfiguration.loadConfiguration(Messages.f);
        }
        else {
            main.plugin.saveResource("messages.yml", false);
            Messages.config = (FileConfiguration)YamlConfiguration.loadConfiguration(Messages.f);
        }
        try {
            Messages.config.save(Messages.f);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.testConfig();
    }
    
    public void testConfig() {
        Messages.config.options().copyDefaults(true);
        Messages.config.set("fuelDisabled", (Object)"&7Fuel is not Enabled!");
        Messages.config.set("notOnline", (Object)"&cThis Player isnt online");
        Messages.config.set("particleOther", (Object)"&aSet &c{particle} &afor &7{player}");
        Messages.config.set("unknownParticle", (Object)"&cUnknow Particle");
        try {
            Messages.config.save(Messages.f);
            this.loadConfig();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadConfig() {
        Messages.displayname = Messages.config.getString("displayname").replace("&", "§");
        Messages.fuel = Messages.config.getString("fuel").replace("&", "§");
        Messages.fillInventory = Messages.config.getString("fillInventory").replace("&", "§");
        Messages.prefix = Messages.config.getString("prefix").replace("&", "§");
        Messages.noPermissions = Messages.config.getString("noPermissions").replace("&", "§");
        Messages.onUpdate = Messages.config.getString("onUpdate").replace("&", "§");
        Messages.noElytra = Messages.config.getString("noElytra").replace("&", "§");
        Messages.unknownCmd = Messages.config.getString("unknownCmd").replace("&", "§");
        Messages.fuelDisabled = Messages.config.getString("fuelDisabled").replace("&", "§");
        Messages.onsetFuel = Messages.config.getString("onsetFuel").replace("&", "§");
        Messages.noElytraPlus = Messages.config.getString("noElytraPlus").replace("&", "§");
        Messages.selectInventory = Messages.config.getString("selectInventory").replace("&", "§");
        Messages.newParticle = Messages.config.getString("newParticle").replace("&", "§");
        Messages.notOnline = Messages.config.getString("notOnline").replace("&", "§");
        Messages.particleOther = Messages.config.getString("particleOther").replace("&", "§");
        Messages.unknownParticle = Messages.config.getString("unknownParticle").replace("&", "§");
    }
}
