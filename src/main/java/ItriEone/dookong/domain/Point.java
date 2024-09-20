package ItriEone.dookong.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

@Entity
@Getter @Setter
public class Point {
    @Id @GeneratedValue
    @Column(name = "POINT_ID")
    private Long id;

    private int totalPoint;
    private String description;

}
