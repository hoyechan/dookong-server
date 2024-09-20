package com.example.itrieone.controller;

import com.example.itrieone.dto.purchaseItem.PurchaseItemRequestDto;
import com.example.itrieone.dto.item.ItemCreateRequestDto;
import com.example.itrieone.dto.item.ItemReadDto;
import com.example.itrieone.dto.purchaseItem.PurchaseItemReadDto;
import com.example.itrieone.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    //아이템 생성
    @PostMapping("/create")
    public ResponseEntity<ItemReadDto> createItem(@RequestBody ItemCreateRequestDto itemCreateRequestDto) {
        ItemReadDto itemReadDto = itemService.createItem(itemCreateRequestDto);
        return ResponseEntity.ok(itemReadDto);
    }

    //아이템 구매
    @PostMapping("/purchase")
    public ResponseEntity<PurchaseItemReadDto> purchaseItem(@RequestBody PurchaseItemRequestDto purchaseItemRequestDto) {
        PurchaseItemReadDto purchaseItemReadDto = itemService.purchaseItem(purchaseItemRequestDto);
        return ResponseEntity.ok(purchaseItemReadDto);
    }


}
