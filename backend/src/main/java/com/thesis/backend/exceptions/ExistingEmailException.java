package com.thesis.backend.exceptions;

import lombok.NonNull;

public class ExistingEmailException extends RuntimeException {
    public ExistingEmailException(@NonNull String s) {
        super(s);
    }
}
