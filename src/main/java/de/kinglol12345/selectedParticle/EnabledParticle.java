// 
// Decompiled by Procyon v0.5.36
// 

package de.kinglol12345.selectedParticle;

import de.kinglol12345.main.main;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;

public class EnabledParticle
{
    private String ep;
    private int ItemID;
    private byte data;
    public static List<EnabledParticle> epList;
    public static final String eparticle;
    
    public EnabledParticle(final String ep, final int ItemID, final byte data) {
        this.ep = ep;
        this.ItemID = ItemID;
        this.data = data;
        EnabledParticle.epList.add(this);
    }
    
    public String getEp() {
        return this.ep;
    }
    
    public int getItemID() {
        return this.ItemID;
    }
    
    public byte getData() {
        return this.data;
    }
    
    public static EnabledParticle getByName(final String name) {
        for (final EnabledParticle e : EnabledParticle.epList) {
            if (e.ep.equals(name)) {
                return e;
            }
        }
        return null;
    }
    
    static {
        EnabledParticle.epList = (List<EnabledParticle>)Lists.newArrayList();
        eparticle = main.Particle;
    }
}
