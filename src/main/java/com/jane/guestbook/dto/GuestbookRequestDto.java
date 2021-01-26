package com.jane.guestbook.dto;

import com.jane.guestbook.entity.Guestbook;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class GuestbookRequestDto {
    private Long gno;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime regDate, modDate;

    @Builder
    public GuestbookRequestDto(Long gno, String title, String content, String writer, LocalDateTime regDate, LocalDateTime modDate) {
        this.gno = gno;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.regDate = regDate;
        this.modDate = modDate;
    }

    // DTOtoEntity
    public Guestbook toEntity() {
        Guestbook entity = Guestbook.builder()
                                    .gno(gno)
                                    .title(title)
                                    .content(content)
                                    .writer(writer)
                                    .build();
        return entity;
    }
}
