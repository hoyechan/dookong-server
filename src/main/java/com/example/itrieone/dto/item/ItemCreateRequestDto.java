package com.example.itrieone.dto.item;

import com.example.itrieone.domain.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCreateRequestDto {
    private String name;
    private int requiredPoints;
    private String description;

    private String category;

    public Item toEntity(){
        return Item.builder()
                .name(name)
                .requiredPoints(requiredPoints)
                .description(description)
                .category(category)
                .build();
    }
}
