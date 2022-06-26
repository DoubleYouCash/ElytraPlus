// 
// Decompiled by Procyon v0.5.36
// 

package de.kinglol12345.fuel;

import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ElytraFillEvent extends Event
{
    private final Player player;
    private final ItemStack jetPack;
    private final int oldFuel;
    private int newFuel;
    boolean cancel;
    private static final HandlerList handlers;
    
    public ElytraFillEvent(final Player arg0, final ItemStack arg1, final int arg2, final int arg3) {
        this.cancel = false;
        this.player = arg0;
        this.jetPack = arg1;
        this.oldFuel = arg2;
        this.newFuel = arg3;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public ItemStack getElytra() {
        return this.jetPack;
    }
    
    public int getNewFuel() {
        return this.newFuel;
    }
    
    public void setNewFuel(final int arg0) {
        this.newFuel = arg0;
    }
    
    public int getOldFuel() {
        return this.oldFuel;
    }
    
    public boolean isCancelled() {
        return this.cancel;
    }
    
    public void setCancelled(final boolean arg0) {
        this.cancel = arg0;
    }
    
    public static HandlerList getHandlerList() {
        return ElytraFillEvent.handlers;
    }
    
    public HandlerList getHandlers() {
        return ElytraFillEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
