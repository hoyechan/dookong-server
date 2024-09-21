package com.example.itrieone.controller;

import com.example.itrieone.domain.Member;
import com.example.itrieone.dto.item.ItemCreateRequestDto;
import com.example.itrieone.dto.item.ItemReadDto;
import com.example.itrieone.dto.member.MemberLoginRequestDto;
import com.example.itrieone.dto.member.MemberReadDto;
import com.example.itrieone.dto.member.MemberRegisterRequestDto;
import com.example.itrieone.dto.purchaseItem.PurchaseItemReadDto;
import com.example.itrieone.dto.purchaseItem.PurchaseItemRequestDto;
import com.example.itrieone.dto.recycling.RecyclingReadDto;
import com.example.itrieone.dto.recycling.RecyclingSubmitRequestDto;
import com.example.itrieone.dto.point.PointReadDto;
import com.example.itrieone.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FullFlowIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    MemberRepository memberRepository;

    private Long memberId;
    private Long memberId2;
    private Long itemId;

    @BeforeEach
    public void setUp() {
        // 회원 가입
        MemberRegisterRequestDto registerRequest = new MemberRegisterRequestDto();
        registerRequest.setUsername("testUser");
        registerRequest.setPassword("password");
        registerRequest.setEmail("testEmail@example.com");

        ResponseEntity<MemberReadDto> registerResponse = restTemplate.postForEntity("/api/members/register", registerRequest, MemberReadDto.class);
        assertThat(registerResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        MemberRegisterRequestDto registerRequest2 = new MemberRegisterRequestDto();
        registerRequest2.setUsername("testUser2");
        registerRequest2.setPassword("password2");
        registerRequest2.setEmail("testEmail2@example.com");

        ResponseEntity<MemberReadDto> registerResponse2 = restTemplate.postForEntity("/api/members/register", registerRequest2, MemberReadDto.class);
        assertThat(registerResponse2.getStatusCode()).isEqualTo(HttpStatus.OK);


    }

    @Test
    public void testFullFlow() {
        // 로그인
        MemberLoginRequestDto loginRequest = new MemberLoginRequestDto();
        loginRequest.setEmail("testEmail@example.com");
        loginRequest.setPassword("password");

        ResponseEntity<MemberReadDto> loginResponse = restTemplate.postForEntity("/api/members/login", loginRequest, MemberReadDto.class);
        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        memberId = loginResponse.getBody().getMemberId();

        MemberLoginRequestDto loginRequest2 = new MemberLoginRequestDto();
        loginRequest2.setEmail("testEmail@example.com");
        loginRequest2.setPassword("password");

        ResponseEntity<MemberReadDto> loginResponse2 = restTemplate.postForEntity("/api/members/login", loginRequest2, MemberReadDto.class);
        assertThat(loginResponse2.getStatusCode()).isEqualTo(HttpStatus.OK);
        memberId2 = loginResponse2.getBody().getMemberId();

        // 포인트를 1200으로 설정 (테스트에서는 포인트 수정을 위한 별도 엔드포인트가 있다고 가정)
        // 포인트를 1200으로 설정
        Optional<Member> findMember = memberRepository.findById(memberId);
        findMember.ifPresent(member -> {
            member.setTotalPoint(1200);
            memberRepository.save(member); // 변경사항을 데이터베이스에 저장
        });

        // 분리수거 제출
        RecyclingSubmitRequestDto recyclingSubmitRequest = new RecyclingSubmitRequestDto();
        recyclingSubmitRequest.setMemberId(memberId);
        recyclingSubmitRequest.setBeforePictureUrl("Test Recycling Submission");
        recyclingSubmitRequest.setAfterPictureUrl("Test Recycling Submission");

        ResponseEntity<RecyclingReadDto> recyclingSubmitResponse = restTemplate.postForEntity("/api/recycling/submit", recyclingSubmitRequest, RecyclingReadDto.class);
        assertThat(recyclingSubmitResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Long recyclingId = recyclingSubmitResponse.getBody().getRecyclingId();

        RecyclingSubmitRequestDto recyclingSubmitRequest2 = new RecyclingSubmitRequestDto();
        recyclingSubmitRequest2.setMemberId(memberId2);
        recyclingSubmitRequest2.setBeforePictureUrl("Test Recycling Submission");
        recyclingSubmitRequest2.setAfterPictureUrl("Test Recycling Submission");

        ResponseEntity<RecyclingReadDto> recyclingSubmitResponse2 = restTemplate.postForEntity("/api/recycling/submit", recyclingSubmitRequest2, RecyclingReadDto.class);
        assertThat(recyclingSubmitResponse2.getStatusCode()).isEqualTo(HttpStatus.OK);
        Long recyclingId2 = recyclingSubmitResponse2.getBody().getRecyclingId();

        // 분리수거 승인
        ResponseEntity<String> approveResponse = restTemplate.postForEntity("/api/recycling/reject/" + recyclingId, null, String.class);
        assertThat(approveResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(approveResponse.getBody()).isEqualTo("분리수거 활동이 거절되었습니다.");

        // 아이템 등록
        ItemCreateRequestDto itemCreateRequest = new ItemCreateRequestDto();
        itemCreateRequest.setName("Test Item");
        itemCreateRequest.setCategory("Electronics");
        itemCreateRequest.setRequiredPoints(1000);
        itemCreateRequest.setDescription("You can use it anyway");
        itemCreateRequest.setPictureUrl("pictureUrl");

        ResponseEntity<ItemReadDto> itemCreateResponse = restTemplate.postForEntity("/api/items/create", itemCreateRequest, ItemReadDto.class);
        assertThat(itemCreateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        itemId = itemCreateResponse.getBody().getItemId();

        // 아이템 구매
        PurchaseItemRequestDto purchaseItemRequest = new PurchaseItemRequestDto();
        purchaseItemRequest.setMemberId(memberId);
        purchaseItemRequest.setItemId(itemId);

        ResponseEntity<PurchaseItemReadDto> purchaseResponse = restTemplate.postForEntity("/api/items/purchase", purchaseItemRequest, PurchaseItemReadDto.class);
        assertThat(purchaseResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}

