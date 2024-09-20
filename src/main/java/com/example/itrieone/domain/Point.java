package com.example.itrieone.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Point {
    @Id @GeneratedValue
    @Column(name = "POINT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(name = "POINT_VALUE")
    private int pointValue;
    private String description;
    private LocalDateTime date;

    public void setMember(Member member) {
        this.member = member;
        if (!member.getPoints().contains(this)) {
            member.getPoints().add(this);
        }
    }
}
