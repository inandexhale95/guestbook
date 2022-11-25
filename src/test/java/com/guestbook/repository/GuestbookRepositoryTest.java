package com.guestbook.repository;

import com.guestbook.entity.Guestbook;
import com.guestbook.entity.QGuestbook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

    @Test
    public void testQuery1() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        // 동적 처리를 위해 Q클래스를 얻어온다, Q도메인 클래스를 이용하면 엔티티 클래스에 선언된 title, content 같은 필드를 변수로 활용 가능
        QGuestbook qGuestbook = QGuestbook.guestbook;

        // builder는 where 문에 들어가는 조건을 넣어주는 컨테이너
        BooleanBuilder builder = new BooleanBuilder();

        String key = "1";

        // com.querydsl.core.types.Predicate 타입만 넣을 수 있다.
        BooleanExpression expression = qGuestbook.title.contains(key);

        // 만들어진 조건 결합
        builder.and(expression);

        Page<Guestbook> page = repository.findAll(builder, pageable);

        page.forEach(i -> System.out.println(i));
    }

    @Test
    public void testQuery2() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;

        BooleanBuilder builder = new BooleanBuilder();

        String key = "1";

        BooleanExpression exTitle = qGuestbook.title.contains(key);
        BooleanExpression exContent = qGuestbook.content.contains(key);

        BooleanExpression exAll = exTitle.or(exContent);

        builder.and(exAll);
        // gno가 0보다 크다라는 조건
        builder.and(qGuestbook.gno.gt(0L));

        Page<Guestbook> page = repository.findAll(builder, pageable);

        page.forEach(i -> System.out.println(i));
    }
}