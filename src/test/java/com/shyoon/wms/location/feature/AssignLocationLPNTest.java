package com.shyoon.wms.location.feature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AssignLocationLPNTest {

    private AssignLocationLPN assignLocationLPN;

    @BeforeEach
    void setUp() {
        assignLocationLPN = new AssignLocationLPN();
    }

    @Test
    @DisplayName("로케이션에 LPN을 할당한다.")
    void assignLocationLPN() {
        final AssignLocationLPN.Request request = new AssignLocationLPN.Request();
        assignLocationLPN.request(request);
    }

    private class AssignLocationLPN {

        public void request(Request request) {

        }

        public record Request() {
        }
    }
}
