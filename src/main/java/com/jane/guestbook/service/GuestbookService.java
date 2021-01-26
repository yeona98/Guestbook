package com.jane.guestbook.service;

import com.jane.guestbook.dto.GuestbookDTO;

public interface GuestbookService {
    Long register(GuestbookDTO payload);
}
