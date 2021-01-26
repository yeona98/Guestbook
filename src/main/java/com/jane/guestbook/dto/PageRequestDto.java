package com.jane.guestbook.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
public class PageRequestDto {
    private int page;
    private int size;

    public PageRequestDto() {
        this.page = 1;
        this.size = 10;
    }

    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page - 1, size, sort); // 페이지 번호가 0부터 시작
    }

    @Builder
    public PageRequestDto(int page, int size) {
        this.page = page;
        this.size = size;
    }
}
