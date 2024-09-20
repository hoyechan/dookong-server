package com.example.itrieone.dto.point;

import com.example.itrieone.domain.Item;
import com.example.itrieone.domain.Member;
import com.example.itrieone.domain.Point;
import com.example.itrieone.dto.item.ItemReadDto;
import com.example.itrieone.dto.member.MemberReadDto;
import com.example.itrieone.dto.purchaseItem.PurchaseItemReadDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PointReadDto {

    private Long id;
    private int pointValue;
    private String description;
    private LocalDateTime date;

    public static PointReadDto fromEntity(Point point) {
        return PointReadDto.builder()
                .id(point.getId())
                .date(point.getDate())
                .pointValue(point.getPointValue())
                .description(point.getDescription())
                .build();
    }
}
