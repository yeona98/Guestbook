package com.jane.guestbook.repository;

import com.jane.guestbook.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
}
