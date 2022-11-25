package com.guestbook.repository;

import com.guestbook.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuestbookRepositoryTest {

    @Autowired
    private GuestbookRepository repository;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 300)
                .forEach(i -> {
                    Guestbook guestbook = Guestbook.builder()
                            .title("Title..." + i)
                            .content("Content..." + i)
                            .writer("Writer..." + i)
                            .build();
                    repository.save(guestbook);
                });
    }

    @Test
    public void updateGuestbook() {
        Optional<Guestbook> optionalGuestbook = repository.findById(300L);

        if (optionalGuestbook.isPresent()) {
            Guestbook guestbook = optionalGuestbook.get();
            guestbook.setTitle("Update title!!!");
            guestbook.setContent("Update content!!!");

            repository.save(guestbook);
        }


    }
}