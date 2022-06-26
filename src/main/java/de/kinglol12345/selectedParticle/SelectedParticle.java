// 
// Decompiled by Procyon v0.5.36
// 

package de.kinglol12345.selectedParticle;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;

public class SelectedParticle
{
    private String uuid;
    private String ep;
    public static List<SelectedParticle> eplist;
    
    public SelectedParticle(final String uuid, final String ep) {
        this.uuid = uuid;
        this.ep = ep;
        SelectedParticle.eplist.add(this);
    }
    
    public void setParticle(final String ep) {
        this.ep = ep;
        SPConfig.setPlayer(this);
    }
    
    public String getParticle() {
        if (this.ep == null) {
            return null;
        }
        return this.ep;
    }
    
    public String getUUID() {
        return this.uuid;
    }
    
    public static SelectedParticle getSelectetParticle(final String uuid) {
        if (SelectedParticle.eplist == null) {
            return null;
        }
        for (final SelectedParticle sp : SelectedParticle.eplist) {
            if (uuid.equals(sp.uuid)) {
                return sp;
            }
        }
        return null;
    }
    
    static {
        SelectedParticle.eplist = (List<SelectedParticle>)Lists.newArrayList();
    }
}
