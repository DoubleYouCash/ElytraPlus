// 
// Decompiled by Procyon v0.5.36
// 

package de.kinglol12345.selectedParticle;

import java.util.Iterator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import de.kinglol12345.fuel.ElytraFillEvent;
import de.kinglol12345.main.main;
import de.kinglol12345.fuel.Elytra;
import org.bukkit.Material;
import de.kinglol12345.Messages.Messages;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class CommandEP implements CommandExecutor
{
    public boolean onCommand(final CommandSender cs, final Command cmd, final String label, final String[] args) {
        if (label.equalsIgnoreCase("elytraplus") || label.equalsIgnoreCase("ep")) {
            if (!(cs instanceof Player)) {
                return true;
            }
            final Player p = (Player)cs;
            if (args.length == 0) {
                if (!p.hasPermission("elytraplus.changeparticle")) {
                    p.sendMessage(Messages.prefix + " " + Messages.noPermissions);
                    return true;
                }
                SPInventory.openInv(p);
                return true;
            }
            else {
                if (p.hasPermission("elytraplus.update") && args.length == 1) {
                    if (args[0].equalsIgnoreCase("update")) {
                        if (p.getItemInHand().getType() == Material.ELYTRA) {
                            new Elytra(p.getItemInHand());
                            p.sendMessage(Messages.prefix + " " + Messages.onUpdate);
                        }
                        else {
                            p.sendMessage(Messages.prefix + " " + Messages.noElytra);
                        }
                    }
                    else {
                        p.sendMessage(Messages.prefix + " " + Messages.unknownCmd);
                    }
                }
                if (args.length == 2) {
                    if (p.hasPermission("elytraplus.setfuel") && args[0].equalsIgnoreCase("setFuel")) {
                        if (!main.Fuel) {
                            p.sendMessage(Messages.prefix + " " + Messages.fuelDisabled);
                            return true;
                        }
                        if (p.getItemInHand().getType() == Material.ELYTRA) {
                            final ItemStack is = p.getItemInHand();
                            if (main.nmsAccess.isElytraPlus(is)) {
                                final ElytraFillEvent e = new ElytraFillEvent(p, is, Elytra.getFuel(is), Integer.parseInt(args[1]));
                                Bukkit.getPluginManager().callEvent((Event)e);
                                if (!e.isCancelled()) {
                                    Elytra.setFuel(is, e.getNewFuel());
                                }
                                p.updateInventory();
                                p.sendMessage(Messages.prefix + " " + Messages.onsetFuel);
                            }
                            else {
                                p.sendMessage(Messages.prefix + " " + Messages.noElytraPlus);
                            }
                        }
                        else {
                            p.sendMessage(Messages.prefix + " " + Messages.noElytra);
                        }
                    }
                    if (p.hasPermission("elytraplus.changeparticle.other")) {
                        if (this.isOnlinePlayer(args[0])) {
                            final EnabledParticle ep = EnabledParticle.getByName(args[1].toUpperCase());
                            if (ep == null) {
                                p.sendMessage(Messages.prefix + " " + Messages.unknownParticle);
                                return true;
                            }
                            final Player z = Bukkit.getPlayer(args[0]);
                            SPInventory.setParticle(z, ep);
                            String msg = Messages.particleOther;
                            msg = msg.replace("{player}", z.getName());
                            msg = msg.replace("{particle}", ep.getEp());
                            p.sendMessage(Messages.prefix + " " + msg);
                            return true;
                        }
                        else {
                            p.sendMessage(Messages.prefix + " " + Messages.notOnline);
                        }
                    }
                    else {
                        p.sendMessage(Messages.prefix + " " + Messages.noPermissions);
                    }
                }
            }
        }
        return true;
    }
    
    public boolean isOnlinePlayer(final String name) {
        for (final Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
