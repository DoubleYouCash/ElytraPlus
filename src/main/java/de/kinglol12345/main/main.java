// 
// Decompiled by Procyon v0.5.36
// 

package de.kinglol12345.main;

import java.util.Arrays;
import java.util.Iterator;
import org.bukkit.entity.Player;
import java.lang.reflect.InvocationTargetException;
import de.kinglol12345.fuel.ElytraUse;
import de.kinglol12345.fuel.ElytraListener;
import de.kinglol12345.selectedParticle.SPConfig;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import de.kinglol12345.Messages.Messages;
import de.kinglol12345.selectedParticle.SPInventory;
import java.util.List;
import de.kinglol12345.selectedParticle.CommandEP;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin
{
    public static Plugin plugin;
    public static CommandEP cmdEP;
    public static String version;
    public static boolean Enable;
    public static double Acceleration;
    public static double MaxSpeed;
    public static String Particle;
    public static boolean AllElytra;
    public static boolean Fuel;
    public static int FuelPerMove;
    public static boolean Shiftstart;
    public static List<String> disabledWorlds;
    public static int StartUp_Cost;
    public static SPInventory spinv;
    public static NMS nmsAccess;
    
    public void onEnable() {
        if (this.setUp()) {
            main.plugin = (Plugin)this;
            main.cmdEP = new CommandEP();
            this.saveDefaultConfig();
            new ConfigManager();
            new Messages().create();
            this.getCommand("elytraplus").setExecutor((CommandExecutor)main.cmdEP);
            this.getCommand("ep").setExecutor((CommandExecutor)main.cmdEP);
            main.spinv = new SPInventory();
            Bukkit.getPluginManager().registerEvents((Listener)main.spinv, (Plugin)this);
            Bukkit.getPluginManager().registerEvents((Listener)new SPConfig(), (Plugin)this);
            Bukkit.getPluginManager().registerEvents((Listener)new SPInventory(), (Plugin)this);
            Bukkit.getPluginManager().registerEvents((Listener)new ElytraListener(), (Plugin)this);
            Bukkit.getPluginManager().registerEvents((Listener)new ElytraUse(), (Plugin)this);
            this.loadAllOnlinePlayer();
            if (main.Fuel) {
                Bukkit.getPluginManager().registerEvents((Listener)new ElytraListener(), (Plugin)this);
            }
            this.getLogger().info("ElytraPlus setup was successful!");
            this.getLogger().info("The plugin setup process is complete!");
        }
        else {
            this.getLogger().severe("Failed to setup ElytraPlus!");
            this.getLogger().severe("Your server version is not compatible with this plugin!");
            Bukkit.getPluginManager().disablePlugin((Plugin)this);
        }
    }
    
    public void onDisable() {
    }
    
    private boolean setUp() {
        final String packageName = Bukkit.getServer().getClass().getPackage().getName();
        final String[] packageSplit = packageName.split("\\.");
        final String version = packageSplit[packageSplit.length - 1];
        try {
            final Class<?> nmsClass = Class.forName("de.kinglol12345.NMS." + version);
            if (NMS.class.isAssignableFrom(nmsClass)) {
                main.nmsAccess = (NMS)nmsClass.getConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
            }
            else {
                System.out.println("Something went wrong, please note down version and contact author version:" + version);
                this.setEnabled(false);
            }
        }
        catch (ClassNotFoundException e6) {
            System.out.println("No compatible Server Version");
            System.out.println("Plugin disabled");
            this.setEnabled(false);
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        catch (InvocationTargetException e3) {
            e3.printStackTrace();
        }
        catch (NoSuchMethodException e4) {
            e4.printStackTrace();
        }
        catch (SecurityException e5) {
            e5.printStackTrace();
        }
        return this.isEnabled();
    }
    
    private void loadAllOnlinePlayer() {
        for (final Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("elytraplus.changeparticle")) {
                SPConfig.loadPlayer(p.getUniqueId().toString());
            }
        }
    }
    
    static {
        main.disabledWorlds = Arrays.asList("disabled_world1", "disabled_world2", "disabled_world3");
    }
}
