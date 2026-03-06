package com.grepp.backend5.member.infra.persistence;

import com.grepp.backend5.member.domain.model.Member;
import com.grepp.backend5.member.domain.repository.MemberRepository;
import com.grepp.backend5.member.presentation.dto.req.MemberReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepoAdapter implements MemberRepository {
    public final MemberJpaRepository memberJpaRepository;
    @Override
    public List<Member> findAll() {
        return memberJpaRepository.findAll();
    }

    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(member);
    }

    @Override
    public boolean findByPhone(String phone) {
        return memberJpaRepository.findByPhone(phone).isPresent();
    }

    @Override
    public Member findByEmail(String email) {
        return memberJpaRepository.findByEmail(email).orElseThrow();
    }
}
