package com.grepp.backend5.member.application.service;

import com.grepp.backend5.member.application.usecase.MemberUseCase;
import com.grepp.backend5.member.domain.model.Member;
import com.grepp.backend5.member.domain.repository.MemberRepository;
import com.grepp.backend5.member.presentation.dto.req.MemberReq;
import com.grepp.backend5.member.presentation.dto.res.MemberAdmRes;
import com.grepp.backend5.member.presentation.dto.res.MemberRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
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
            Member member = memberRepository.save(Member.create(memberReq.email(), memberReq.name(), memberReq.address(),
                    memberReq.status(), memberReq.password(), memberReq.phone()));
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
