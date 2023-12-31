package com.shyoon.wms.outbound.feature.api;

import com.shyoon.wms.common.Scenario;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class AllocatePickingApi extends AllocatePickingToteApi {

    private Long outboundNo = 1L;

    public AllocatePickingApi outboundNo(final Long outboundNo) {
        this.outboundNo = outboundNo;
        return this;
    }

    public Scenario request() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .post("/outbounds/{outboundNo}/allocate-picking", outboundNo)
                .then().log().all();

        return new Scenario();
    }
}
