package com.example.itrieone.dto.item;

import com.example.itrieone.domain.Item;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemReadDto {
    private Long itemId;
    private String name;
    private int requiredPoints;
    private String description;

    private String category;
    private String pictureUrl;

    public static ItemReadDto fromEntity(Item item) {
        return ItemReadDto.builder()
                .itemId(item.getId())
                .name(item.getName())
                .requiredPoints(item.getRequiredPoints())
                .description(item.getDescription())
                .category(item.getCategory())
                .pictureUrl(item.getPictureUrl())
                .build();
    }
}
