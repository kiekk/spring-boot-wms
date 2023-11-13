package com.shyoon.wms.location.feature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegisterLocationTest {

    private RegisterLocation registerLocation;

    @BeforeEach
    void setUp() {
        registerLocation = new RegisterLocation();
    }

    @Test
    @DisplayName("로케이션을 등록한다.")
    void registerLocation() {
        RegisterLocation.Request request = new RegisterLocation.Request();
        registerLocation.request(request);
    }

    private class RegisterLocation {
        public void request(Request request) {

        }

        public record Request() {
        }
    }
}