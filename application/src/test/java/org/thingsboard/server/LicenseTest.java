package org.thingsboard.server;

import org.junit.jupiter.api.Test;
import org.thingsboard.server.utils.AmiCode;
import org.thingsboard.server.utils.HardwareUtils;

import java.net.SocketException;

import static org.assertj.core.api.Assertions.assertThat;

public class LicenseTest {
    String prefix = "20230618";
    String hacAddress = "4C-D9-8F-C4-BC-A0";
    int usbSecretKey = 344897635;

    @Test
    public void HMC_Address_Test() throws SocketException {
        String MACAddress =  HardwareUtils.getMACAddress();
        assertThat(MACAddress).isEqualTo(hacAddress);
    }

    @Test
    public void UsbKey_Test() {
        String usbKey = AmiCode.GetUsbKey(prefix,hacAddress);
        String usbKey2 = AmiCode.Encode(prefix,usbSecretKey,hacAddress);
        System.out.println("UsbKey:"+usbKey);
        assertThat(usbKey).isEqualTo(usbKey2);
    }
    @Test
    public void softKey_Test() throws SocketException {
        String softKey = AmiCode.GetSoftKey(prefix);
        String softKey2 = AmiCode.GetSoftKey(prefix,hacAddress);
        System.out.println("softKey:"+softKey);
        System.out.println("softKey2:"+softKey2);
        assertThat(softKey).isEqualTo(softKey2);
    }

    @Test
    public void verify_Test() {
        boolean verify = AmiCode.verify("AMISOFT-2005-7140-7636-D164-353E-8AED-58F7-CB51-4A18-2318",false);
        assertThat(verify).isTrue();
    }
}
