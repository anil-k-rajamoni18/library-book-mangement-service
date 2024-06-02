package com.develop.librarybookmanagementservice.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoreException extends RuntimeException {
    private final String code;
    private final String description;

    public CoreException(String description) {
        super(description);
        this.code = null;
        this.description = description;

    }
    public CoreException(String code, String description) {
        super(description);
        this.code = code;
        this.description = description;
    }

    public CoreException(String code, Throwable cause) {
        super(code, cause);
        this.code = code;
        this.description = null;
    }

    public CoreException(Throwable cause) {
        super(cause);
        this.code = "SYS_DEFAULT_ERROR";
        this.description = "Internal Server Error, Please contact administrator";
    }

}
