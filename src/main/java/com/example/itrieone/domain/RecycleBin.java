package com.example.itrieone.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class RecycleBin {
    @Id
    @GeneratedValue
    @Column(name = "RECYCLE_BIN_ID")
    private Long id;

    private String name; // 분리수거통의 이름 또는 설명

    private double latitude; // 위도
    private double longitude; // 경도

    private String address; // 주소 (선택 사항)

    // 기타 필요한 필드...
}
