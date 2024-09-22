package com.example.itrieone;

import com.example.itrieone.domain.Member;
import com.example.itrieone.domain.MemberRole;
import com.example.itrieone.domain.RecycleBin;
import com.example.itrieone.repository.MemberRepository;
import com.example.itrieone.repository.RecycleBinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final RecycleBinRepository recycleBinRepository;
    @Override
    public void run(String... args) throws Exception {



            Member member1 = Member.builder()
                    .username("member1")
                    .password("member1pass")
                    .email("member1@example.com")
                    .totalPoint(80)
                    .role(MemberRole.USER)
                    .build();
            memberRepository.save(member1);

            Member member2 = Member.builder()
                    .username("member2")
                    .password("member2pass")
                    .email("member2@example.com")
                    .totalPoint(70)
                    .role(MemberRole.USER)
                    .build();
            memberRepository.save(member2);

            Member member3 = Member.builder()
                    .username("member3")
                    .password("member3pass")
                    .email("member3@example.com")
                    .totalPoint(100)
                    .role(MemberRole.USER)
                    .build();
            memberRepository.save(member3);

            Member member4 = Member.builder()
                    .username("member4")
                    .password("member4pass")
                    .email("member4@example.com")
                    .totalPoint(90)
                    .role(MemberRole.USER)
                    .build();
            memberRepository.save(member4);

            Member member5 = Member.builder()
                    .username("member5")
                    .password("member5pass")
                    .email("member5@example.com")
                    .totalPoint(60)
                    .role(MemberRole.USER)
                    .build();
            memberRepository.save(member5);

        Member admin = Member.builder()
                .username("admin")
                .password("adminpass")
                .email("admin@example.com")
                .totalPoint(0)
                .role(MemberRole.ADMIN)
                .build();
        memberRepository.save(admin);

        RecycleBin recycleBin = RecycleBin.builder()//35.888553, 128.610584
                .latitude(35.888553)
                .longitude(128.610584)
                .build();
        recycleBinRepository.save(recycleBin);

        RecycleBin recycleBin2 = RecycleBin.builder()//35.888553, 128.610584
                .latitude(35.888720)
                .longitude(128.613668)
                .build();
        recycleBinRepository.save(recycleBin2);

        RecycleBin recycleBin3 = RecycleBin.builder()//35.888553, 128.610584
                .latitude(35.8887401)
                .longitude(128.608383)
                .build();
        recycleBinRepository.save(recycleBin3);

        RecycleBin recycleBin4 = RecycleBin.builder()//35.888553, 128.610584
                .latitude(35.891670)
                .longitude(128.611450)
                .build();
        recycleBinRepository.save(recycleBin4);

        RecycleBin recycleBin5 = RecycleBin.builder()//35.888553, 128.610584
                .latitude(35.891162)
                .longitude(128.614445)
                .build();
        recycleBinRepository.save(recycleBin5);

    }
}
