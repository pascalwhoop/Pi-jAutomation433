package com.opitz.genericdatamodel.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * User: Pascal
 * Date: 29.05.13
 * Time: 16:52
 */

@Entity
public class ObjectValue {

    private Integer id;
    private String value;
    private Date minDate;
    private Date maxDate;
    private ObjectTypeAttribute objectAttribute;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String wert) {
        this.value = wert;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ObjectAttribute_fk")
    public ObjectTypeAttribute getObjectAttribute() {
        return objectAttribute;
    }

    public void setObjectAttribute(ObjectTypeAttribute objectAttribute) {
        this.objectAttribute = objectAttribute;
    }
}
