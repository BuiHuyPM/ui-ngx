package org.thingsboard.server.utils;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class HardwareUtils {
    public static String getMACAddress() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        String macAddress = null;
        while(networkInterfaces.hasMoreElements())
        {
            NetworkInterface network = networkInterfaces.nextElement();
            byte[] mac = network.getHardwareAddress();
            if(mac != null)
            {
                String[] hexadecimal = new String[mac.length];
                for (int i = 0; i < mac.length; i++) {
                    hexadecimal[i] = String.format("%02X", mac[i]);
                }
                macAddress = String.join("-", hexadecimal);
                break;
            }
        }
        return macAddress;
    }
}
