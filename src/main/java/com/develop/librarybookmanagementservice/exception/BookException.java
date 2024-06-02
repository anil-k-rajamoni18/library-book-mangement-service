package com.develop.librarybookmanagementservice.exception;

import java.io.Serial;

public class BookException extends CoreException {

    @Serial
    private static final long serialVersionUID = -7153401222436984515L;
    public BookException(String code, String message) {
        super(code, message);
    }

    public BookException(String message) {
        super(message);
    }
}
