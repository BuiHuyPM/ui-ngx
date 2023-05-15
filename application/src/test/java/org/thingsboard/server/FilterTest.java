package org.thingsboard.server;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FilterTest {

    @Test
    public void allowedUrl() {
        String uri = "/580ad9a21db-974f16223.css";
        List<String> allowedUris = new ArrayList<>();
        allowedUris.add("/api/v1/.*");
        allowedUris.add("/static/.*");
        allowedUris.add("/assets/.*");
        allowedUris.add("/webjars/.*");
        allowedUris.add("/swagger-ui/.*");
        allowedUris.add(".*/[a-zA-Z0-9.\\-]+(.json|.ico|.css|.js|.png|.svg|.jpg|.ttf)");
        boolean isMatch = allowedUris.stream().anyMatch(uri::matches);
        assertThat(isMatch).isTrue();
    }
}
