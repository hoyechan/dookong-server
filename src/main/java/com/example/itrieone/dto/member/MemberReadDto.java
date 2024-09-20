package com.example.itrieone.dto.member;

import com.example.itrieone.domain.Member;
import com.example.itrieone.domain.MemberRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberReadDto {
    private Long memberId;
    private String username;
    private String email;
    private int totalPoint;
    private MemberRole role;
    //    private List<Point> points = new ArrayList<>();
    //    private List<Recycling> recyclings = new ArrayList<>();


    public static MemberReadDto fromEntity(Member member) {
        return MemberReadDto.builder()
                .memberId(member.getId())
                .username(member.getUsername())
                .email(member.getEmail())
                .totalPoint(member.getTotalPoint())
                .role(member.getRole()).build();
    }
}
