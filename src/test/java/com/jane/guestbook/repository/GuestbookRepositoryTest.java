package com.jane.guestbook.repository;

import com.jane.guestbook.entity.Guestbook;
import com.jane.guestbook.entity.QGuestbook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GuestbookRepositoryTest {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Transactional
    @Test
    public void 등록_테스트() {
        //given
        String title = "title";
        String content = "content";
        String writer = "writer";

        //when
        Guestbook guestbook = guestbookRepository.save(new Guestbook(title, content, writer));

        // then
        assertNotNull(guestbook);
        assertEquals(title, guestbook.getTitle());
        assertEquals(content, guestbook.getContent());
        assertEquals(writer, guestbook.getWriter());
    }

    @Test
    public void Dummies_등록_테스트() { // 다음 테스트들을 위해 먼저 실행
        String title = "Title";
        String content = "Content";
        String writer = "user";

        IntStream.rangeClosed(1, 300).forEach(i -> {
            guestbookRepository.save(Guestbook.builder()
                                                .title(title + i)
                                                .content(content + i)
                                                .writer(writer + (i % 10))
                                                .build());
        });
    }

    @Test
    public void 수정시간_테스트() {
        //given
        Optional<Guestbook> result = guestbookRepository.findById(300L);
        String changedTitle = "Changed Title";
        String changedContent = "Changed Content";

        //when
        if (result.isPresent()) {
            Guestbook guestbook = result.get();

            guestbook.changeTitle(changedTitle);
            guestbook.changeContent(changedContent);

            guestbookRepository.save(guestbook);
        }

        //then
        System.out.println(result.get().getModDate());
        assertNotEquals(result.get().getModDate(), result.get().getRegDate());
        assertThat(result.get().getModDate()).isAfter(result.get().getRegDate());
    }

    // Querydsl 테스트

    @Test
    public void 단일항목검색_쿼리_테스트() {
        //given
        Sort sort = Sort.by("gno").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);

        QGuestbook qGuestbook = QGuestbook.guestbook; // 동적 처리를 위한 Q도메인 클래스

        String keyword = "1"; // title 에 "1"이라는 글자가 있는 것 검색

        //when
        BooleanBuilder builder = new BooleanBuilder(); // where 문에 들어가는 조건들을 넣어주는 컨테이너
        BooleanExpression expression = qGuestbook.title.contains(keyword);
        builder.and(expression);

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        //then
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
            assertThat(guestbook.getTitle()).contains(keyword);
        });
    }

    @Test
    public void 다중항목검색_쿼리_테스트() {
        //given
        Sort sort = Sort.by("gno").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);

        QGuestbook qGuestbook = QGuestbook.guestbook; // 동적 처리를 위한 Q도메인 클래스

        String keyword = "1"; // title 과 content 에 "1"이라는 글자가 있는 것 검색

        //when
        BooleanBuilder builder = new BooleanBuilder(); // where 문에 들어가는 조건들을 넣어주는 컨테이너

        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        BooleanExpression exContent = qGuestbook.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);

        builder.and(exAll);
        builder.and(qGuestbook.gno.gt(0L)); // gno 가 0 보다 큰 것 검색

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        //then
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
            assertThat(guestbook.getTitle()).contains(keyword);
            assertThat(guestbook.getContent()).contains(keyword);
            assertThat(guestbook.getGno()).isGreaterThan(0L);
        });
    }
}
