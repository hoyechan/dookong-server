package ItriEone.dookong.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<Point> points = new ArrayList<>();

    @OneToMany(mappedBy = "member")
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
