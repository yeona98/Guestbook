package com.jane.guestbook.dto;

import com.jane.guestbook.entity.Guestbook;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GuestbookMapper {
    private Long gno;
    private String title;
    private String content;
    private String writer;

    @Builder
    public GuestbookMapper(Long gno, String title, String content, String writer) {
        this.gno = gno;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    // EntityToDTO
    public static GuestbookRegisterRequestDto of(Guestbook entity) {
        GuestbookRegisterRequestDto dto = GuestbookRegisterRequestDto.builder()
                                                    .gno(entity.getGno())
                                                    .title(entity.getTitle())
                                                    .content(entity.getContent())
                                                    .writer(entity.getWriter())
                                                    .regDate(entity.getRegDate())
                                                    .modDate(entity.getModDate())
                                                    .build();
        return dto;
    }
}
