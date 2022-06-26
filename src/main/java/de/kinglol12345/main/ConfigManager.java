// 
// Decompiled by Procyon v0.5.36
// 

package de.kinglol12345.main;

import java.util.List;
import de.kinglol12345.fuel.Elytra;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager
{
    public FileConfiguration config;
    
    public ConfigManager() {
        this.config = main.plugin.getConfig();
        this.getConfig();
    }
    
    private void getConfig() {
        main.Enable = this.config.getBoolean("Enable");
        main.Acceleration = this.config.getDouble("Acceleration");
        main.MaxSpeed = this.config.getDouble("Max-Speed");
        main.Particle = this.config.getString("Standard-Particle");
        main.disabledWorlds = (List<String>)this.config.getStringList("disabled-worlds");
        main.AllElytra = this.config.getBoolean("All-Elytras");
        main.Fuel = this.config.getBoolean("Fuel");
        main.FuelPerMove = this.config.getInt("Fuel_Use") / 7;
        main.Shiftstart = this.config.getBoolean("shift-start");
        Elytra.MAX_FUEL = this.config.getInt("Max_Fuel");
        main.StartUp_Cost = this.config.getInt("StartUp_Cost");
        main.nmsAccess.loadParticle(this.config);
    }
}
