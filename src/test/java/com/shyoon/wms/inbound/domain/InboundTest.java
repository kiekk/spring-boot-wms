package com.shyoon.wms.inbound.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InboundTest {

    @Test
    @DisplayName("입고를 승인한다.")
    void confirmed() {
        // given
        final Inbound inbound = InboundFixture.anInbound().build();
        final InboundStatus beforeStatus = inbound.getStatus();

        // when
        inbound.confirmed();

        // then
        assertThat(beforeStatus).isEqualTo(InboundStatus.REQUESTED);
        assertThat(inbound.getStatus()).isEqualTo(InboundStatus.CONFIRMED);
    }

    @Test
    @DisplayName("입고 승인을 실패한다. 입고의 상태가 요청이 아닌 경우 예외가 발생한다.")
    void fail_invalid_status_confirmed() {
        // given
        final Inbound confirmedInbound = InboundFixture.anInboundWithConfirmed().build();

        // when & then
        assertThatThrownBy(confirmedInbound::confirmed)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("입고 요청 상태가 아닙니다.");
    }

    @Test
    @DisplayName("입고를 반려/거부하면 입고의 상태가 REJECTED가 된다.")
    void reject() {
        // given
        final Inbound inbound = InboundFixture.anInbound().build();
        final InboundStatus beforeStatus = inbound.getStatus();

        // when
        final String rejectionReason = "반려 사유";
        inbound.reject(rejectionReason);

        // then
        assertThat(beforeStatus).isEqualTo(InboundStatus.REQUESTED);
        assertThat(inbound.getStatus()).isEqualTo(InboundStatus.REJECTED);
    }

    @Test
    @DisplayName("입고를 반려/거부를 실패한다. 입고의 상태가 요청이 아닌 경우 예외가 발생한다.")
    void fail_invalid_status_reject() {
        // given
        final Inbound rejectedInbound = InboundFixture.anInboundWithRejected().build();

        // when & then
        assertThatThrownBy(() -> rejectedInbound.reject("rejectionReason"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("입고 요청 상태가 아닙니다.");
    }

    @Test
    @DisplayName("LPN을 등록한다.")
    void registerLPN() {
        // given
        final Inbound inbound = InboundFixture.anInboundWithConfirmed().build();
        final Long inboundItemNo = 1L;
        final String lpnBarcode = "LPN-0001";
        final LocalDateTime expirationAt = LocalDateTime.now().plusDays(1);

        // when
        inbound.registerLPN(inboundItemNo, lpnBarcode, expirationAt);

        // then
        final InboundItem inboundItem = inbound.testingGetInboundItemBy(inboundItemNo);
        final List<LPN> lpns = inboundItem.testingGetLpnList();
        assertThat(lpns).hasSize(1);
    }

    @Test
    @DisplayName("입고 확정 상태가 아닌 경우 LPN을 등록에 실패한다.")
    void fail_invalid_status_registerLPN() {
        // given
        final Inbound inbound = InboundFixture.anInbound().build();
        final Long inboundItemNo = 1L;
        final String lpnBarcode = "LPN-0001";
        final LocalDateTime expirationAt = LocalDateTime.now().plusDays(1);

        // when & then
        assertThatThrownBy(() -> inbound.registerLPN(inboundItemNo, lpnBarcode, expirationAt))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("입고 확정 상태가 아닙니다.");
    }

    @Test
    @DisplayName("유통 기한이 현재 날짜보다 이전일 경우 LPN 등록에 실패한다.")
    void fail_expire_registerLPN() {
        // given
        final Inbound inbound = InboundFixture.anInboundWithConfirmed().build();
        final Long inboundItemNo = 1L;
        final String lpnBarcode = "LPN-0001";
        final LocalDateTime expirationAt = LocalDateTime.now();

        // when & then
        assertThatThrownBy(() -> inbound.registerLPN(inboundItemNo, lpnBarcode, expirationAt))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("유통기한은 현재 시간보다 이전일 수 없습니다.");
    }

}
