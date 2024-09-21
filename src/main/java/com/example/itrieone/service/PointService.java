package com.example.itrieone.service;

import com.example.itrieone.domain.Member;
import com.example.itrieone.domain.MemberRole;
import com.example.itrieone.domain.Point;
import com.example.itrieone.dto.member.MemberReadDto;
import com.example.itrieone.dto.point.PointReadDto;
import com.example.itrieone.repository.MemberRepository;
import com.example.itrieone.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PointService {
    private final PointRepository pointRepository;
    private final MemberRepository memberRepository;

    /**
     * 특정 회원의 이달의 point 가져오기
     * @param memberId
     * @return
     */
    public int getMonthlyPoints(Long memberId) {
        Integer result = pointRepository.findMonthlyPointsByMemberId(memberId);
        return result != null ? result : 0; // null인 경우 0을 반환
    }

    /**
     * 모든 회원의 이번 달 point 순위 가져오기
     * @return List<Member>
     */
    public List<Member> getMonthlyAllRanking() {
        return memberRepository.findMonthlyRanking();
    }


    /**
     * 특정 회원의 이번 달 point 순위 가져오기
     * @param memberId
     * @return
     */
    public int getMonthlyOneRanking(Long memberId) {
        List<Member> monthlyRanking = getMonthlyAllRanking();

        // 초기 값 설정
        int rank = 1; // 첫 번째 순위부터 시작
        int count = 1; // 현재 사용자를 추적하는 인덱스
        int previousPoints = -1; // 이전 사용자의 포인트, 초기값은 비교를 위해 음수 사용

        int userRanking = -1; // 초기값, 사용자의 순위를 찾을 때까지 -1로 유지

        for (int i = 0; i < monthlyRanking.size(); i++) {
            Member member = monthlyRanking.get(i);
            int currentPoints = getMonthlyPoints(member.getId());

            // 같은 포인트를 가진 경우 같은 순위를 유지
            if (currentPoints == previousPoints) {
                if (member.getId().equals(memberId)) {
                    userRanking = rank; // 공동 순위인 경우에도 같은 순위로 설정
                    break;
                }
            } else {
                // 새로운 순위로 갱신
                rank = count;
                if (member.getId().equals(memberId)) {
                    userRanking = rank;
                    break;
                }
            }

            // 상태 업데이트
            previousPoints = currentPoints;
            count++; // 현재 처리 중인 사용자의 수 증가
        }

        // 꼴찌인 경우 처리
        if (userRanking == -1) {
            userRanking = rank;
        }

        return userRanking;
    }

    /**
     * 특정 회원의 전체 포인트 내역 조회
     * @param memberId
     * @return List<PointReadDto>
     */
    public List<PointReadDto> getAllPointsByMember(Long memberId) {
        List<Point> points = pointRepository.findByMemberId(memberId);
        return points.stream()
                .map(PointReadDto::fromEntity)
                .collect(Collectors.toList());
    }


    /**
     * 전체 회원의 이번 달 포인트 순위 조회 (ADMIN 제외)
     * @return List<MemberReadDto>
     */
    public List<MemberReadDto> getAllRanking() {
        List<Member> monthlyRanking = memberRepository.findMonthlyRanking();

        return monthlyRanking.stream()
                .map(MemberReadDto::fromEntity)
                .collect(Collectors.toList());
    }



}
