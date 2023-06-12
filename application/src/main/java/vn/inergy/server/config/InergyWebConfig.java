package vn.inergy.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thingsboard.server.dao.settings.AdminSettingsService;
import vn.inergy.server.filter.LicenseFilter;

@Configuration
public class InergyWebConfig {
    @Autowired
    private AdminSettingsService adminSettingsService;

    @Bean
    public FilterRegistrationBean<LicenseFilter> licenseFilter(){
        FilterRegistrationBean<LicenseFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new LicenseFilter(adminSettingsService));
        registrationBean.addUrlPatterns("/api/(?!v1/)(?!license$).*");
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
