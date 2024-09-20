package com.example.itrieone.service;

import com.example.itrieone.domain.Item;
import com.example.itrieone.domain.Member;
import com.example.itrieone.domain.Point;
import com.example.itrieone.domain.PurchaseItem;
import com.example.itrieone.dto.purchaseItem.PurchaseItemRequestDto;
import com.example.itrieone.dto.item.ItemCreateRequestDto;
import com.example.itrieone.dto.item.ItemReadDto;
import com.example.itrieone.dto.purchaseItem.PurchaseItemReadDto;
import com.example.itrieone.exception.InsufficientPointsException;
import com.example.itrieone.repository.ItemRepository;
import com.example.itrieone.repository.MemberRepository;
import com.example.itrieone.repository.PointRepository;
import com.example.itrieone.repository.PurchaseItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final PointRepository pointRepository;

    /**
     * 아이템 생성(ADMIN만 가능)
     * @param itemCreateRequestDto
     * @return
     */
    @Transactional
    public ItemReadDto createItem(ItemCreateRequestDto itemCreateRequestDto) {

        Item item = itemRepository.save(itemCreateRequestDto.toEntity());
        return ItemReadDto.fromEntity(item);
    }

    /**
     * 아이템 구매
     * @param purchaseItemRequestDto
     * @return
     */
    @Transactional
    public PurchaseItemReadDto purchaseItem(PurchaseItemRequestDto purchaseItemRequestDto) {
        // 회원 정보 조회
        Optional<Member> findMember = memberRepository.findById(purchaseItemRequestDto.getMemberId());
        if (findMember.isEmpty()) {
            throw new NoSuchElementException("존재하지 않는 회원입니다.");
        }
        Member member = findMember.get();

        // 아이템 정보 조회
        Optional<Item> findItem = itemRepository.findById(purchaseItemRequestDto.getItemId());
        if (findItem.isEmpty()) {
            throw new NoSuchElementException("존재하지 않는 아이템입니다.");
        }
        Item item = findItem.get();

        // 회원의 총 포인트와 아이템 가격 비교
        if (member.getTotalPoint() < item.getRequiredPoints()) {
            throw new InsufficientPointsException("포인트가 부족합니다.");
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

        return PurchaseItemReadDto.fromEntity(item, member);
    }

}
