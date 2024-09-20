package com.example.itrieone.dto.recycling;

import com.example.itrieone.domain.Item;
import com.example.itrieone.domain.Member;
import com.example.itrieone.domain.Recycling;
import com.example.itrieone.domain.RecyclingStatus;
import com.example.itrieone.dto.item.ItemReadDto;
import com.example.itrieone.dto.member.MemberReadDto;
import com.example.itrieone.dto.purchaseItem.PurchaseItemReadDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecyclingReadDto {
    private Long recyclingId;
    private MemberReadDto member;
    private String mediaUrl;
    private RecyclingStatus recyclingStatus;

    public static RecyclingReadDto fromEntity(Recycling recycling) {
        return RecyclingReadDto.builder()
                .recyclingId(recycling.getId())
                .member(MemberReadDto.fromEntity(recycling.getMember())) // DTO 변환 사용
                .mediaUrl(recycling.getMediaUrl())
                .recyclingStatus(recycling.getRecyclingStatus())
                .build();
    }
}
