package org.thingsboard.server.config;

import Key.KeyObj;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UsbKeyFilter implements Filter {
    @Value("${licenseKey}")
    private Integer licenseKey;

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
        allowedUris.add("/api/v1/\\w+/telemetry");
        allowedUris.add("/api/v1/\\w+/attributes");
        allowedUris.add("/api/v1/\\w+/attributes/updates");
        allowedUris.add("/api/v1/\\w+/rpc");
        allowedUris.add("/api/v1/\\w+/rpc/\\w+");
        allowedUris.add("/api/v1/\\w+/claim");
        allowedUris.add("/api/v1/\\w+/provision");
        allowedUris.add("/api/v1/\\w+/firmware");

        boolean isMatch = allowedUris.stream().anyMatch(uri::matches);

        if (!isMatch){
            KeyObj mUniKey = new KeyObj();
            short[] handle = new short[1];
            int[] lp1 = new int[1];
            int[] lp2 = new int[2];
            long result = mUniKey.UniKey_Find(handle, lp1, lp2);
            if (result != mUniKey.SUCCESS || licenseKey != lp1[0]) {
                res.setHeader("Content-Type", "charset=UTF-16LE");
                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                res.getWriter().write("Please insert your license USB key into the server's USB port !");
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
