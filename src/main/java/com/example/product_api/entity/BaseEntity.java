package com.example.product_api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@MappedSuperclass
@ToString
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity <ID extends Serializable>{

    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false, nullable = false)
    protected Instant createdAt;

    @LastModifiedDate
    @Column(name = "UPDATED_AT")
    protected Instant updatedAt;

}
