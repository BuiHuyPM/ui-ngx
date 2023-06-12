package vn.inergy.server;

import org.junit.jupiter.api.Test;
import vn.inergy.server.utils.AmiCode;
import vn.inergy.server.utils.HardwareUtils;

import java.net.SocketException;

import static org.assertj.core.api.Assertions.assertThat;

public class LicenseTest {
//    String prefix = "20230618";
    String prefix = "GNUHTAHN";
    String hacAddress = "02-42-58-3D-73-8E";
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
        System.out.println("usbKey2:"+usbKey2);
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
        boolean verify = AmiCode.verify("AMISOFT-GNTA-AC90-2260-879D-3C2C-BFF8-39D4-6384-8329-UHHN",false);
        assertThat(verify).isTrue();
    }
}
