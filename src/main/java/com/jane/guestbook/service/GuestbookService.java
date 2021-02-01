package com.jane.guestbook.service;

import com.jane.guestbook.dto.GuestbookRegisterRequestDto;
import com.jane.guestbook.dto.PageMapper;
import com.jane.guestbook.dto.PageRequestDto;
import com.jane.guestbook.entity.Guestbook;

public interface GuestbookService {
    // 등록 처리
    Long register(GuestbookRegisterRequestDto payload);
    // 목록 처리
    PageMapper<GuestbookRegisterRequestDto, Guestbook> getList(PageRequestDto payload);
    // 조회 처리
    GuestbookRegisterRequestDto read(Long gno);
    // 삭제 처리
    void remove(Long gno);
    // 수정 처리
    void modify(GuestbookRegisterRequestDto payload);
}
