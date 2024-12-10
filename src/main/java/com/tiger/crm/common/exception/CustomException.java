package com.tiger.crm.common.exception;

import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class CustomException extends RuntimeException {
    private final String userMessage;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    public CustomException(String userMessage) {
        super(userMessage);
        this.userMessage = userMessage;
    }

    public CustomException(String userMessage, Throwable cause) {
        super(userMessage, cause);
        this.userMessage = userMessage;
        LOGGER.error("Exception occurred: {}", userMessage, cause);
    }

    public String getUserMessage() {
        return userMessage;
    }

}
