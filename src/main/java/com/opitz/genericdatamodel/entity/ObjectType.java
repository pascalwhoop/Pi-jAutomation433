package com.opitz.genericdatamodel.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * User: Pascal
 * Date: 29.05.13
 * Time: 16:24
 */

@Entity
public class ObjectType {

    private Integer id;
    private String label;
    private Set<ObjectTypeAttribute> attributes = new HashSet();


    //GETTER SETTER
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic(optional = false)
    @Column(unique = true)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name= "objectAttribute_fk")
    public Set<ObjectTypeAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<ObjectTypeAttribute> attributes) {
        this.attributes = attributes;
    }
}
