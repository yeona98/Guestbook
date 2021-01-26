package com.jane.guestbook.service;

import com.jane.guestbook.dto.GuestbookDTO;
import com.jane.guestbook.entity.Guestbook;
import com.jane.guestbook.repository.GuestbookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuestbookServiceTest {
    @Autowired
    private GuestbookRepository guestbookRepository;
    @Autowired
    private GuestbookService guestbookService;

    // https://cheese10yun.github.io/spring-guide-test-1/

    @Test
    void register_테스트() {
        //given
        String title = "title";
        String content = "content";
        String writer = "user";

        GuestbookDTO payload = GuestbookDTO.builder()
                                            .title(title)
                                            .content(content)
                                            .writer(writer)
                                            .build();

        //when
        Long guestbookGno = guestbookService.register(payload);
        Guestbook guestbook = guestbookRepository.findById(guestbookGno).get();

        //then
        assertNotNull(guestbook);
        assertEquals(title, guestbook.getTitle());
        assertEquals(content, guestbook.getContent());
        assertEquals(writer, guestbook.getWriter());

        assertThat(guestbook).isNotNull();
        assertThat(guestbook.getTitle()).isEqualTo(title);
        assertThat(guestbook.getContent()).isEqualTo(content);
        assertThat(guestbook.getWriter()).isEqualTo(writer);
    }
}