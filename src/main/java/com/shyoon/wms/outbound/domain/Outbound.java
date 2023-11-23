package com.shyoon.wms.outbound.domain;

import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

public class Outbound {

    private Long outboundNo;
    private final Long orderNo;
    private final OrderCustomer orderCustomer;
    private final String deliveryRequirements;
    private final List<OutboundProduct> outboundProducts;
    private final Boolean isPriorityDelivery;
    private final LocalDate desiredDeliveryAt;

    public Outbound(
            final Long orderNo,
            final OrderCustomer orderCustomer,
            final String deliveryRequirements,
            final List<OutboundProduct> outboundProducts,
            final Boolean isPriorityDelivery,
            final LocalDate desiredDeliveryAt) {
        validateConstructor(orderNo, orderCustomer, deliveryRequirements, outboundProducts, isPriorityDelivery, desiredDeliveryAt);

        this.orderNo = orderNo;
        this.orderCustomer = orderCustomer;
        this.deliveryRequirements = deliveryRequirements;
        this.outboundProducts = outboundProducts;
        this.isPriorityDelivery = isPriorityDelivery;
        this.desiredDeliveryAt = desiredDeliveryAt;
    }

    private void validateConstructor(
            final Long orderNo,
            final OrderCustomer orderCustomer,
            final String deliveryRequirements,
            final List<OutboundProduct> outboundProducts,
            final Boolean isPriorityDelivery,
            final LocalDate desiredDeliveryAt) {
        Assert.notNull(orderNo, "주문번호는 필수입니다.");
        Assert.notNull(orderCustomer, "주문고객은 필수입니다.");
        Assert.notNull(deliveryRequirements, "배송요구사항은 필수입니다.");
        Assert.notEmpty(outboundProducts, "출고상품은 필수입니다.");
        Assert.notNull(isPriorityDelivery, "우선출고여부는 필수입니다.");
        Assert.notNull(desiredDeliveryAt, "희망출고일은 필수입니다.");
    }

    public void assignNo(final Long outboundNo) {
        this.outboundNo = outboundNo;
    }

    public Long getOutboundNo() {
        return outboundNo;
    }
}
