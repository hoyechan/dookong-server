package com.example.itrieone.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Recycling {
    @Id @GeneratedValue
    @Column(name = "RECYCLING_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

//    @OneToOne
//    @JoinColumn(name = "POINT_ID")
//    private Point point;

    private String beforePictureUrl;
    private String afterPictureUrl;
    private LocalDateTime localDateTime;

    @Enumerated(EnumType.STRING)
    private RecyclingStatus recyclingStatus; //PENDING, APPROVED, REJECTED

    //== 연관관계 편의 메서드 ==//
    public void setMember(Member member) {
        this.member = member;
        if (!member.getRecyclings().contains(this)) {
            member.getRecyclings().add(this);
        }
    }
}
