package com.example.itrieone.controller;

import com.example.itrieone.domain.Member;
import com.example.itrieone.dto.member.MemberReadDto;
import com.example.itrieone.dto.point.PointReadDto;
import com.example.itrieone.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/points")
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    /**
     * 특정 회원의 이달의 포인트 가져오기
     * @param memberId
     * @return ResponseEntity<Integer>
     */
    @GetMapping("/monthly/{memberId}")
    public ResponseEntity<Integer> getMonthlyPoints(@PathVariable("memberId") Long memberId) {
        int monthlyPoints = pointService.getMonthlyPoints(memberId);
        return ResponseEntity.ok(monthlyPoints);
    }

    /**
     * 특정 회원의 이번 달 포인트 순위 가져오기
     * @param memberId
     * @return ResponseEntity<Integer>
     */
    @GetMapping("/monthly-ranking/{memberId}")
    public ResponseEntity<Integer> getMonthlyOneRanking(@PathVariable("memberId") Long memberId) {
        int rank = pointService.getMonthlyOneRanking(memberId);
        return ResponseEntity.ok(rank);
    }

    /**
     * 특정 회원의 전체 포인트 사용 내역 가져오기
     * @param memberId
     * @return
     */
    @GetMapping("/all/{memberId}")
    public ResponseEntity<List<PointReadDto>> getAllPointsByMember(@PathVariable("memberId") Long memberId) {
        List<PointReadDto> pointList = pointService.getAllPointsByMember(memberId);
        return ResponseEntity.ok(pointList);
    }

    /**
     * 모든 회원의 포인트 순위 가져오기
     * @return
     */
    @GetMapping("/all-ranking")
    public ResponseEntity<List<MemberReadDto>> getAllRanking() {
        List<MemberReadDto> rankingList = pointService.getAllRanking();
        return ResponseEntity.ok(rankingList);
    }
}

