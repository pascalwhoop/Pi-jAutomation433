package com.opitz.iotprototype.messages;

import java.io.Serializable;
import java.util.HashMap;

/**
 * User: Pascal
 * Date: 29.05.14
 * Time: 12:29
 *
 * This Message describes a Plug Group {@link com.opitz.iotprototype.entities.DeviceGroup}. It Stores the usernames and the ID's of the plugs its containing
 *
 * @author Pascal
 * @version 29.05.14
 */
public class DeviceGroupJSONMessage implements Serializable {

    private Integer id;
    private String label;
    private HashMap<Integer,String> users; //map of users
    private HashMap<Integer, String> plugs; //map of plugs

    public DeviceGroupJSONMessage(){
        users = new HashMap<>();
        plugs = new HashMap<>();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public HashMap<Integer, String> getPlugs() {
        return plugs;
    }

    public void setPlugs(HashMap<Integer, String> plugs) {
        this.plugs = plugs;
    }

    public HashMap<Integer, String> getUsers() {
        return users;
    }

    public void setUsers(HashMap<Integer, String> users) {
        this.users = users;
    }
}
