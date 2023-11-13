package com.shyoon.wms.common;

import com.shyoon.wms.inbound.feature.api.ConfirmInboundApi;
import com.shyoon.wms.inbound.feature.api.RegisterInboundApi;
import com.shyoon.wms.inbound.feature.api.RegisterLPNApi;
import com.shyoon.wms.inbound.feature.api.RejectInboundApi;
import com.shyoon.wms.product.feature.api.RegisterProductApi;

public class Scenario {
    public static RegisterProductApi registerProduct() {
        return new RegisterProductApi();
    }

    public static RegisterInboundApi registerInbound() {
        return new RegisterInboundApi();
    }

    public static ConfirmInboundApi confirmInbound() {
        return new ConfirmInboundApi();
    }

    public RejectInboundApi rejectInbound() {
        return new RejectInboundApi();
    }

    public RegisterLPNApi registerLPN() {
        return new RegisterLPNApi();
    }
}
