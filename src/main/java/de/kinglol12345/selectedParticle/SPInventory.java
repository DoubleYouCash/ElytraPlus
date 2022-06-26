// 
// Decompiled by Procyon v0.5.36
// 

package de.kinglol12345.selectedParticle;

import java.util.ArrayList;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.inventory.InventoryClickEvent;
import java.util.Iterator;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import de.kinglol12345.Messages.Messages;
import org.bukkit.entity.Player;
import java.util.List;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.Listener;

public class SPInventory implements Listener
{
    public static Inventory inv;
    public static List<Player> player;
    public static int i;
    
    public SPInventory() {
        createInv();
    }
    
    public static void createInv() {
        SPInventory.inv = Bukkit.createInventory((InventoryHolder)null, getSize(EnabledParticle.epList.size() + 1), Messages.selectInventory);
        for (final EnabledParticle e : EnabledParticle.epList) {
            if (e.getEp() != null) {
                addItem(e.getItemID(), e.getData(), e.getEp());
            }
        }
    }
    
    public static void openInv(final Player p) {
        p.openInventory(SPInventory.inv);
        if (!SPInventory.player.contains(p)) {
            SPInventory.player.add(p);
        }
    }
    
    @EventHandler
    public void onClick(final InventoryClickEvent e) {
        if (e.getClickedInventory() == null) {
            return;
        }
        if (e.getClickedInventory().getName() == Messages.selectInventory) {
            e.setCancelled(true);
            final Player p = (Player)e.getWhoClicked();
            final ItemStack is = e.getCurrentItem();
            if (is == null) {
                return;
            }
            if (p.hasPermission("elytraplus.changeparticle")) {
                if (!is.hasItemMeta()) {
                    return;
                }
                if (!is.getItemMeta().hasDisplayName()) {
                    return;
                }
                String name = is.getItemMeta().getDisplayName();
                name = name.substring(2);
                final EnabledParticle ep = EnabledParticle.getByName(name);
                if (ep == null) {
                    return;
                }
                setParticle(p, ep);
                if (SPInventory.player.contains(p)) {
                    p.sendMessage(Messages.prefix + " " + Messages.newParticle + name);
                    SPInventory.player.remove(p);
                }
                p.closeInventory();
            }
        }
    }
    
    public static void setParticle(final Player p, final EnabledParticle ep) {
        if (SelectedParticle.getSelectetParticle(p.getUniqueId().toString()) == null) {
            final SelectedParticle sp = new SelectedParticle(p.getUniqueId().toString(), ep.getEp());
            SPConfig.setPlayer(sp);
        }
        else {
            final SelectedParticle sp = SelectedParticle.getSelectetParticle(p.getUniqueId().toString());
            sp.setParticle(ep.getEp());
        }
    }
    
    private static void addItem(final int id, final byte data, final String name) {
        final ItemStack is = new ItemStack(id, 1, (short)data);
        final ItemMeta im = is.getItemMeta();
        im.setDisplayName("§c" + name);
        is.setItemMeta(im);
        SPInventory.inv.addItem(new ItemStack[] { is });
    }
    
    private static int getSize(final int size) {
        return (size <= 9) ? 9 : ((size <= 18) ? 18 : ((size <= 27) ? 27 : ((size <= 36) ? 36 : ((size <= 45) ? 45 : ((size <= 54) ? 54 : ((size <= 63) ? 63 : ((size <= 72) ? 72 : ((size <= 81) ? 81 : ((size <= 90) ? 90 : ((size <= 99) ? 99 : ((size <= 108) ? 108 : ((size <= 117) ? 117 : 126))))))))))));
    }
    
    static {
        SPInventory.player = new ArrayList<Player>();
        SPInventory.i = 0;
    }
}
