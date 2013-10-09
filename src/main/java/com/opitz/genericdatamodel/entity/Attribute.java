package com.opitz.genericdatamodel.entity;

import javax.persistence.*;

/**
 * User: Pascal
 * Date: 28.05.13
 * Time: 10:43
 */
@Entity
public class Attribute {

    //FIELDS
    private Integer id;
    private String label;
    /*private enum feldtypen {
        STRING, INTEGER, DOUBLE, DATE
    }
    private feldtypen feldtyp;  */
    private String datatype;

    private String dataunit;

    //CONSTRUCTORS
    public Attribute(){

    }
    public Attribute(String label){
        this.label = label;
    }


    //GETTER SETTER
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataunit() {
        return dataunit;
    }

    public void setDataunit(String einheit) {
        this.dataunit = einheit;
    }

    @Basic(optional = false)
    public String getLabel() {
        return label;
    }

    public void setLabel(String bezeichnung) {
        this.label = bezeichnung;
    }

    @Basic(optional = false)
    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String feldtyp) {
        this.datatype = feldtyp;
    }
}
