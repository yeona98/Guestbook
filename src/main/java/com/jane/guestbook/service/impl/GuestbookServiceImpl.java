package com.jane.guestbook.service.impl;

import com.jane.guestbook.dto.GuestbookMapper;
import com.jane.guestbook.dto.GuestbookRequestDto;
import com.jane.guestbook.dto.PageMapper;
import com.jane.guestbook.dto.PageRequestDto;
import com.jane.guestbook.entity.Guestbook;
import com.jane.guestbook.repository.GuestbookRepository;
import com.jane.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Log4j2
@RequiredArgsConstructor
@Service
public class GuestbookServiceImpl implements GuestbookService {
    private final GuestbookRepository guestbookRepository;

    @Override
    public Long register(GuestbookRequestDto payload) {
        log.info("DTO to Entity");
        log.info(payload);
        log.info(payload.toEntity());

        return guestbookRepository.save(payload.toEntity()).getGno();
    }

    @Override
    public PageMapper<GuestbookRequestDto, Guestbook> getList(PageRequestDto payload) {
        Sort sort = Sort.by("gno").descending();
        Pageable pageable = payload.getPageable(sort);

        Page<Guestbook> result = guestbookRepository.findAll(pageable);

        Function<Guestbook, GuestbookRequestDto> fn = (entity -> GuestbookMapper.of(entity)); // of()를 이용해서 java.util.Function 을 생성

        // 이를 Mapper 로 구성
        return new PageMapper<>(result, fn);
    }

    @Override
    public GuestbookRequestDto read(Long gno) {
        Optional<Guestbook> result = guestbookRepository.findById(gno);

        return result.isPresent()? GuestbookMapper.of(result.get()) : null;
    }

    @Override
    public void remove(Long gno) {
        guestbookRepository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookRequestDto payload) {
        Optional<Guestbook> result = guestbookRepository.findById(payload.getGno());

        if (result.isPresent()) {
            Guestbook modifiedGuestbook = result.get();

            // title, content 만 업데이트
            modifiedGuestbook.changeTitle(payload.getTitle());
            modifiedGuestbook.changeContent(payload.getContent());

            guestbookRepository.save(modifiedGuestbook);
        }
    }
}
