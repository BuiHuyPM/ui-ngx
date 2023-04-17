package org.thingsboard.server;

import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        String uri = "/static/favicon.ico";
        List<String> allowedUris = new ArrayList<>();
        allowedUris.add("/api/v1/.*");
        allowedUris.add("/static/.*");
        allowedUris.add("/assets/.*");
        allowedUris.add("/webjars/.*");
        allowedUris.add("/swagger-ui/.*");
        allowedUris.add(".*\\/\\w*(.json|.ico|.css|.js|.png|.svg|.jpg|.ttf)");
        boolean isMatch = allowedUris.stream().anyMatch(uri::matches);
        System.out.println("isMatch"+isMatch);
    }
}
