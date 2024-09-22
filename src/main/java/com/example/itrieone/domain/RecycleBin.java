package com.example.itrieone.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor(force = true) // 기본 생성자 강제 생성
@AllArgsConstructor
public class RecycleBin {
    @Id
    @GeneratedValue
    @Column(name = "RECYCLE_BIN_ID")
    private Long id;

    private double latitude; // 위도
    private double longitude; // 경도

    // 기타 필요한 필드...
}
