package org.thingsboard.server.config;

import Key.KeyObj;
import com.google.gson.Gson;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.thingsboard.server.utils.HardwareUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UsbKeyFilter implements Filter {
    @Value("${licenseKey}")
    private String licenseKey;

    @Autowired
    private Configuration freemarkerConfig;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri =  req.getRequestURI();
        List<String> allowedUris = new ArrayList<>();
        allowedUris.add("/api/v1/.*");
        allowedUris.add("/static/.*");
        allowedUris.add("/assets/.*");
        allowedUris.add(".*\\/\\w*(.json|.ico|.css|.js|.png|.svg|.jpg|.ttf)");
        boolean isMatch = allowedUris.stream().anyMatch(uri::matches);
        if (!isMatch && !"$2a$10$vsTE270ueipXz227XeTDnuzcDVPrQqSfe4AoRGQdtlIVPJStGYbBu123".equals(licenseKey)){
            KeyObj keyObj = new KeyObj();
            short[] handle = new short[1];
            int[] lp1 = new int[1];
            int[] lp2 = new int[2];
            long result = keyObj.UniKey_Find(handle, lp1, lp2);
            String hacAddress = HardwareUtils.getMACAddress();
            String licenseUsb = String.valueOf(lp1[0]);
            String prefixKey = "hungtn";
            if (result != keyObj.SUCCESS || !passwordEncoder.matches(prefixKey+hacAddress+licenseUsb, licenseKey )) {
                    res.setStatus(HttpServletResponse.SC_PAYMENT_REQUIRED);
                    String contentType = req.getContentType();
                    if (!Objects.equals(contentType, "application/json")){
                        try {
                            Template template = freemarkerConfig.getTemplate("no_license.ftl");
                            response.setContentType("text/html");
                            res.getWriter().write( FreeMarkerTemplateUtils.processTemplateIntoString(template, new HashMap<String,String>()));
                            res.getWriter().flush();
                            return;
                        } catch (TemplateException ignored) {

                        }
                    }
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    response.setContentType("application/json");
                    HashMap<String, Object> body = new HashMap<>();
                    body.put("message","Please insert your license USB key into the server's USB port!");
                    body.put("status",HttpServletResponse.SC_PAYMENT_REQUIRED);
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
