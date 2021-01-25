package com.jane.guestbook.repository;

import com.jane.guestbook.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

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
        assertThat(memo.getMemoText().equals("Sample"));
    }

    @Transactional // 연결된 DB에 데이터 생성 안 됨.
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
}
