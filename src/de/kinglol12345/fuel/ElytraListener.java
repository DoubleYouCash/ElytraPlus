// 
// Decompiled by Procyon v0.5.36
// 

package de.kinglol12345.fuel;

import org.bukkit.inventory.Inventory;
import java.util.Iterator;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.Material;
import de.kinglol12345.Messages.Messages;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.GameMode;
import de.kinglol12345.main.main;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.Listener;

public class ElytraListener implements Listener
{
    public ElytraListener() {
        this.timer();
    }
    
    @EventHandler
    public void onSneak(final PlayerToggleSneakEvent e) {
        final Player p = e.getPlayer();
        if (p.isSneaking()) {
            ElytraSpeedUp.remove(p);
        }
        else if (testStartUp(p)) {
            new ElytraSpeedUp(p);
        }
    }
    
    @EventHandler
    public void onDmg3(final EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            final Player player = (Player)e.getEntity();
            if (e.getCause().equals((Object)EntityDamageEvent.DamageCause.FLY_INTO_WALL)) {
                if (!player.hasPermission("ElytraPlus.use")) {
                    return;
                }
                if (main.disabledWorlds.contains(player.getLocation().getWorld().getName())) {
                    return;
                }
                if (!ElytraUse.isElytraWeared(player)) {
                    return;
                }
                if (!main.AllElytra && !main.nmsAccess.isElytraPlus(player.getInventory().getChestplate())) {
                    return;
                }
                if (player.isGliding()) {
                    e.setCancelled(true);
                }
            }
        }
    }
    
    public static boolean testStartUp(final Player p) {
        return main.Enable && main.Shiftstart && ElytraUse.isElytraWeared(p) && !main.disabledWorlds.contains(p.getLocation().getWorld().getName()) && !main.nmsAccess.isPlayerGliding(p) && (main.AllElytra || main.nmsAccess.isElytraPlus(p.getInventory().getChestplate())) && (!main.Fuel || p.getGameMode() == GameMode.CREATIVE || Elytra.getFuel(p.getInventory().getChestplate()) >= main.StartUp_Cost);
    }
    
    @EventHandler
    public void invClick(final InventoryClickEvent e) {
        if (e.getClickedInventory() == null) {
            return;
        }
        if (e.getCurrentItem() == null) {
            return;
        }
        if (e.getWhoClicked() instanceof Player) {
            if (e.getInventory().getTitle().equals(Messages.fillInventory) && e.isShiftClick()) {
                e.setCancelled(true);
            }
            if (e.getClickedInventory().getTitle().equals(Messages.fillInventory)) {
                if (e.getCurrentItem().getType().equals((Object)Material.BUCKET)) {
                    return;
                }
                if (e.getSlot() != 0) {
                    e.setCancelled(true);
                    return;
                }
                if (getPower(e.getCursor()) != 0 || getPower(e.getCurrentItem()) != 0) {
                    return;
                }
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onInteract(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && p.getItemInHand().getType().equals((Object)Material.ELYTRA) && main.Fuel && main.nmsAccess.isElytraPlus(p.getItemInHand())) {
            if (p.getInventory().getChestplate() == null || p.getInventory().getChestplate().getType() == Material.AIR) {
                p.getInventory().setChestplate(new ItemStack(Material.AIR));
            }
            e.setCancelled(true);
            new ElytraFillInventory(p, e.getItem());
            p.updateInventory();
        }
    }
    
    @EventHandler
    public void onClose(final InventoryCloseEvent e) {
        if (e.getPlayer() instanceof Player) {
            final Player p = (Player)e.getPlayer();
            if (e.getInventory() == null) {
                return;
            }
            if (e.getInventory().getTitle().equalsIgnoreCase(Messages.fillInventory) && ElytraFillInventory.getByPlayer(p) != null) {
                final ItemStack is = e.getInventory().getItem(0);
                ElytraFillInventory.liste.remove(ElytraFillInventory.getByPlayer(p));
                if (is == null) {
                    return;
                }
                p.getInventory().addItem(new ItemStack[] { is });
            }
        }
    }
    
    public void timer() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main.plugin, (Runnable)new Runnable() {
            @Override
            public void run() {
                for (final ElytraFillInventory e : ElytraFillInventory.liste) {
                    final Player p = e.getPlayer();
                    final Inventory inv = e.getInv();
                    if (inv == null) {
                        return;
                    }
                    if (p == null) {
                        return;
                    }
                    if (inv.getItem(0) == null) {
                        return;
                    }
                    if (ElytraListener.getPower(inv.getItem(0)) <= 0) {
                        continue;
                    }
                    final int fuel = ElytraListener.getPower(inv.getItem(0));
                    final ItemStack elytra = e.getElytra();
                    final int oldFuel = Elytra.getFuel(elytra);
                    final ItemStack is = inv.getItem(0);
                    if (oldFuel >= Elytra.MAX_FUEL) {
                        continue;
                    }
                    final ElytraFillEvent ef = new ElytraFillEvent(p, elytra, oldFuel, oldFuel + fuel);
                    Bukkit.getPluginManager().callEvent((Event)ef);
                    if (!ef.isCancelled()) {
                        if (oldFuel + fuel > Elytra.MAX_FUEL) {
                            Elytra.setFuel(elytra, Elytra.MAX_FUEL);
                        }
                        else {
                            Elytra.setFuel(elytra, ef.getNewFuel());
                        }
                        if (is.getType().equals((Object)Material.LAVA_BUCKET)) {
                            inv.setItem(0, new ItemStack(Material.BUCKET));
                        }
                        else {
                            is.setAmount(is.getAmount() - 1);
                            inv.setItem(0, is);
                        }
                    }
                    p.updateInventory();
                }
            }
        }, 0L, 4L);
    }
    
    public static int getPower(final ItemStack arg0) {
        switch (arg0.getType()) {
            case COAL: {
                return 80;
            }
            case BLAZE_ROD: {
                return 120;
            }
            case COAL_BLOCK: {
                return 800;
            }
            case LAVA_BUCKET: {
                return 1000;
            }
            case WOOD: {
                return 15;
            }
            case LOG: {
                return 15;
            }
            case LOG_2: {
                return 15;
            }
            case SAPLING: {
                return 5;
            }
            default: {
                return 0;
            }
        }
    }
}
