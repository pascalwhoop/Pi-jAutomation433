package com.opitz.iotprototype.services;

import com.opitz.iotprototype.daos.UserDAO;
import com.opitz.iotprototype.entities.NetworkNode;
import com.opitz.iotprototype.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * User: Pascal
 * Date: 27.03.14
 * Time: 10:56
 */

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDAO userDAO;
    @Autowired
    NetworkNodeService networkNodeService;

    @Transactional
    @Override
    public Serializable save(User user) {
        return userDAO.save(user);
    }

    @Transactional
    @Override
    public void update(User user) {
        userDAO.update(user);
    }

    @Transactional
    @Override
    public void delete(User user) {
        userDAO.delete(user);
    }

    @Transactional
    @Override
    public User load(String username) {
        return userDAO.load(username);
    }

    @Transactional
    @Override
    public HashMap<String, String> getDeviceMACUserMap() {
        List<User> users = userDAO.listAll();
        HashMap<String, String> map = new HashMap<>();
        for(User user : users){
            map.put(user.getPersonalDevice().getMacAddress(), user.getUsername());
        }
        return map;
    }

    @Transactional
    @Override
    public List<User> listAll() {
        return userDAO.listAll();
    }

    @Transactional
    @Override
    public String retrieveUserStatusByUsername(String username){
        User user = load(username);
        return determineUserState(user);
    }

    /**
     * helper function which determines wether a user is considered "online" or "offline".
     * @param user
     * @return
     */
    private String determineUserState(User user) {

        //get the users data from DB and see how "long ago" the user device has last been seen
        Date now = new Date();
        NetworkNode userDevice = user.getPersonalDevice();
        Date userLastSeen = userDevice.getLastSeen();

        //see if there is a newer node in cache with the same mac address
        //(which translates in the same device just not from DB but form arp cache)

        NetworkNode node = networkNodeService.getNodeFromCacheByMac(userDevice.getMacAddress());
        if(node != null){
            userLastSeen = node.getLastSeen();
        }
        //hard coded for now 2 minutes away == offline
        if(now.getTime() - userLastSeen.getTime() < (1000*60*2)){
            return "online";
        }
        else{
            return "offline";
        }
    }

    @Transactional
    @Override
    public HashMap<String, String> retrieveAllUserStatuses() {
        List<User> users = listAll();
        HashMap<String, String> states = new HashMap<>();
        for(User user : users){
            states.put(user.getUsername(), determineUserState(user));
        }
        return states;
    }

    /**
     * method for matching any users in DB with any nodes passed. Any node that has been found and is matched to a user
     * is returned.
     * @param nodes
     * @return
     */
    @Transactional
    @Override
    public List<User> checkForUserNodes(HashMap<String, NetworkNode> nodes) {
        List<User> users = listAll();
        Set<String> macAddresses = nodes.keySet();

        //iterating through our users and looking if any of the found nodes have the mac address of the user device
        for(User user : users){
            //if foundDevice != any(userDevice) then remove user from list. only those that are found stay in the list
            if (!macAddresses.contains(user.getPersonalDevice().getMacAddress())){
                users.remove(user);
            }
        }
        //return all found users
        return users;
    }
}
