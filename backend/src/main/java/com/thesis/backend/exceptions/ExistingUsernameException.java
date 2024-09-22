package com.thesis.backend.exceptions;

import lombok.NonNull;

public class ExistingUsernameException extends RuntimeException {
    public ExistingUsernameException(@NonNull String s) {
        super(s);
    }
}
