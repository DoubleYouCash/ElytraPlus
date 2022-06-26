// 
// Decompiled by Procyon v0.5.36
// 

package de.kinglol12345.selectedParticle;

import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.configuration.file.YamlConfiguration;
import de.kinglol12345.main.main;
import org.bukkit.configuration.file.FileConfiguration;
import java.io.File;
import org.bukkit.event.Listener;

public class SPConfig implements Listener
{
    public static File selected;
    public static FileConfiguration selectedCFG;
    
    public SPConfig() {
        SPConfig.selected = new File(main.plugin.getDataFolder(), "SelectedParticle.yml");
        SPConfig.selectedCFG = (FileConfiguration)YamlConfiguration.loadConfiguration(SPConfig.selected);
    }
    
    public static void loadPlayer(final String uuid) {
        if (isSet(uuid)) {
            final String name = SPConfig.selectedCFG.getString("selected." + uuid + ".Particle");
            final EnabledParticle ep = EnabledParticle.getByName(name);
            String e;
            if (ep == null) {
                e = EnabledParticle.eparticle;
            }
            else {
                e = ep.getEp();
            }
            new SelectedParticle(uuid, e);
        }
    }
    
    public static boolean isSet(final String uuid) {
        return SPConfig.selectedCFG.isSet("selected." + uuid);
    }
    
    public static void setPlayer(final SelectedParticle sp) {
        final String uuid = sp.getUUID();
        SPConfig.selectedCFG.set("selected." + uuid + ".Particle", (Object)sp.getParticle());
        saveConfig();
    }
    
    public static void saveConfig() {
        try {
            SPConfig.selectedCFG.save(SPConfig.selected);
        }
        catch (Exception ex) {}
    }
    
    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final String uuid = event.getPlayer().getUniqueId().toString();
        if (isSet(uuid)) {
            loadPlayer(uuid);
        }
    }
    
    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        final String uuid = event.getPlayer().getUniqueId().toString();
        if (SelectedParticle.getSelectetParticle(uuid) != null) {
            SelectedParticle.eplist.remove(SelectedParticle.getSelectetParticle(uuid));
        }
    }
}
