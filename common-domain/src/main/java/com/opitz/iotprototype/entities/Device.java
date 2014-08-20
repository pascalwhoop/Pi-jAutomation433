package com.opitz.iotprototype.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.opitz.iotprototype.enums.DeviceAccess;
import com.opitz.iotprototype.enums.DeviceType;
import com.opitz.iotprototype.enums.PowerState;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * created by: OPITZ CONSULTING Deutschland GmbH
 *
 * @author Brokmeier, Pascal
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)


//see here for details  http://stackoverflow.com/questions/14460844/serialize-deserialize-polymorphic-collection-based-on-pojo-property-using-jackso
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "deviceType",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ElroPowerPlug.class, name = "ELRO"),
})

public abstract class Device implements Serializable {

    private Integer id;
    private String label;
    private DeviceType deviceType;
    private PowerState powerState;
    private DeviceAccess deviceAccess;
    private transient Set<User> usersWithAccess = new HashSet<>();

    public Device() {
        deviceAccess = DeviceAccess.PUBLIC;
    }


    // Getter Setter

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "DEVICE_ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic(optional = false)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Basic(optional = false)
    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    @Basic(optional = false)
    public PowerState getPowerState() {
        return powerState;
    }

    public void setPowerState(PowerState powerState) {
        this.powerState = powerState;
    }

    @Basic(optional = false)
    public DeviceAccess getDeviceAccess() {
        return deviceAccess;
    }

    public void setDeviceAccess(DeviceAccess deviceAccess) {
        this.deviceAccess = deviceAccess;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    public Set<User> getUsersWithAccess() {
        return usersWithAccess;
    }

    public void setUsersWithAccess(Set<User> usersWithAccess) {
        this.usersWithAccess = usersWithAccess;
    }
}
