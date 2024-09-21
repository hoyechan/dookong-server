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
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    @Transactional(rollbackFor = Exception.class)
    public ItemReadDto createItem(ItemCreateRequestDto itemCreateRequestDto, MultipartFile image) throws IOException {
        try {// 파일을 저장할 경로
            String uploadDir = "uploads/";
            Path uploadPath = Paths.get(uploadDir);

            // uploads 폴더가 없으면 생성
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath); // 폴더가 없으면 생성
            }

            // 이미지 파일 저장
            String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // 파일 저장 후 URL 생성
            String imageUrl = "/uploads/" + fileName;

            // 생성된 이미지 URL을 itemCreateRequestDto에 설정
            Item item = itemCreateRequestDto.toEntity();
            item.setPictureUrl(imageUrl);

            // Item 객체 생성 및 저장
            Item savedItem = itemRepository.save(item);

            // ItemReadDto로 변환하여 반환
            return ItemReadDto.fromEntity(savedItem);

        }catch(Exception e){
            System.err.println("예외 발생: " + e.getMessage());
            e.printStackTrace();
            throw e;  // 예외를 다시 던져 트랜잭션 롤백 확인
        }
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

    @Transactional
    public List<ItemReadDto> getAllItems() {
        List<Item> items = itemRepository.findAll(); // 모든 아이템 가져오기
        return items.stream()
                .map(ItemReadDto::fromEntity) // 각 Item을 ItemReadDto로 변환
                .collect(Collectors.toList()); // List<ItemReadDto>로 변환
    }

}
