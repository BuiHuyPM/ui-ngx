package org.thingsboard.server;

import static org.assertj.core.api.Assertions.assertThat;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.thingsboard.server.utils.AmiCode;

public class LicenseTest {
    @SneakyThrows
    @Test
    public void generateKey(){
        String softKey = AmiCode.GetSoftKey();
        String softKey2 = AmiCode.GetSoftKey("D8-12-65-99-40-07");
        System.out.println("SoftKey:"+softKey2);
        assertThat(softKey).isEqualTo(softKey2);
    }
}
