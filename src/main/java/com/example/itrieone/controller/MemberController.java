package com.example.itrieone.controller;

import com.example.itrieone.ErrorResponse;
import com.example.itrieone.dto.member.MemberReadDto;
import com.example.itrieone.dto.member.MemberRegisterRequestDto;
import com.example.itrieone.dto.member.MemberLoginRequestDto;
import com.example.itrieone.exception.DuplicateMemberException;
import com.example.itrieone.exception.InvalidLoginException;
import com.example.itrieone.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    // 회원 가입
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody MemberRegisterRequestDto registerRequestDto) {
        try {
            MemberReadDto memberReadDto = memberService.register(registerRequestDto);
            return ResponseEntity.ok(memberReadDto);
        } catch (DuplicateMemberException e) {
            // ErrorResponse를 사용하여 더 구조화된 오류 응답 반환
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("이미 존재하는 사용자입니다."));
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginRequestDto loginRequestDto) {
        try {
            MemberReadDto memberReadDto = memberService.login(loginRequestDto);
            return ResponseEntity.ok(memberReadDto);
        } catch (InvalidLoginException e) {
            // ErrorResponse를 사용하여 더 구조화된 오류 응답 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("로그인 정보가 올바르지 않습니다."));
        }
    }



    // 특정 회원 정보 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberReadDto> getMemberById(@PathVariable Long memberId) {
        MemberReadDto memberReadDto = memberService.getMemberById(memberId);
        return ResponseEntity.ok(memberReadDto);
    }

}
