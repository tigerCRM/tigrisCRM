package com.tiger.crm.common.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public String handleCustomException(CustomException ex, Model model) {
        // CustomException에서 사용자 메시지를 받아서 모델에 추가
        model.addAttribute("errorMessage", ex.getUserMessage());
        return "common/error"; // 오류 페이지로 이동
    }

    // 다른 예외
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model) {
        model.addAttribute("errorMessage", "알 수 없는 오류가 발생했습니다.");
        return "common/error"; // 오류 페이지로 이동
    }
}