package ItriEone.dookong.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Message {
    @Id @GeneratedValue
    @Column(name = "MESSAGE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SENDER_ID")
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "RECEIVER_ID")
    private Member receiver;

    private LocalDateTime date;
    private String content;
}
