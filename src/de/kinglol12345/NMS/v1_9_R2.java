// 
// Decompiled by Procyon v0.5.36
// 

package de.kinglol12345.NMS;

import net.minecraft.server.v1_9_R2.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_9_R2.EnumParticle;
import org.bukkit.configuration.file.FileConfiguration;
import java.lang.reflect.Field;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftItemStack;
import de.kinglol12345.main.main;
import net.minecraft.server.v1_9_R2.NBTBase;
import net.minecraft.server.v1_9_R2.NBTTagCompound;
import de.kinglol12345.fuel.Elytra;
import org.bukkit.inventory.ItemStack;
import de.kinglol12345.main.Effect;
import org.bukkit.Location;
import net.minecraft.server.v1_9_R2.PlayerConnection;
import net.minecraft.server.v1_9_R2.Packet;
import net.minecraft.server.v1_9_R2.PacketPlayOutChat;
import net.minecraft.server.v1_9_R2.IChatBaseComponent;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import de.kinglol12345.selectedParticle.SelectedParticle;
import de.kinglol12345.selectedParticle.EnabledParticle;
import org.bukkit.entity.Player;
import de.kinglol12345.main.NMS;

public class v1_9_R2 implements NMS
{
    @Override
    public void playParticle(final Player p, final boolean normal) {
        if (normal) {
            this.playParticle(p.getLocation(), EnabledParticle.eparticle);
        }
        else if (SelectedParticle.getSelectetParticle(p.getUniqueId().toString()) != null) {
            String e = this.getParticle(SelectedParticle.getSelectetParticle(p.getUniqueId().toString()).getParticle()).name();
            e = ((e == null) ? "NONE" : e);
            if (this.hasParticle(e)) {
                this.playParticle(p.getLocation(), e);
            }
        }
        else {
            this.playParticle(p.getLocation(), EnabledParticle.eparticle);
        }
    }
    
    @Override
    public void sendLabel(final Player p, final String message) {
        final PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
        final PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}"), (byte)2);
        connection.sendPacket((Packet)packet);
    }
    
    public void playParticle(final Location loc, final String ep) {
        new Effect(loc, ep, 0.2, 0.0, 0.2, 0.0, 10).playAll();
    }
    
    @Override
    public void createInt(final ItemStack arg0, final String name, final int anz) {
        final NBTTagCompound TAG = getTag(arg0, true);
        if (TAG != null) {
            TAG.set(Elytra.NAME, (NBTBase)new NBTTagCompound());
        }
        final NBTTagCompound tag = (NBTTagCompound)TAG.get(Elytra.NAME);
        tag.setInt(name, 0);
        Elytra.update(arg0);
    }
    
    @Override
    public void setInt(final ItemStack arg0, final int arg1, final String arg2) {
        if (this.isElytraPlus(arg0)) {
            final NBTTagCompound tag = getTag(arg0, true);
            final NBTTagCompound TAG = (NBTTagCompound)tag.get(Elytra.NAME);
            if (TAG != null) {
                if (TAG.hasKey(arg2)) {
                    TAG.setInt(arg2, arg1);
                    Elytra.update(arg0);
                }
                else {
                    TAG.setInt(arg2, arg1);
                    Elytra.update(arg0);
                }
            }
        }
    }
    
    @Override
    public boolean fuel(final ItemStack arg0, final int power) {
        if (main.nmsAccess.isElytraPlus(arg0)) {
            final NBTTagCompound tag = getTag(arg0, true);
            final NBTTagCompound TAG = (NBTTagCompound)tag.get(Elytra.NAME);
            if (TAG != null) {
                if (!TAG.hasKey(Elytra.FUEL)) {
                    TAG.setInt(Elytra.FUEL, power);
                    Elytra.update(arg0);
                    return true;
                }
                if (TAG.getInt(Elytra.FUEL) <= Elytra.MAX_FUEL - power) {
                    TAG.setInt(Elytra.FUEL, TAG.getInt(Elytra.FUEL) + power);
                    Elytra.update(arg0);
                    return true;
                }
                if (TAG.getInt(Elytra.FUEL) <= Elytra.MAX_FUEL) {
                    Elytra.update(arg0);
                    return false;
                }
                TAG.setInt(Elytra.FUEL, Elytra.MAX_FUEL);
                Elytra.update(arg0);
            }
        }
        return false;
    }
    
    @Override
    public boolean isElytraPlus(final ItemStack arg0) {
        final NBTTagCompound TAG = getTag(arg0, false);
        return TAG != null && TAG.hasKey(Elytra.NAME);
    }
    
    @Override
    public int getInt(final ItemStack arg0, final String arg1, final int arg2) {
        if (this.isElytraPlus(arg0)) {
            final NBTTagCompound tag = getTag(arg0, true);
            final NBTTagCompound TAG = (NBTTagCompound)tag.get(Elytra.NAME);
            if (TAG != null) {
                if (TAG.hasKey(arg1)) {
                    return TAG.getInt(arg1);
                }
                TAG.setInt(arg1, arg2);
                return TAG.getInt(arg1);
            }
        }
        return 0;
    }
    
    public static NBTTagCompound getTag(final ItemStack arg0, final boolean create) {
        net.minecraft.server.v1_9_R2.ItemStack item = null;
        try {
            final Field handle = CraftItemStack.class.getDeclaredField("handle");
            handle.setAccessible(true);
            item = (net.minecraft.server.v1_9_R2.ItemStack)handle.get(arg0);
        }
        catch (Exception e) {
            item = CraftItemStack.asNMSCopy(arg0);
        }
        NBTTagCompound tag = null;
        try {
            if (!item.hasTag()) {
                if (create) {
                    tag = new NBTTagCompound();
                    item.setTag(tag);
                }
            }
            else {
                tag = item.getTag();
            }
        }
        catch (NullPointerException ex) {}
        return tag;
    }
    
    @Override
    public boolean isPlayerGliding(final Player player) {
        return ((CraftPlayer)player).getHandle().cC();
    }
    
    @Override
    public void loadParticle(final FileConfiguration config) {
        for (final EnumParticle ep : EnumParticle.values()) {
            final boolean enabled = config.getBoolean(ep.name() + ".Enabled");
            if (enabled) {
                String Item = config.getString(ep.name() + ".Item");
                if (!Item.contains(":")) {
                    Item += ":0";
                }
                final String[] id = Item.split(":");
                final int ItemID = Integer.parseInt(id[0]);
                final byte data = Byte.parseByte(id[1]);
                new EnabledParticle(ep.name(), ItemID, data);
            }
        }
        new EnabledParticle("NONE", 166, (byte)0);
    }
    
    public PacketPlayOutWorldParticles getPacket(final Effect e) {
        return new PacketPlayOutWorldParticles(this.getParticle(e.Enumparticle), true, (float)e.Location.getX(), (float)e.Location.getY(), (float)e.Location.getZ(), (float)e.DirectionX, (float)e.DirectionY, (float)e.DirectionZ, (float)e.Speed, e.Amount, new int[0]);
    }
    
    @Override
    public boolean hasParticle(final String ep) {
        return !ep.equalsIgnoreCase("NONE") && this.getParticle(ep) != null;
    }
    
    public EnumParticle getParticle(final String ep) {
        for (final EnumParticle e : EnumParticle.values()) {
            if (e.name().equals(ep)) {
                return e;
            }
        }
        return null;
    }
    
    @Override
    public void play(final Player p, final Effect e) {
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)this.getPacket(e));
    }
}
