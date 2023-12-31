package com.shyoon.wms.outbound.domain;

import com.shyoon.wms.location.domain.Location;
import com.shyoon.wms.location.domain.LocationFixture;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class OutboundFixture {
    private Long orderNo = 1L;
    private String deliveryRequirements = "배송 요구사항";
    private boolean isPriorityDelivery = false;
    private LocalDate desiredDeliveryAt = LocalDate.now();
    private List<OutboundProductFixture> outboundProducts = List.of(OutboundProductFixture.anOutboundProduct());
    private OrderCustomerFixture orderCustomer = OrderCustomerFixture.anOrderCustomer();
    private PackagingMaterialFixture packagingMaterial = PackagingMaterialFixture.aPackagingMaterial();
    private LocationFixture pickingTote = LocationFixture.aLocation();

    public static OutboundFixture anOutbound() {
        return new OutboundFixture();
    }

    public OutboundFixture orderNo(final Long orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public OutboundFixture deliveryRequirements(final String deliveryRequirements) {
        this.deliveryRequirements = deliveryRequirements;
        return this;
    }

    public OutboundFixture isPriorityDelivery(final boolean isPriorityDelivery) {
        this.isPriorityDelivery = isPriorityDelivery;
        return this;
    }

    public OutboundFixture desiredDeliveryAt(final LocalDate desiredDeliveryAt) {
        this.desiredDeliveryAt = desiredDeliveryAt;
        return this;
    }

    public OutboundFixture outboundProducts(final OutboundProductFixture... outboundProducts) {
        this.outboundProducts = List.of(outboundProducts);
        return this;
    }

    public OutboundFixture orderCustomer(final OrderCustomerFixture orderCustomer) {
        this.orderCustomer = orderCustomer;
        return this;
    }

    public OutboundFixture packagingMaterial(final PackagingMaterialFixture packagingMaterial) {
        this.packagingMaterial = packagingMaterial;
        return this;
    }

    public OutboundFixture pickingTote(final LocationFixture pickingTote) {
        this.pickingTote = pickingTote;
        return this;
    }

    public Outbound build() {
        return new Outbound(
                orderNo,
                orderCustomer.build(),
                deliveryRequirements,
                buildOutboundProducts(),
                isPriorityDelivery,
                desiredDeliveryAt,
                buildPackagingMaterial(),
                buildPickingTote()
        );
    }

    private List<OutboundProduct> buildOutboundProducts() {
        return outboundProducts.stream()
                .map(OutboundProductFixture::build)
                .collect(Collectors.toList());
    }

    private PackagingMaterial buildPackagingMaterial() {
        return packagingMaterial == null ? null : packagingMaterial.build();
    }

    private Location buildPickingTote() {
        return pickingTote == null ? null : pickingTote.build();
    }

}
