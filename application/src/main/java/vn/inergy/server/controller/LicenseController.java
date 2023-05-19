package vn.inergy.server.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Map;


@RestController
@RequestMapping("/api/license")
public class LicenseController extends BaseController {
    private final ObjectMapper mapper;
    private final AdminSettingsService adminSettingsService;

    public LicenseController(ObjectMapper mapper, AdminSettingsService adminSettingsService) {
        this.mapper = mapper;
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

    @PostMapping
    @ResponseBody
    public AdminSettings saveLicense(@RequestBody AdminSettings adminSettings) throws ThingsboardException {
        try {
            if (adminSettings.getJsonValue() != null){
                Map<String, Object> jsonNode = mapper.convertValue(adminSettings.getJsonValue(), new TypeReference<>() {});
                String licenseKey = jsonNode.get("licenseKey").toString();
                String pre = AmiCode.getPrefix(licenseKey);
                LocalDate exp = AmiCode.preFixToLocalDate(pre);
                jsonNode.put("expirationTime",exp);
                adminSettings.setJsonValue(mapper.convertValue(jsonNode, JsonNode.class));
            }
            adminSettings.setTenantId(TenantId.SYS_TENANT_ID);
            adminSettings.setKey(AmiCode.key);
            adminSettings = checkNotNull(adminSettingsService.saveAdminSettings(TenantId.SYS_TENANT_ID, adminSettings));
            return adminSettings;
        } catch (Exception e) {
            throw handleException(e);
        }
    }
}
