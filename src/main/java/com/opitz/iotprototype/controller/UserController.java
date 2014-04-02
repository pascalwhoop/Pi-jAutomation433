package com.opitz.iotprototype.controller;

import com.opitz.iotprototype.entities.User;
import com.opitz.iotprototype.services.NetworkNodeService;
import com.opitz.iotprototype.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User: Pascal
 * Date: 03.09.13
 * Time: 22:06
 */

@Controller
@RequestMapping("/service/user")
public class UserController {

    @Autowired
    NetworkNodeService networkNodeService;
    @Autowired
    UserService userService;

    /**
     *
     * Method to set the current state of the user. For now only basic functionality
     * @param username
     * @param state expected states: present, absent
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/{username}/state/{setState}", method = RequestMethod.POST)
    public String setUserState(@PathVariable("username") String username, @PathVariable("setState") String state){
        return "foo";
    }


    /**
     * Please note that when adding a new user via rest, there must be a network node supplied with the user. so first fetch all network nodes from the api, then select the one that
     * should be the one that the user usually carries on him (a smartphone e.g.) and then add the user with this method.
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/add", method = RequestMethod.PUT)
    public User addNewUser(@RequestBody User user){
        userService.save(user);
        return userService.load(user.getUsername());
    }

    @ResponseBody
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public User updateUser(@RequestBody User user){
        userService.update(user);
        return userService.load(user.getUsername());
    }

    @ResponseBody
    @RequestMapping(value="/delete", method = RequestMethod.DELETE)
    public boolean deleteUser(@RequestBody User user){
        try{
            userService.delete(user);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @ResponseBody
    @RequestMapping(value="/getall", method = RequestMethod.GET)
    public List<User> getAll(){
        return userService.listAll();
    }

    @ResponseBody
    @RequestMapping(value="/getbyusername/{username}", method = RequestMethod.GET)
    public User getByUsername(@PathVariable String username){
        return userService.load(username);
    }

    @ResponseBody
    @RequestMapping(value="/{username}/state", method = RequestMethod.GET)
    public String getUserState(@PathVariable String username){
        return userService.retrieveUserStatus(username);
    }



}
