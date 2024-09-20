package ItriEone.dookong.service;

import ItriEone.dookong.domain.Item;
import ItriEone.dookong.domain.Member;
import ItriEone.dookong.domain.Point;
import ItriEone.dookong.domain.PurchaseItem;
import ItriEone.dookong.repository.ItemRepository;
import ItriEone.dookong.repository.MemberRepository;
import ItriEone.dookong.repository.PointRepository;
import ItriEone.dookong.repository.PurchaseItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final PointRepository pointRepository;

    /**
     * 아이템 생성(ADMIN 만 가능)
     * @param name
     * @param description
     * @param requiredPoints
     * @param category
     * @param pictureUrl
     */
    public Long createItem(String name, String description, int requiredPoints, String category, String pictureUrl) {
        // 새로운 아이템 생성
        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setRequiredPoints(requiredPoints);
        item.setCategory(category);
        item.setPictureUrl(pictureUrl);

        // 아이템 저장
        itemRepository.save(item);
        return item.getId();
    }

    /**
     * 아이템 구매(Point가 Item의 requiredPoint 보다 많이 가지고 있는 USER만 가능)
     * @param memberId
     * @param itemId
     * @return result message
     */
    public String purchaseItem(Long memberId, Long itemId) {
        // 회원 정보 조회
        Optional<Member> findMember = memberRepository.findById(memberId);
        if (findMember.isEmpty()) {
            return "회원이 존재하지 않습니다.";
        }
        Member member = findMember.get();

        // 아이템 정보 조회
        Optional<Item> findItem = itemRepository.findById(itemId);
        if (findItem.isEmpty()) {
            return "아이템이 존재하지 않습니다.";
        }
        Item item = findItem.get();

        // 회원의 총 포인트와 아이템 가격 비교
        if (member.getTotalPoint() < item.getRequiredPoints()) {
            return "포인트가 부족합니다.";
        }

        //구매 아이템 생성
        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.setMember(member);
        purchaseItem.setItem(item);
        purchaseItemRepository.save(purchaseItem);

        // 아이템 구매 처리
        Point point = new Point();
        point.setDate(LocalDateTime.now());
        point.setDescription(item.getName() + " 구매");
        point.setPointValue(-item.getRequiredPoints());

        member.setTotalPoint(member.getTotalPoint() - item.getRequiredPoints());
        member.addPoint(point);

        pointRepository.save(point);

        return "아이템을 성공적으로 구매했습니다.";
    }

}
