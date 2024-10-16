package com.qticket.payment.global.annotations;

import static com.qticket.payment.global.annotations.DateTimeFormat.ASIA_SEOUL;
import static com.qticket.payment.global.annotations.DateTimeFormat.ISO_DATE_TIME;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@JacksonAnnotationsInside
@Retention(RetentionPolicy.RUNTIME)
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ISO_DATE_TIME, timezone = ASIA_SEOUL)
public @interface DateTimeFormat {

    String ISO_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    String ASIA_SEOUL = "Asia/Seoul";

}
