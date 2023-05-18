package org.thingsboard.server;

import org.junit.jupiter.api.Test;
import org.thingsboard.server.utils.AmiCode;
import org.thingsboard.server.utils.HardwareUtils;

import java.net.SocketException;

import static org.assertj.core.api.Assertions.assertThat;

public class LicenseTest {
    String hacAddress = "4C-D9-8F-C4-BC-A0";
    int usbSecretKey = 344897635;

    @Test
    public void HMC_Address_Test() throws SocketException {
        String MACAddress =  HardwareUtils.getMACAddress();
        assertThat(MACAddress).isEqualTo(hacAddress);
    }

    @Test
    public void UsbKey_Test() {
        String usbKey = AmiCode.GetUsbKey("20230516",hacAddress);
        String usbKey2 = AmiCode.Encode("20230516",usbSecretKey,hacAddress);
        System.out.println("UsbKey:"+usbKey);
        assertThat(usbKey).isEqualTo(usbKey2);
    }
    @Test
    public void softKey_Test() throws SocketException {
        String softKey = AmiCode.GetSoftKey("20230618");
        String softKey2 = AmiCode.GetSoftKey("20230618",hacAddress);
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
