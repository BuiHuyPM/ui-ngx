package org.thingsboard.server.utils;

import Key.KeyObj;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AmiCode {
    public static String GetUsbKey() throws SocketException, UnknownHostException {
        KeyObj keyObj = new KeyObj();
        short[] handle = new short[1];
        int[] lp1 = new int[1];
        int[] lp2 = new int[2];
        long ret = keyObj.UniKey_Find(handle, lp1, lp2);
        return ret == keyObj.SUCCESS ? Encode(lp1[0]) : null;
    };
    private static String Encode(int usbKey) throws SocketException, UnknownHostException {
        String hacAddress = HardwareUtils.getMACAddress();
        String licenseUsb = String.valueOf(usbKey);
        String prefixKey = "h-u-n-g-d-z-a-i";
        String key = prefixKey + hacAddress + licenseUsb;
        String uuid = UUID.nameUUIDFromBytes(key.getBytes()).toString();
        String encoder = uuid.toUpperCase();
        encoder = encoder.replaceAll("-","");
        List<String> encoderArray = usingSplitMethod(encoder);
        encoder = "AMISOFT-"+String.join("-", encoderArray);
        return encoder;
    };

    private static List<String> usingSplitMethod(String text) {
        String[] results = text.split("(?<=\\G.{" + 4 + "})");
        return Arrays.asList(results);
    }
}
