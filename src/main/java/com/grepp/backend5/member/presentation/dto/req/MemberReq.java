package com.grepp.backend5.member.presentation.dto.req;

public record MemberReq(String email, String name, String password,
                        String phone, String address, String status) {
}
