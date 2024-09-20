package com.example.itrieone.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor(force = true) // 기본 생성자 강제 생성
@AllArgsConstructor
public class Item {
    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;
    private int requiredPoints;
    private String description;

    private String category;
    private String pictureUrl;
}
