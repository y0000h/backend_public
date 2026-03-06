package com.grepp.backend5.member.presentation.dto.res;

import java.time.LocalDateTime;
import java.util.UUID;

public record MemberAdmRes(UUID id, String email, String name, String phone,
                           String address, String status, UUID regId, LocalDateTime regDt,
                           UUID modifyId, LocalDateTime modifyDt, String flag) {
}
