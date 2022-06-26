// 
// Decompiled by Procyon v0.5.36
// 

package de.kinglol12345.fuel;

import com.google.common.collect.Lists;
import java.util.Iterator;
import org.bukkit.Material;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import de.kinglol12345.Messages.Messages;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.Player;

public class ElytraFillInventory
{
    private Player p;
    private Inventory inv;
    private ItemStack elytra;
    public static List<ElytraFillInventory> liste;
    
    public ElytraFillInventory(final Player p, final ItemStack elytra) {
        this.p = p;
        this.elytra = elytra;
        this.createInv();
        this.openInv();
        ElytraFillInventory.liste.add(this);
    }
    
    private void openInv() {
        this.p.openInventory(this.inv);
    }
    
    private void createInv() {
        this.inv = Bukkit.createInventory((InventoryHolder)this.p, 9, Messages.fillInventory);
        for (int i = 1; i < 9; ++i) {
            this.inv.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7));
        }
    }
    
    public Inventory getInv() {
        return this.inv;
    }
    
    public Player getPlayer() {
        return this.p;
    }
    
    public ItemStack getElytra() {
        return this.elytra;
    }
    
    public static ElytraFillInventory getByPlayer(final Player p) {
        for (final ElytraFillInventory e : ElytraFillInventory.liste) {
            if (e.getPlayer().equals(p)) {
                return e;
            }
        }
        return null;
    }
    
    static {
        ElytraFillInventory.liste = (List<ElytraFillInventory>)Lists.newArrayList();
    }
}
