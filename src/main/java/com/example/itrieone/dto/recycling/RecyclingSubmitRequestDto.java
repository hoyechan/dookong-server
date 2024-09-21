package com.example.itrieone.dto.recycling;

import com.example.itrieone.domain.Member;
import com.example.itrieone.domain.Recycling;
import com.example.itrieone.domain.RecyclingStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RecyclingSubmitRequestDto {
    private Long memberId;

//    private String beforePictureUrl;
//    private String afterPictureUrl;

    public Recycling toEntity(){
        return Recycling.builder()
                .recyclingStatus(RecyclingStatus.PENDING)
                .localDateTime(LocalDateTime.now())
                .build();
    }
}
