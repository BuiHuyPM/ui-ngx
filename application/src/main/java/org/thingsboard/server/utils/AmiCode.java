package org.thingsboard.server.utils;

import Key.KeyObj;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AmiCode {
    public static final String key = "license-key";

    public static String GetUsbKey(String pattern) throws SocketException, UnknownHostException {
        String hacAddress = HardwareUtils.getMACAddress();
        return GetUsbKey(pattern,hacAddress);
    }
    public static String GetUsbKey(String pattern,String hacAddress) throws SocketException, UnknownHostException {
        KeyObj keyObj = new KeyObj();
        short[] handle = new short[1];
        int[] lp1 = new int[1];
        int[] lp2 = new int[2];
        long ret = keyObj.UniKey_Find(handle, lp1, lp2);
        int usbKey = lp1[0];
        return ret == keyObj.SUCCESS ? Encode(usbKey,pattern,hacAddress) : null;
    }

    public static String GetSoftKey(String pattern,String hacAddress) throws SocketException, UnknownHostException {
        return Encode(1047893,pattern,hacAddress);
    }
    public static String GetSoftKey(String pattern) throws SocketException, UnknownHostException {
        return Encode(1047893,pattern);
    }
    public static String Encode(int secretKey,String pattern) throws SocketException, UnknownHostException {
        String hacAddress = HardwareUtils.getMACAddress();
        return Encode(secretKey,pattern,hacAddress);
    }

    public static String Encode(int secretKey,String pattern,String hacAddress) {
        String licenseUsb = String.valueOf(secretKey);
        DateTimeFormatter dateFormat =  DateTimeFormatter.ofPattern(pattern);
        String prefixKey = LocalDate.now().format(dateFormat);
        String key = prefixKey + hacAddress + licenseUsb;
        String uuid = UUID.nameUUIDFromBytes(key.getBytes()).toString();
        String encoder = uuid.toUpperCase();
        encoder = encoder.replaceAll("-","");
        List<String> encoderArray = usingSplitMethod(encoder);
        encoder = "AMISOFT-"+String.join("-", encoderArray);
        return encoder;
    }

    private static List<String> usingSplitMethod(String text) {
        String[] results = text.split("(?<=\\G.{" + 4 + "})");
        return Arrays.asList(results);
    }
}
