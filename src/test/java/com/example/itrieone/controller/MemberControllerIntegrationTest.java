package com.example.itrieone.controller;

import com.example.itrieone.dto.member.MemberRegisterRequestDto;
import com.example.itrieone.dto.member.MemberLoginRequestDto;
import com.example.itrieone.dto.member.MemberReadDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class MemberControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        // 모든 사용자를 삭제하거나 데이터베이스를 초기화합니다.
        //restTemplate.delete("/api/members/deleteAll"); // 실제 엔드포인트를 구현해야 합니다.
        MemberRegisterRequestDto request = new MemberRegisterRequestDto();
        request.setUsername("testMember");
        request.setPassword("password");
        request.setEmail("testEmail@example.com");

        ResponseEntity<MemberReadDto> response = restTemplate.postForEntity("/api/members/register", request, MemberReadDto.class);
    }

    @Test
    public void testRegister() {
        MemberRegisterRequestDto request = new MemberRegisterRequestDto();
        request.setUsername("testUser");
        request.setPassword("password");
        request.setEmail("testEmail");

        ResponseEntity<MemberReadDto> response = restTemplate.postForEntity("/api/members/register", request, MemberReadDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getUsername()).isEqualTo("testUser");
    }

    @Test
    public void testLogin() {
        // 먼저 회원 등록을 진행하여 로그인 시도에 필요한 사용자를 만듭니다.
        MemberRegisterRequestDto registerRequest = new MemberRegisterRequestDto();
        registerRequest.setUsername("loginUser");
        registerRequest.setPassword("password");
        registerRequest.setEmail("loginEmail");
        restTemplate.postForEntity("/api/members/register", registerRequest, MemberReadDto.class);

        // 로그인 시도
        MemberLoginRequestDto loginRequest = new MemberLoginRequestDto();
        loginRequest.setEmail("loginEmail");
        loginRequest.setPassword("password");

        ResponseEntity<MemberReadDto> response = restTemplate.postForEntity("/api/members/login", loginRequest, MemberReadDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getUsername()).isEqualTo("loginUser");
    }

    @Test
    public void testGetMemberById() {
        // 먼저 회원을 로그인하여 ID를 가져옴
        MemberLoginRequestDto loginRequest = new MemberLoginRequestDto();
        loginRequest.setEmail("testEmail@example.com");
        loginRequest.setPassword("password");

        ResponseEntity<MemberReadDto> loginResponse = restTemplate.postForEntity("/api/members/login", loginRequest, MemberReadDto.class);

        // 회원 ID로 특정 회원 정보 조회 테스트
        Long memberId = loginResponse.getBody().getMemberId();
        ResponseEntity<MemberReadDto> response = restTemplate.getForEntity("/api/members/" + memberId, MemberReadDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getUsername()).isEqualTo("testMember");
    }

}
