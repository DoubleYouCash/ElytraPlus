// 
// Decompiled by Procyon v0.5.36
// 

package de.kinglol12345.main;

import java.util.Iterator;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Effect
{
    public Location Location;
    public String Enumparticle;
    public double DirectionX;
    public double DirectionY;
    public double DirectionZ;
    public double Speed;
    public int Amount;
    
    public Effect(final Location loc, final String ep, final int amount) {
        this.Location = loc;
        this.Enumparticle = ep;
        this.DirectionX = 0.0;
        this.DirectionY = 0.0;
        this.DirectionZ = 0.0;
        this.Speed = 0.0;
        this.Amount = amount;
    }
    
    public Effect(final Location loc, final String ep, final double directionx, final double directiony, final double directionz, final double speed, final int amount) {
        this.Location = loc;
        this.Enumparticle = ep;
        this.DirectionX = directionx;
        this.DirectionY = directiony;
        this.DirectionZ = directionz;
        this.Speed = speed;
        this.Amount = amount;
    }
    
    public void playAll() {
        for (final Player p : Bukkit.getOnlinePlayers()) {
            main.nmsAccess.play(p, this);
        }
    }
}
