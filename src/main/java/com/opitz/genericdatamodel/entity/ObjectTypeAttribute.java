package com.opitz.genericdatamodel.entity;

import javax.persistence.*;

/**
 * User: Pascal
 * Date: 29.05.13
 * Time: 16:32
 */

@Entity
@Table(name = "objecttypeattribute")
public class ObjectTypeAttribute {

    private Integer id;
    private Attribute attribute;
    private boolean required;


    //GETTER SETTER
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    @Basic(optional = false)
    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
