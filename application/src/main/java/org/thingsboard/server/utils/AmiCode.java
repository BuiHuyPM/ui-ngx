package org.thingsboard.server.utils;

import Key.KeyObj;
import org.thingsboard.server.common.data.StringUtils;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AmiCode {
    public static final String key = "license-key";
    private static final int secretKey = 1047893;

    public static String GetUsbKey(String prefix) throws SocketException {
        String hacAddress = HardwareUtils.getMACAddress();
        return GetUsbKey(prefix, hacAddress);
    }

    public static String GetUsbKey(String prefix, String hacAddress) {
        KeyObj keyObj = new KeyObj();
        short[] handle = new short[1];
        int[] lp1 = new int[1];
        int[] lp2 = new int[2];
        long ret = keyObj.UniKey_Find(handle, lp1, lp2);
        int usbKey = lp1[0];
        return ret == keyObj.SUCCESS ? Encode(prefix, usbKey, hacAddress) : null;
    }

    public static String GetSoftKey(String prefix) throws SocketException {
        return Encode(prefix, secretKey);
    }

    public static String GetSoftKey(String prefix, String hacAddress) {
        return Encode(prefix, secretKey, hacAddress);
    }

    public static String Encode(String prefix, int secretKey) throws SocketException {
        String hacAddress = HardwareUtils.getMACAddress();
        return Encode(prefix, secretKey, hacAddress);
    }

    public static String Encode(String prefix, int secretKey, String hacAddress) {
        String licenseUsb = String.valueOf(secretKey);
        String key = prefix + hacAddress + licenseUsb;
        String uuid = UUID.nameUUIDFromBytes(key.getBytes()).toString();
        String encoder = uuid.toUpperCase();
        encoder = encoder.replaceAll("-", "");
        List<String> encoderArray = usingSplitMethod(encoder);
        String[] prefixes = prefix.split("(?<=\\G.{" + 2 + "})");
        String prefix1 = prefixes[0] + prefixes[2] + "-";
        String prefix2 = "-" + prefixes[1] + prefixes[3];
        encoder = "AMISOFT-" + prefix1 + String.join("-", encoderArray) + prefix2;
        return encoder;
    }

    private static List<String> usingSplitMethod(String text) {
        String[] results = text.split("(?<=\\G.{" + 4 + "})");
        return Arrays.asList(results);
    }

    public static boolean verify(String licenseKey, boolean isHardKey) {
        try {
            String[] prefixes = new String[4];
            prefixes[0] = licenseKey.substring(8 , 10);
            prefixes[2] = licenseKey.substring(10 , 12);
            prefixes[1] = licenseKey.substring(licenseKey.length()-4,licenseKey.length()-2);
            prefixes[3] = licenseKey.substring(licenseKey.length() - 2);
            String prefix = StringUtils.join(prefixes, "");
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                LocalDate dateTime = LocalDate.parse(prefix, formatter);
                if (dateTime.isBefore(LocalDate.now())) {
                    return false;
                }
            } catch (DateTimeParseException ignored) {

            }

            String code = isHardKey ? GetUsbKey(prefix) : GetSoftKey(prefix);
            if (code == null) {
                return false;
            }
            return licenseKey.equals(code);
        } catch (Exception ex) {
            return false;
        }
    }
}
