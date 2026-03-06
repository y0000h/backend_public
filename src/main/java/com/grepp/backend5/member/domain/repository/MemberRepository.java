package com.grepp.backend5.member.domain.repository;

import com.grepp.backend5.member.domain.model.Member;

import java.util.List;

public interface MemberRepository {
    List<Member> findAll();

    Member save(Member member);

    boolean findByPhone(String phone);

    Member findByEmail(String email);
}
