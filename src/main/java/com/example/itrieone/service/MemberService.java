package com.example.itrieone.service;

import com.example.itrieone.domain.Member;
import com.example.itrieone.dto.member.MemberLoginRequestDto;
import com.example.itrieone.dto.member.MemberReadDto;
import com.example.itrieone.dto.member.MemberRegisterRequestDto;
import com.example.itrieone.exception.DuplicateMemberException;
import com.example.itrieone.exception.InvalidLoginException;
import com.example.itrieone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 회원가입
     * @param registerRequestDto
     * @return
     */
    public MemberReadDto register(MemberRegisterRequestDto registerRequestDto){
        //중복 username 확인
        Optional<Member> findByUserMember = memberRepository.findByusername(registerRequestDto.getUsername());
        if(findByUserMember.isPresent()){
            throw new DuplicateMemberException("이미 존재하는 사용자 이름입니다.");
        }
        //
        Optional<Member> findByEmailMember = memberRepository.findByEmail(registerRequestDto.getEmail());
        if(findByEmailMember.isPresent()){
            throw new DuplicateMemberException("이미 존재하는 아이디입니다.");
        }

        // 새로운 회원 생성
        Member member = memberRepository.save(registerRequestDto.toEntity());
        return MemberReadDto.fromEntity(member);
    }

    /**
     * 로그인
     * @param loginRequestDto
     * @return
     */
    public MemberReadDto login(MemberLoginRequestDto loginRequestDto) {
        // email로 회원 조회
        Optional<Member> findMember = memberRepository.findByEmail(loginRequestDto.getEmail());

        // 회원 존재 여부 및 비밀번호 검증
        if (findMember.isPresent() && findMember.get().getPassword().equals(loginRequestDto.getPassword())) {
            // 로그인 성공 시 MemberReadDto 반환
            return MemberReadDto.fromEntity(findMember.get());
        } else {
            throw new InvalidLoginException("로그인 정보가 올바르지 않습니다.");
        }
    }

    /**
     * Id로 회원 조회
     */
    public MemberReadDto getMemberById(Long memberId){
        Optional<Member> member = memberRepository.findById(memberId);

        if(member.isPresent()){
            return MemberReadDto.fromEntity(member.get());
        } else {
            throw new NoSuchElementException("존재하지 않는 회원입니다.");
        }
    }
}
