package com.shyoon.wms.inbound.feature;

import com.shyoon.wms.common.ApiTest;
import com.shyoon.wms.common.Scenario;
import com.shyoon.wms.inbound.domain.Inbound;
import com.shyoon.wms.inbound.domain.InboundItem;
import com.shyoon.wms.inbound.domain.InboundRepository;
import com.shyoon.wms.inbound.domain.LPN;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterLPNTest extends ApiTest {

    @Autowired
    private RegisterLPN registerLPN;

    @Autowired
    private InboundRepository inboundRepository;

    @Test
    @DisplayName("LPN을 등록한다.")
    @Transactional
    void registerLPN() {
        Scenario
                .registerProduct().request()
                .registerInbound().request()
                .confirmInbound().request();

        final Long inboundItemNo = 1L;
        final String lpnBarcode = "LPN-0001";
        final LocalDateTime expirationAt = LocalDateTime.now().plusDays(1L);

        RegisterLPN.Request request = new RegisterLPN.Request(
                lpnBarcode,
                expirationAt
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/inbounds/inbound-items/{inboundItemNo}/lpns", inboundItemNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        final Inbound inbound = inboundRepository.findByInboundItemNo(inboundItemNo).get();
        final InboundItem inboundItem = inbound.testingGetInboundItemBy(inboundItemNo);
        final List<LPN> lpnList = inboundItem.testingGetLpnList();
        assertThat(lpnList).hasSize(1);
    }

}
