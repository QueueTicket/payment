package com.qticket.payment.global.jpa;

import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@MappedSuperclass
public abstract class Auditable extends CreatedAuditable {

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
