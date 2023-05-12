package org.thingsboard.server.controller;

import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.thingsboard.server.common.data.AdminSettings;
import org.thingsboard.server.common.data.exception.ThingsboardException;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.dao.settings.AdminSettingsService;
import org.thingsboard.server.utils.AmiCode;

import java.util.UUID;


@RestController
@RequestMapping("/api/license")
public class LicenseController extends BaseController {

    private final AdminSettingsService adminSettingsService;

    public LicenseController(AdminSettingsService adminSettingsService) {
        this.adminSettingsService = adminSettingsService;
    }

    @GetMapping
    @ResponseBody
    public AdminSettings getAdminSettings() throws ThingsboardException {
        try {
            String key = AmiCode.key;
            return checkNotNull(adminSettingsService.findAdminSettingsByKey(TenantId.SYS_TENANT_ID, key), "No Administration settings found for key: " + key);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @PostMapping
    @ResponseBody
    public AdminSettings saveAdminSettings(@RequestBody AdminSettings adminSettings) throws ThingsboardException {
        try {
            String key = AmiCode.key;
            adminSettings.setTenantId(TenantId.SYS_TENANT_ID);
            adminSettings.setKey(key);
            adminSettings = checkNotNull(adminSettingsService.saveAdminSettings(TenantId.SYS_TENANT_ID, adminSettings));
            return adminSettings;
        } catch (Exception e) {
            throw handleException(e);
        }
    }
}
