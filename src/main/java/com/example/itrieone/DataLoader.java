package com.example.itrieone;

import com.example.itrieone.domain.Member;
import com.example.itrieone.domain.MemberRole;
import com.example.itrieone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final MemberRepository memberRepository;

    @Override
    public void run(String... args) throws Exception {
        if(!memberRepository.findByusername("admin").isPresent()){
            Member admin = Member.builder()
                    .username("admin")
                    .password("adminpass")
                    .email("admin@example.com")
                    .totalPoint(0)
                    .role(MemberRole.ADMIN)
                    .build();
            memberRepository.save(admin);
        }
    }
}
