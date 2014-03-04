package com.opitz.devices.controller;

import org.krakenapps.pcap.decoder.ethernet.MacAddress;
import org.krakenapps.pcap.util.Arping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * User: Pascal
 * Date: 03.09.13
 * Time: 22:06
 */

@Controller
@RequestMapping("/service/userstate")
public class UserStateController {


    //@Autowired
    //private RuntimeService runtimeService;

    /**
     *
     * Method to set the current state of the user. For now only basic functionality
     * @param username
     * @param state expected states: present, absent
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/{username}/setuserstate", method = RequestMethod.POST)
    public String setUserState(@PathVariable("username") String username, @RequestParam String state){
        return "foo";
    }

    @ResponseBody
    @RequestMapping(value="/findAllDevices", method=RequestMethod.GET)
    public Map<InetAddress, MacAddress> findAllDevicesOnLocalNetwork() throws IOException{
        return tryARPing();
    }

    static {
        try {
            System.setProperty( "java.library.path", "/usr/local/lib" );
            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("+++++++++++"+System.getProperty("java.library.path"));
        System.loadLibrary("kpcap");

        System.out.println("+++++++++++" + "loading librarys worked");

    }


    private Collection<InetAddress> getSubnetInetAddressColl() throws UnknownHostException {
        Set<InetAddress> addresses = new HashSet<InetAddress>();
        for (int i = 1; i<255;i++){
            addresses.add(InetAddress.getByName(this.getOwnSubnet() + "." + i));
        }
        return addresses;
    }

    private Map<InetAddress, MacAddress> tryARPing() throws IOException {
        // TODO richtige
        // System.load("/Users/Pascal/.m2/repository/org/krakenapps/kraken-pcap/1.3.0/kraken-pcap-1.3.0.jar!/lib/linux_x86_64/libkpcap.so");
        Map<InetAddress, MacAddress> nodes = Arping.scan("en0", getSubnetInetAddressColl(), 2);
        System.out.print(nodes.toString());
        return nodes;

    }

    private String getOwnSubnet(){
        String subnet;
        String ownIp = null;
        try {
            ownIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        String[] ipParts = ownIp.split("\\.");
        subnet = ipParts[0]+ "." + ipParts[1] + "." + ipParts[2]; //subnet aus eigener IP schließen
        return subnet;

    }
    /*@ResponseBody
    @RequestMapping(value="/triggerprocess", method = RequestMethod.GET)
    public String triggerProcess(){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("userstatechange");
        System.out.print(processInstance.toString());
        return processInstance.toString();

    }*/




}
