package org.thingsboard.server;

import static org.assertj.core.api.Assertions.assertThat;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.thingsboard.server.utils.AmiCode;
import org.thingsboard.server.utils.HardwareUtils;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LicenseTest {
    String hacAddress = "02-42-0F-F4-AD-F5";
    int usbSecretKey = 344897635;

    @Test
    public void HMC_Address_Test() throws SocketException, UnknownHostException {
        String MACAddress =  HardwareUtils.getMACAddress();
        assertThat(MACAddress).isEqualTo(hacAddress);
    }

    @Test
    public void UsbKey_Test() throws SocketException, UnknownHostException {
        String usbKey = AmiCode.GetUsbKey("20230516",hacAddress);
        String usbKey2 = AmiCode.Encode("20230516",usbSecretKey,hacAddress);
        System.out.println("UsbKey:"+usbKey);
        assertThat(usbKey).isEqualTo(usbKey2);
    }
    @Test
    public void softKey_Test() throws SocketException {
        String softKey = AmiCode.GetSoftKey("20230518");
        String softKey2 = AmiCode.GetSoftKey("20230518",hacAddress);
        System.out.println("softKey:"+softKey);
        System.out.println("softKey2:"+softKey2);
        assertThat(softKey).isEqualTo(softKey2);
    }

    @Test
    public void verify_Test() {
        boolean verify = AmiCode.verify("AMISOFT-7140-7636-D164-353E-8AED-58F7-CB51-4A18-20230518",false);
        assertThat(verify).isTrue();
    }
}
