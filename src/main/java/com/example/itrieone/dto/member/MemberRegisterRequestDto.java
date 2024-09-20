package com.example.itrieone.dto.member;

import com.example.itrieone.domain.Member;
import com.example.itrieone.domain.MemberRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRegisterRequestDto {
    private String username;
    private String email;
    private String password;

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .email(email)
                .password(password)
                .role(MemberRole.USER)
                .build();
    }
}
