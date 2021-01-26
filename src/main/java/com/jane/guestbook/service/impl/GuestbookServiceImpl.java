package com.jane.guestbook.service.impl;

import com.jane.guestbook.entity.Guestbook;
import com.jane.guestbook.dto.GuestbookDTO;
import com.jane.guestbook.repository.GuestbookRepository;
import com.jane.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class GuestbookServiceImpl implements GuestbookService {
    private final GuestbookRepository guestbookRepository;

    @Override
    public Long register(GuestbookDTO payload) {
        log.info("DTO to Entity");
        log.info(payload);
        log.info(payload.toEntity());

        return guestbookRepository.save(payload.toEntity()).getGno();
    }
}
