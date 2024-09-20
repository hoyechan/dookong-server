package ItriEone.dookong.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Recycling {
    @Id @GeneratedValue
    @Column(name = "RECYCLING_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToOne
    @JoinColumn(name = "POINT_ID")
    private Point point;

    private String mediaUrl;

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
