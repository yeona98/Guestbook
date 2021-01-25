package com.jane.guestbook.repository;

import com.jane.guestbook.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemoRepositoryTest {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass() {
        System.out.println(memoRepository.getClass().getName());
    }

    @Transactional // 연결된 DB에 데이터 생성 안 됨.
    @Test
    public void memo_등록_테스트() {
        //given
        Memo memo = Memo.builder()
                        .memoText("Sample")
                        .build();

        //when
        memoRepository.save(memo);

        //then
        assertThat(memo.getMemoText()).isEqualTo("Sample");
        assertThat(memo.getMemoText().equals("Sample"));
    }

    @Transactional // 연결된 DB에 데이터 생성 안 됨. --> 아래 테스트들을 위해 먼저 실행
    @Test
    public void memo_여러개등록_테스트() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder()
                    .memoText("Sample" + i)
                    .build();

            memoRepository.save(memo);
        });
    }

    @Test
    public void memo_findById_테스트() {
        Long mno = 203L;

        Optional<Memo> result = memoRepository.findById(mno);

        // findById()는 실행한 순간에 바로 SQL 처리
        System.out.println("------------------------------------");

        if(result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }

    }

    @Transactional
    @Test
    public void memo_getOne_테스트() {
        Long mno = 203L;

        Memo memo = memoRepository.getOne(mno);

        // getOne()은 객체가 필요한 순간까지 SQL 실행하지 않음
        System.out.println("------------------------------------");

        System.out.println(memo);
    }

    @Transactional // 연결된 DB에 데이터 생성 안 됨.
    @Test
    public void memo_수정_테스트() {
        //given
        String updatedText = "Update Sample";

        Memo memo = Memo.builder()
                        .mno(104L)
                        .memoText(updatedText)
                        .build();

        //when
        memoRepository.save(memo);

        //then
        assertThat(memo.getMemoText().equals(updatedText));
    }

    @Test
    public void memo_삭제_테스트() {
        //given
        Long mno = 104L;

        //when
        memoRepository.deleteById(mno);

        //then
        assertThat(!memoRepository.findById(mno).isPresent());

    }

    @Test
    public void 페이징_테스트() {
        //given
        int page = 0;
        int size = 10;

        Pageable pageable = PageRequest.of(0, 10); // 1페이지 10개

        //when
        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println(result);

        //then
        assertThat(Integer.toString(result.getTotalPages())
                .equals(Integer.toString(size)));
        assertThat(Long.toString(result.getTotalPages())
                .equals(Long.toString(memoRepository.count())));
        assertThat(Long.toString(result.getNumber())
                .equals(Long.toString(page)));
        
        // Page<T>에서 지원하는 메서드
        System.out.println("---------------------------------");
        
        // 총 몇 페이지
        System.out.println("Total Pages: " + result.getTotalPages());
        
        // 전체 개수
        System.out.println("Total Count: " + result.getTotalElements());
        
        // 현재 페이지 번호 0부터 시작
        System.out.println("Page Number: " + result.getNumber());
        
        // 페이지당 데이터 개수
        System.out.println("Page Size: " + result.getSize());
        
        // 다음 페이지 존재 여부
        System.out.println("has next Page?: " + result.hasNext());
        
        // 시작 페이지(0) 여부
        System.out.println("first page?: " + result.isFirst());

        // 실제 페이지의 데이터를 처리
        // 1. getContent()를 이용하여 List<T>로 처리
        // 2. Stream<T> 을 반환하는 get() 이용
        System.out.println("---------------------------------");

        for (Memo memo : result.getContent()) {
            System.out.println(memo);
        }
    }

    @Test
    public void 정렬_테스트() {
        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2); // Sort 객체의 and()를 이용하여 여러 개의 정렬 조건을 다르게 지정하여 연결

        Pageable pageable = PageRequest.of(0, 10, sort1);
        Pageable pageableAnd = PageRequest.of(0, 10, sortAll); // 결합된 정렬 조건 사용

        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Test
    public void 쿼리메서드_테스트() {
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(120L, 130L);

        for (Memo memo : list) {
            System.out.println(memo);
        }
    }

    @Test
    public void 쿼리메서드_with_Pageable_테스트() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(120L, 170L, pageable);

        result.get().forEach(memo -> System.out.println(memo));
    }

    @Commit
    @Transactional
    @Test
    public void deleteBy_쿼리메서드_삭제_테스트() {
        memoRepository.deleteMemoByMnoLessThan(120L);
    }
}
