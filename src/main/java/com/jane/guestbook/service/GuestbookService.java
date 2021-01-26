package com.jane.guestbook.service;

import com.jane.guestbook.dto.GuestbookRequestDto;
import com.jane.guestbook.dto.PageMapper;
import com.jane.guestbook.dto.PageRequestDto;
import com.jane.guestbook.entity.Guestbook;

public interface GuestbookService {
    // 등록 처리
    Long register(GuestbookRequestDto payload);
    // 목록 처리
    PageMapper<GuestbookRequestDto, Guestbook> getList(PageRequestDto payload);
}
