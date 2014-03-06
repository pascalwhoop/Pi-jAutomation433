package com.opitz.devices.services;

import org.krakenapps.pcap.decoder.ethernet.MacAddress;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;
import java.util.Set;

/**
 * User: Pascal
 * Date: 04.03.14
 * Time: 21:39
 */
public interface NetworkNodeService {

    public Map<MacAddress, InetAddress> getAllDevicesWithARPing() throws IOException;

    public Set<String> pingAndQueryArp();

    public void periodicPingScan();
}
