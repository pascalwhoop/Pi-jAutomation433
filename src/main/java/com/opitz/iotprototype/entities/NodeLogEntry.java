package com.opitz.iotprototype.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * User: Pascal
 * Date: 25.03.14
 * Time: 19:04
 */
@Entity
public class NodeLogEntry {

    private Integer id;
    private NetworkNode node;
    private Date timestamp;

    public NodeLogEntry(NetworkNode node, Date timestamp){
        this.node = node;
        this.timestamp = timestamp;
    }

    private NodeLogEntry(){
        this.node = null;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    @Basic(optional=false)
    @ManyToOne
    @JoinColumn(name= "node_mac")
    public NetworkNode getNode() {
        return node;
    }

    private void setNode(NetworkNode networkNode){
        this.node = networkNode;
    }

    @Basic(optional = false)
    public Date getTimestamp() {
        return timestamp;
    }

    private void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @PrePersist
    protected void onPersist(){
        timestamp = new Date();
    }
}
