package com.shyoon.wms.inbound.feature;

import com.shyoon.wms.inbound.domain.Inbound;
import com.shyoon.wms.inbound.domain.InboundRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RejectInbound {
    private final InboundRepository inboundRepository;

    @Transactional
    @PostMapping("/inbounds/{inboundNo}/reject")
    public void request(@PathVariable final Long inboundNo,
                        @RequestBody @Valid final Request request) {
        final Inbound inbound = inboundRepository.getBy(inboundNo);

        inbound.reject(request.rejectionReason);
    }

    public record Request(
            @NotBlank(message = "거절 사유는 필수입니다.")
            String rejectionReason) {
    }
}
