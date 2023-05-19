package vn.inergy.server.model.license;

import lombok.Data;

import java.time.LocalDate;

@Data
public class License {
    private String licenseKey;
    private Boolean isHardKey;
    private LocalDate expirationTime;
}
