package com.tiger.crm.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final String userMessage;

    public CustomException(String userMessage) {
        super(userMessage);
        this.userMessage = userMessage;
    }

    public CustomException(String userMessage, Throwable cause) {
        super(userMessage, cause);
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

}
