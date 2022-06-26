// 
// Decompiled by Procyon v0.5.36
// 

package de.kinglol12345.fuel;

import org.bukkit.Material;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.util.Vector;
import org.bukkit.entity.Player;
import de.kinglol12345.Messages.Messages;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import de.kinglol12345.main.main;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.Listener;

public class ElytraUse implements Listener
{
    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onSpeedup(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (!main.Enable) {
            return;
        }
        if (!player.hasPermission("ElytraPlus.use")) {
            return;
        }
        if (main.disabledWorlds.contains(player.getLocation().getWorld().getName())) {
            return;
        }
        if (!main.nmsAccess.isPlayerGliding(player)) {
            return;
        }
        if (!isElytraWeared(player)) {
            return;
        }
        if (!main.AllElytra && !main.nmsAccess.isElytraPlus(player.getInventory().getChestplate())) {
            return;
        }
        if (!player.isSneaking()) {
            return;
        }
        if (main.Fuel && player.getGameMode() != GameMode.CREATIVE && Elytra.getFuel(player.getInventory().getChestplate()) <= 0) {
            return;
        }
        if (player.hasPermission("ElytraPlus.changeparticle")) {
            main.nmsAccess.playParticle(player, false);
        }
        else {
            main.nmsAccess.playParticle(player, true);
        }
        final Vector v = player.getVelocity();
        if (v.length() > main.MaxSpeed) {
            return;
        }
        if (main.Fuel && player.getGameMode() != GameMode.CREATIVE) {
            final ElytraFillEvent e = new ElytraFillEvent(player, player.getInventory().getChestplate(), Elytra.getFuel(player.getInventory().getChestplate()), Elytra.getFuel(player.getInventory().getChestplate()) - main.FuelPerMove);
            Bukkit.getPluginManager().callEvent((Event)e);
            Elytra.setFuel(e.getElytra(), e.getNewFuel());
            main.nmsAccess.sendLabel(player, Messages.fuel + Elytra.getFuel(player.getInventory().getChestplate()));
        }
        player.setVelocity(player.getVelocity().multiply(main.Acceleration));
    }
    
    static boolean isElytraWeared(final Player player) {
        return player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getType() == Material.ELYTRA && player.getInventory().getChestplate().getDurability() < 431;
    }
}
