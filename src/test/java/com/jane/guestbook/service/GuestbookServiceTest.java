package com.jane.guestbook.service;

import com.jane.guestbook.dto.GuestbookRequestDto;
import com.jane.guestbook.dto.PageMapper;
import com.jane.guestbook.dto.PageRequestDto;
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
    void register_등록테스트() {
        //given
        String title = "title";
        String content = "content";
        String writer = "user";

        GuestbookRequestDto payload = GuestbookRequestDto.builder()
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

    @Test
    void getList_목록조회테스트() {
        PageRequestDto pageRequestDto = PageRequestDto.builder()
                                                        .page(1)
                                                        .size(10)
                                                        .build();

        PageMapper<GuestbookRequestDto, Guestbook> resultDto = guestbookService.getList(pageRequestDto);

        System.out.println("-----------------현재 페이지 DTO LIST-----------------");
        for(GuestbookRequestDto payload : resultDto.getDtoList()) {
            System.out.println(payload);
        }

        System.out.println("----------------------------------------------------");
        System.out.println("이전 페이지로 가는 링크 필요한가? PREV: " + resultDto.isPrev());
        System.out.println("다음 페이지로 가는 링크 필요한가? NEXT: " + resultDto.isNext());
        System.out.println("전체 페이지 수 TOTAL: " + resultDto.getTotalPage());

        System.out.println("----------------화면에 출력될 페이지 번호---------------");
        resultDto.getPageList().forEach(i -> System.out.println(i));
    }
}