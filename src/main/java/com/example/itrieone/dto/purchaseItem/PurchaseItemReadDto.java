package com.example.itrieone.dto.purchaseItem;

import com.example.itrieone.domain.Item;
import com.example.itrieone.domain.Member;
import com.example.itrieone.dto.item.ItemReadDto;
import com.example.itrieone.dto.member.MemberReadDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PurchaseItemReadDto {
    private ItemReadDto itemReadDto;
    private MemberReadDto memberReadDto;

    public static PurchaseItemReadDto fromEntity(Item item, Member member) {
        return PurchaseItemReadDto.builder()
                .itemReadDto(ItemReadDto.fromEntity(item))
                .memberReadDto(MemberReadDto.fromEntity(member))
                .build();
    }
}
