package com.guestbook.service;

import com.guestbook.dto.GuestbookDTO;
import com.guestbook.dto.PageRequestDTO;
import com.guestbook.dto.PageResultDTO;
import com.guestbook.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuestbookServiceTest {

    @Autowired
    private GuestbookService guestbookService;

    @Test
    public void register() {
        GuestbookDTO dto = GuestbookDTO.builder()
                .title("Sample title...")
                .content("Sample content...")
                .writer("user0")
                .build();

        System.out.println(guestbookService.register(dto));
    }

    @Test
    public void getList() {
        PageRequestDTO requestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = guestbookService.getList(requestDTO);

        System.out.println("PREV: " + resultDTO.isPrev());
        System.out.println("PREV: " + resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());

        System.out.println("----------------------------------------");
        resultDTO.getDtoList()
                .forEach(i -> System.out.println(i));

        System.out.println("----------------------------------------");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }
}