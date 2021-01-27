package com.jane.guestbook.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// https://semtax.tistory.com/77
// https://ivvve.github.io/2019/01/13/java/Spring/pagination_3/
// http://devstory.ibksplatform.com/2020/03/spring-boot-jpa-pageable.html

@Getter
@RequiredArgsConstructor
public class PageMapper<DTO, ENTITY> { // 다른 곳에서 가져다 쓸 수 있게 제네릭 타입 사용
    private List<DTO> dtoList;
    private int totalPage;
    private int currentPage;
    private int listSize;
    private int start, end;
    private boolean prev, next;
    private List<Integer> pageNumList; // 페이지 번호 목록

    // Page<ENTITY> 타입으로 받은 페이지 처리 결과(ENTITY 객체들)를 DTO 객체로 변환
    public PageMapper(Page<ENTITY> result, Function<ENTITY, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());

        totalPage = result.getTotalPages();

        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        this.currentPage = pageable.getPageNumber() + 1;
        this.listSize = pageable.getPageSize();

        int currentEnd = (int) (Math.ceil(currentPage/10.0)) * 10;

        start = currentEnd - 9;
        end = totalPage > currentEnd ? currentEnd : totalPage;

        prev = start > 1;
        next = totalPage > currentEnd;

        pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}
