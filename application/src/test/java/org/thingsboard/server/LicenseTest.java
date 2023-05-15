package org.thingsboard.server;

import static org.assertj.core.api.Assertions.assertThat;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.thingsboard.server.utils.AmiCode;
import org.thingsboard.server.utils.HardwareUtils;

import java.net.SocketException;
import java.net.UnknownHostException;

public class LicenseTest {
    String hacAddress = "D8-12-65-99-40-07";
    int usbSecretKey = 344897635;

    @Test
    public void HMC_Address_Test() throws SocketException, UnknownHostException {
        String MACAddress =  HardwareUtils.getMACAddress();
        assertThat(MACAddress).isEqualTo(hacAddress);
    }

    @Test
    public void UsbKey_Test() throws SocketException, UnknownHostException {
        String usbKey = AmiCode.GetUsbKey(hacAddress);
        String usbKey2 = AmiCode.Encode(usbSecretKey,hacAddress);
        System.out.println("UsbKey:"+usbKey);
        assertThat(usbKey).isEqualTo(usbKey2);
    }
    @Test
    public void softKey_Test() throws SocketException, UnknownHostException {
        String softKey = AmiCode.GetSoftKey();
        String softKey2 = AmiCode.GetSoftKey(hacAddress);
        System.out.println("SoftKey:"+softKey2);
        assertThat(softKey).isEqualTo(softKey2);
    }


}
