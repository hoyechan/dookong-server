package com.example.itrieone.dto.recyclebin;

import com.example.itrieone.domain.RecycleBin;
import com.example.itrieone.dto.member.MemberReadDto;
import com.example.itrieone.dto.recycling.RecyclingReadDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecycleBinReadDto {
    private Long recycleBinId;
    private double latitude; // 위도
    private double longitude;

    public static RecycleBinReadDto fromEntity(RecycleBin recycleBin) {
        return RecycleBinReadDto.builder()
                .recycleBinId(recycleBin.getId())
                .latitude(recycleBin.getLatitude())
                .longitude(recycleBin.getLongitude())
                .build();
    }
}