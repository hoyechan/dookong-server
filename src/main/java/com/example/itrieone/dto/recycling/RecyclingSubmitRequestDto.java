package com.example.itrieone.dto.recycling;

import com.example.itrieone.domain.Member;
import com.example.itrieone.domain.Recycling;
import com.example.itrieone.domain.RecyclingStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecyclingSubmitRequestDto {
    private Long memberId;
    private String mediaUrl;

    public Recycling toEntity(){
        return Recycling.builder()
                .mediaUrl(mediaUrl)
                .recyclingStatus(RecyclingStatus.PENDING)
                .build();
    }
}
