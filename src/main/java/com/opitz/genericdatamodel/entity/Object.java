package com.opitz.genericdatamodel.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: Pascal
 * Date: 29.05.13
 * Time: 16:47
 */
@Entity
public class Object {

    private Integer id;
    private String label;
    private Date minDate;
    private Date maxDate;
    private ObjectType typ;
    private Object parentNode;
    private List<Object> children;
    private Set<ObjectValue> values = new HashSet();


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

    @ManyToOne
    @JoinColumn(name= "objectType_fk")
    public ObjectType getTyp() {
        return typ;
    }

    public void setTyp(ObjectType typ) {
        this.typ = typ;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "objectParent_fk")
    public Object getParentNode() {
        return parentNode;
    }

    public void setParentNode(Object parentNode) {
        this.parentNode = parentNode;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY    )
    @JoinColumn(name = "objectWert_fk")
    public Set<ObjectValue> getValues() {
        return values;
    }

    public void setValues(Set<ObjectValue> values) {
        this.values = values;
    }

    @Transient
    public List<Object> getChildren() {
        return children;
    }

    public void setChildren(List<Object> children) {
        this.children = children;
    }
}
