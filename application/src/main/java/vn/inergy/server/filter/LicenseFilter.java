package vn.inergy.server.filter;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.thingsboard.server.common.data.AdminSettings;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.dao.settings.AdminSettingsService;
import vn.inergy.server.utils.AmiCode;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LicenseFilter implements Filter {
    @Autowired
    private AdminSettingsService adminSettingsService;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        boolean isMatch = uri.matches("/api/(?!v1/)(?!license$).*");
        if (isMatch && false) {
            AdminSettings adminSettings = adminSettingsService.findAdminSettingsByKey(TenantId.SYS_TENANT_ID, AmiCode.key);
            boolean hasLicense = true;
            if (adminSettings == null){
                hasLicense = false;
            }else{
                boolean isHardKey = adminSettings.getJsonValue().get("isHardKey").asBoolean();
                String licenseKey = adminSettings.getJsonValue().get("licenseKey").asText();
                if (!AmiCode.verify(licenseKey,isHardKey)){
                    hasLicense = false;
                }
            }
            if (!hasLicense) {
                res.setStatus(HttpServletResponse.SC_PAYMENT_REQUIRED);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                response.setContentType("application/json");
                HashMap<String, Object> body = new HashMap<>();
                body.put("message", "Please insert your license USB key into the server's USB port!");
                body.put("status", HttpServletResponse.SC_PAYMENT_REQUIRED);
                body.put("timestamp", LocalDateTime.now().format(formatter));
                Gson gson = new Gson();
                res.getWriter().write(gson.toJson(body));
                res.getWriter().flush();
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}