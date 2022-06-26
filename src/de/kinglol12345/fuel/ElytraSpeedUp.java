// 
// Decompiled by Procyon v0.5.36
// 

package de.kinglol12345.fuel;

import java.util.ArrayList;
import java.util.Iterator;
import org.bukkit.util.Vector;
import org.bukkit.event.Event;
import de.kinglol12345.main.main;
import org.bukkit.Bukkit;
import java.util.List;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.entity.Player;

public class ElytraSpeedUp
{
    private Player player;
    private BukkitTask task;
    public static List<ElytraSpeedUp> all;
    
    public ElytraSpeedUp(final Player p) {
        remove(p);
        this.player = p;
        ElytraSpeedUp.all.add(this);
        this.timer();
    }
    
    private void timer() {
        this.task = Bukkit.getScheduler().runTaskLater(main.plugin, (Runnable)new Runnable() {
            @Override
            public void run() {
                if (ElytraSpeedUp.this.player != null && !ElytraSpeedUp.this.player.isFlying()) {
                    final Vector v = ElytraSpeedUp.this.player.getLocation().getDirection();
                    v.multiply(2);
                    v.setY(1);
                    ElytraSpeedUp.this.player.setVelocity(v);
                    Bukkit.getScheduler().runTaskLater(main.plugin, (Runnable)new Runnable() {
                        @Override
                        public void run() {
                            if (ElytraListener.testStartUp(ElytraSpeedUp.this.player)) {
                                ElytraSpeedUp.this.player.setGliding(true);
                                final ElytraFillEvent event = new ElytraFillEvent(ElytraSpeedUp.this.player, ElytraSpeedUp.this.player.getInventory().getChestplate(), Elytra.getFuel(ElytraSpeedUp.this.player.getInventory().getChestplate()), Elytra.getFuel(ElytraSpeedUp.this.player.getInventory().getChestplate()) - main.StartUp_Cost);
                                Bukkit.getPluginManager().callEvent((Event)event);
                                Elytra.setFuel(event.getElytra(), event.getNewFuel());
                            }
                        }
                    }, 5L);
                }
                ElytraSpeedUp.this.remove();
            }
        }, 40L);
    }
    
    public static void remove(final Player p) {
        if (ElytraSpeedUp.all != null) {
            for (final ElytraSpeedUp s : ElytraSpeedUp.all) {
                if (s.player == null) {
                    s.remove();
                }
                else {
                    if (s.player == p) {
                        s.remove();
                        return;
                    }
                    continue;
                }
            }
        }
    }
    
    private void remove() {
        this.task.cancel();
        ElytraSpeedUp.all.remove(this);
    }
    
    static {
        ElytraSpeedUp.all = new ArrayList<ElytraSpeedUp>();
    }
}
