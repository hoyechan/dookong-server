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

    private LocalDateTime date;
    private String mediaUrl;

    @Enumerated(EnumType.STRING)
    private RecyclingStatus recyclingStatus;
}
