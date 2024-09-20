package ItriEone.dookong.service;

import ItriEone.dookong.domain.Member;
import ItriEone.dookong.repository.MemberRepository;
import ItriEone.dookong.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PointService {
    private final PointRepository pointRepository;
    private final MemberRepository memberRepository;

    /**
     * 이달의 point 가져오기
     * @param memberId
     * @return
     */
    public int getMonthlyPoints(Long memberId) {
        Integer result = pointRepository.findMonthlyPointsByMemberId(memberId);
        return result != null ? result : 0; // null인 경우 0을 반환
    }

    /**
     * 모든 member의 이번달 point 순위
     * @return
     */
    public List<Member> getMonthlyAllRanking() {
        return memberRepository.findMonthlyRanking();
    }

    /**
     * user의 이번달 point 순위
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






}
