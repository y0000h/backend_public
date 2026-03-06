package com.grepp.backend5.member.presentation.dto.res;

import java.util.UUID;

public record MemberRes(UUID id, String name, String address) {
}
