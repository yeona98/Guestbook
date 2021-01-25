package com.jane.guestbook.entity;

import lombok.*;

import javax.persistence.*;

@Getter @ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Guestbook extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gno;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 1500, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;
}
