package com.grepp.backend5.member.application.service;

import com.grepp.backend5.member.application.usecase.MemberUseCase;
import com.grepp.backend5.member.domain.model.Member;
import com.grepp.backend5.member.domain.repository.MemberRepository;
import com.grepp.backend5.member.presentation.dto.req.MemberReq;
import com.grepp.backend5.member.presentation.dto.res.MemberAdmRes;
import com.grepp.backend5.member.presentation.dto.res.MemberRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService implements MemberUseCase {
    public final MemberRepository memberRepository;
    @Override
    public List<MemberRes> findAll() {
        return memberRepository.findAll().stream().map(this::changeMemberResType).toList();
    }

    @Override
    public List<MemberAdmRes> findAdmAll() {
        return memberRepository.findAll().stream().map(this::changeMemberAdmResType).toList();
    }

    @Transactional
    @Override
    public MemberRes save(MemberReq memberReq) {
        if(!memberRepository.findByPhone(memberReq.phone())){

            SecureRandom random = new SecureRandom();
            byte[] saltkey = random.generateSeed(8);

            Member member =Member.create(memberReq.email(), memberReq.name(), memberReq.address(),
                    memberReq.status(), memberReq.password(), memberReq.phone());
            member.setSaltKey(Base64.getEncoder().encodeToString(saltkey));
            log.info("saltkey : {}", member.getSaltKey());
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            member.setPassword(encoder.encode(memberReq.password()+member.getSaltKey()));

            memberRepository.save(member);
            return new MemberRes(
                    member.getId(), member.getName(), member.getAddress());
        }else{
            //TODO: throw
        }
        return null;
    }

    private MemberRes changeMemberResType(Member member){
        return new MemberRes(
                member.getId(), member.getName(), member.getAddress()
        );
    }
    private MemberAdmRes changeMemberAdmResType(Member member){
        return new MemberAdmRes(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getPhone(),
                member.getAddress(),
                member.getStatus(),
                member.getRegId(),
                member.getRegDt(),
                member.getModifyId(),
                member.getModifyDt(),
                member.getFlag()
        );
    }
}
