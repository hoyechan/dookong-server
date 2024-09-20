package ItriEone.dookong.service;

import ItriEone.dookong.domain.Member;
import ItriEone.dookong.repository.MemberRepository;
import ItriEone.dookong.domain.Member;
import ItriEone.dookong.domain.MemberRole;
import ItriEone.dookong.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     * @param username
     * @param email
     * @param password
     * @param role
     * @return
     */
    public Long register(String username, String email, String password, MemberRole role){
        //중복 username 확인
        Optional<Member> findByUserMember = memberRepository.findByusername(username);
        if(findByUserMember.isPresent()){
            throw new IllegalStateException("이미 존재하는 사용자 이름입니다.");
        }
        //
        Optional<Member> findByEmailMember = memberRepository.findByEmail(email);
        if(findByEmailMember.isPresent()){
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }

        // 새로운 회원 생성
        Member member = new Member();
        member.setUsername(username);
        member.setPassword(password);
        member.setEmail(email);
        member.setTotalPoint(0);
        member.setRole(role);

        return memberRepository.save(member).getId();
    }

    /**
     * 로그인
     * @param email
     * @param password
     * @return
     */

    public boolean login(String email, String password) {
        //email으로 회원 조회
        Optional<Member> findMember = memberRepository.findByEmail(email);

        // 회원 존재 여부 및 비밀번호 검증
        if (findMember.isPresent()) {
            Member member = findMember.get();
            return member.getPassword().equals(password); // 비밀번호 비교
        }
        return false;
    }
}
