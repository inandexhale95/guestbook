package com.guestbook.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass   // 적용된 클래스는 테이블로 생성되지 않는다.
/*
    AuditingEntityListener: JPA 내부에서 엔티티 객체가 생성/변경되는 감지하는 역할
 */
@EntityListeners(value = AuditingEntityListener.class)
@Getter
public class BaseEntity {

    @CreatedDate
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "moddate", updatable = false)
    private LocalDateTime modDate;
}
