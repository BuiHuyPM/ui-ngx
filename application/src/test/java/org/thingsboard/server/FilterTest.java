package org.thingsboard.server;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FilterTest {

    @Test
    public void allowedUrl() {
        String uri = "/api/v2/dasdas";
        List<String> allowedUris = new ArrayList<>();
        allowedUris.add("/api(?!/v1/).*");
        boolean isMatch = allowedUris.stream().anyMatch(uri::matches);
        assertThat(isMatch).isTrue();
    }

    @Test
    public void alloweAdUrl() {
        String uri = "/api/v1/dasdas";
        List<String> allowedUris = new ArrayList<>();
        allowedUris.add("/api(?!/v1/).*");
        boolean isMatch = allowedUris.stream().anyMatch(uri::matches);
        assertThat(isMatch).isFalse();
    }
}
