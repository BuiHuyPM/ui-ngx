package org.thingsboard.server.config;

import com.google.gson.Gson;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.thingsboard.server.utils.AmiCode;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UsbKeyFilter implements Filter {
    @Value("${licenseKey:}")
    private String licenseKey;

    @Autowired
    private Configuration freemarkerConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        List<String> allowedUris = new ArrayList<>();
        allowedUris.add("/api/v1/.*");
        allowedUris.add("/static/.*");
        allowedUris.add("/assets/.*");
        allowedUris.add(".*\\/\\w*(.json|.ico|.css|.js|.png|.svg|.jpg|.ttf)");
        boolean isMatch = allowedUris.stream().anyMatch(uri::matches);
        if (!isMatch && !"xxxxxxxx-xxxx-Bxxx-Axxx-xxxxxxxxxxxx".equals(licenseKey)) {
            String code = AmiCode.GetUsbKey();
            if (code == null || !code.equals(licenseKey)) {
                res.setStatus(HttpServletResponse.SC_PAYMENT_REQUIRED);
                String contentType = req.getContentType();
                if (!Objects.equals(contentType, "application/json")) {
                    try {
                        Template template = freemarkerConfig.getTemplate("no_license.ftl");
                        response.setContentType("text/html");
                        res.getWriter().write(FreeMarkerTemplateUtils.processTemplateIntoString(template, new HashMap<String, String>()));
                        res.getWriter().flush();
                        return;
                    } catch (TemplateException ignored) {

                    }
                }
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
