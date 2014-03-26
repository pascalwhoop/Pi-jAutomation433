package com.opitz.iotprototype.entities;

import javax.persistence.*;

/**
 * A ELRO Power Plug has 2 times 5 DIP switches which can either be set to 0 or 1.
 * The first 5 DIP's are a "system" code and the second 5 are a "unit" code.
 * However it doesn't matter how you encode it, all that matters is that you don't have
 * more than 1 plug with the same code (unless you explicitly intend to do so)
 *
 * One could use the System codes to represent seperate rooms but this can also be abstracted in the middleware.
 */

@Entity
public class ElroPowerPlug {

    private Integer id;
    private String label;
    private String switchID;
    private String groupID;
    private boolean lastKnownState;


    //Getter Setter

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic(optional=false)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Basic(optional=false)
    public String getSwitchID() {
        return switchID;
    }

    public void setSwitchID(String switchID) {
        this.switchID = switchID;
    }

    @Basic(optional=false)
    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    @Basic(optional = true)
    public boolean isLastKnownState() {
        return lastKnownState;
    }

    public void setLastKnownState(boolean lastKnownState) {
        this.lastKnownState = lastKnownState;
    }
}
