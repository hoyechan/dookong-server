package com.example.itrieone.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberLoginRequestDto {
    private String email;
    private String password;
}
