package com.thesis.backend.annotations;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NonNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JsonFormat(pattern = "yyyy.MM.dd HH:mm")
@NonNull
public @interface ValidDate {
}
