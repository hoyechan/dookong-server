package com.example.itrieone.controller;

import com.example.itrieone.dto.purchaseItem.PurchaseItemRequestDto;
import com.example.itrieone.dto.item.ItemCreateRequestDto;
import com.example.itrieone.dto.item.ItemReadDto;
import com.example.itrieone.dto.purchaseItem.PurchaseItemReadDto;
import com.example.itrieone.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    //아이템 생성
    // 아이템 생성
    @PostMapping("/create")
    public ResponseEntity<ItemReadDto> createItem(
            @RequestPart("itemData") String itemData, // String으로 받은 후 변환
            @RequestPart("image") MultipartFile image) throws IOException {

        // itemData를 JSON으로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        ItemCreateRequestDto itemCreateRequestDto = objectMapper.readValue(itemData, ItemCreateRequestDto.class);

        // 서비스로 전달하여 아이템 생성
        ItemReadDto itemReadDto = itemService.createItem(itemCreateRequestDto, image);
        return ResponseEntity.ok(itemReadDto);
    }

    //아이템 구매
    @PostMapping("/purchase")
    public ResponseEntity<PurchaseItemReadDto> purchaseItem(@RequestBody PurchaseItemRequestDto purchaseItemRequestDto) {
        PurchaseItemReadDto purchaseItemReadDto = itemService.purchaseItem(purchaseItemRequestDto);
        return ResponseEntity.ok(purchaseItemReadDto);
    }

    // 모든 아이템 리스트 가져오기
    @GetMapping("/all")
    public ResponseEntity<List<ItemReadDto>> getAllItems() {
        List<ItemReadDto> itemReadDtoList = itemService.getAllItems();
        return ResponseEntity.ok(itemReadDtoList);
    }
}
