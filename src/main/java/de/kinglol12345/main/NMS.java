// 
// Decompiled by Procyon v0.5.36
// 

package de.kinglol12345.main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

public interface NMS
{
    void sendLabel(final Player p0, final String p1);
    
    void playParticle(final Player p0, final boolean p1);
    
    void createInt(final ItemStack p0, final String p1, final int p2);
    
    void setInt(final ItemStack p0, final int p1, final String p2);
    
    boolean isElytraPlus(final ItemStack p0);
    
    int getInt(final ItemStack p0, final String p1, final int p2);
    
    boolean fuel(final ItemStack p0, final int p1);
    
    boolean isPlayerGliding(final Player p0);
    
    void loadParticle(final FileConfiguration p0);
    
    void play(final Player p0, final Effect p1);
    
    boolean hasParticle(final String p0);
}
