package org.thingsboard.server;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FilterTest {
    String regex = "/api/(?!v1/)(?!license$).*";

    @Test
    public void allowUrl() {
        String uri = "/api/v2/dasdas";
        boolean isMatch = uri.matches(regex);
        assertThat(isMatch).isTrue();
    }
    @Test
    public void notAllowUrlLicense() {
        String uri = "/api/license";
        boolean isMatch = uri.matches(regex);
        assertThat(isMatch).isFalse();
    }
    @Test
    public void notAllowUrl() {
        String uri = "/api/v1/dasdas";
        boolean isMatch = uri.matches(regex);
        assertThat(isMatch).isFalse();
    }
}
