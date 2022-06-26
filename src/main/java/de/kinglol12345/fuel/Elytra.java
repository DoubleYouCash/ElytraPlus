// 
// Decompiled by Procyon v0.5.36
// 

package de.kinglol12345.fuel;

import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;
import java.util.Arrays;
import de.kinglol12345.Messages.Messages;
import de.kinglol12345.main.main;
import org.bukkit.inventory.ItemStack;

public class Elytra
{
    public static String NAME;
    public static String FUEL;
    public static int MAX_FUEL;
    
    public Elytra(final ItemStack arg0) {
        main.nmsAccess.createInt(arg0, Elytra.FUEL, getFuel(arg0));
    }
    
    public static void update(final ItemStack arg0) {
        if (main.nmsAccess.isElytraPlus(arg0)) {
            final int fuel = getFuel(arg0);
            final ItemMeta meta = arg0.getItemMeta();
            arg0.setItemMeta(meta);
            if (main.Fuel) {
                meta.setLore((List)Arrays.asList(Messages.fuel + fuel));
            }
            else if (meta.getLore() != null) {
                meta.getLore().clear();
            }
            meta.setDisplayName(Messages.displayname);
            arg0.setItemMeta(meta);
        }
    }
    
    public static int getFuel(final ItemStack arg0) {
        if (main.nmsAccess.isElytraPlus(arg0)) {
            return main.nmsAccess.getInt(arg0, Elytra.FUEL, 0);
        }
        return 0;
    }
    
    public static void setFuel(final ItemStack arg0, final int arg1) {
        if (arg1 <= 0) {
            main.nmsAccess.setInt(arg0, 0, Elytra.FUEL);
        }
        if (arg1 >= Elytra.MAX_FUEL) {
            main.nmsAccess.setInt(arg0, Elytra.MAX_FUEL, Elytra.FUEL);
        }
        if (arg1 > 0 && arg1 < Elytra.MAX_FUEL) {
            main.nmsAccess.setInt(arg0, arg1, Elytra.FUEL);
        }
    }
    
    static {
        Elytra.NAME = "elytraplus";
        Elytra.FUEL = "fuel";
    }
}
