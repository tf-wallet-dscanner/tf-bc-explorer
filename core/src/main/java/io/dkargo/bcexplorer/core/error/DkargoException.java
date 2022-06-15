package io.dkargo.bcexplorer.core.error;

import lombok.Getter;

@Getter
public class DkargoException extends RuntimeException {

    //http status
    private int status;

    //custom code
    private int code;

    public DkargoException(ErrorCodeEnum error) {
        super(error.getMessage());
        this.status = error.getStatus();
        this.code = error.getCode();
    }

    public DkargoException(ErrorCodeEnum error, String message) {
        super(message);
        this.status = error.getStatus();
        this.code = error.getCode();
    }

    public DkargoException(int status, int code, String message) {
        super(message);
        this.status = status;
        this.code = code;
    }
}
