package vn.inergy.server.controller;

import org.springframework.web.bind.annotation.*;
import org.thingsboard.server.common.data.AdminSettings;
import org.thingsboard.server.controller.BaseController;
import vn.inergy.server.model.license.License;
import org.thingsboard.server.common.data.exception.ThingsboardErrorCode;
import org.thingsboard.server.common.data.exception.ThingsboardException;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.dao.settings.AdminSettingsService;
import vn.inergy.server.utils.AmiCode;

import java.time.LocalDate;


@RestController
@RequestMapping("/api/license")
public class LicenseController extends BaseController {

    private final AdminSettingsService adminSettingsService;

    public LicenseController(AdminSettingsService adminSettingsService) {
        this.adminSettingsService = adminSettingsService;
    }

    @GetMapping
    @ResponseBody
    public AdminSettings getLicense() throws ThingsboardException {
        try {
            String key = AmiCode.key;
            return checkNotNull(adminSettingsService.findAdminSettingsByKey(TenantId.SYS_TENANT_ID, key), "No Administration settings found for key: " + key);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @ResponseBody
    @GetMapping("detail")
    public License getLicenseDetail() throws ThingsboardException {
        try {
            AdminSettings adminSettings = adminSettingsService.findAdminSettingsByKey(TenantId.SYS_TENANT_ID, AmiCode.key);

            if (adminSettings == null) {
                throw new ThingsboardException("License not found", ThingsboardErrorCode.ITEM_NOT_FOUND);
            }

            String licenseKey = adminSettings.getJsonValue().get("licenseKey").asText();
            boolean isHardKey = adminSettings.getJsonValue().get("isHardKey").asBoolean();
            String pre = AmiCode.getPrefix(licenseKey);
            LocalDate exp = AmiCode.preFixToLocalDate(pre);

            License license = new License();
            license.setLicenseKey(licenseKey);
            license.setIsHardKey(isHardKey);
            license.setExpirationTime(exp);

            return license;
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @PostMapping
    @ResponseBody
    public AdminSettings saveLicense(@RequestBody AdminSettings adminSettings) throws ThingsboardException {
        try {
            adminSettings.setTenantId(TenantId.SYS_TENANT_ID);
            adminSettings.setKey(AmiCode.key);
            adminSettings = checkNotNull(adminSettingsService.saveAdminSettings(TenantId.SYS_TENANT_ID, adminSettings));
            return adminSettings;
        } catch (Exception e) {
            throw handleException(e);
        }
    }
}
