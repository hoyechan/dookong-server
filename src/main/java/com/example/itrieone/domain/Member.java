package com.example.itrieone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor(force = true) // 기본 생성자 강제 생성
@AllArgsConstructor // 모든 필드를 포함하는 생성자 생성
public class Member {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Point> points = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @Builder.Default
    @JsonIgnore
    private List<Recycling> recyclings = new ArrayList<>();

    private String username;
    private String password;
    private String email;
    private int totalPoint;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    //== 연관관계 편의 메서드 ==//
    public void addRecycling(Recycling recycling) {
        recyclings.add(recycling);
        recycling.setMember(this);
    }

    public void addPoint(Point point){
        points.add(point);
        point.setMember(this);
    }

}
