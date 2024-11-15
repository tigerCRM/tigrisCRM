package com.tiger.crm.common.validation;

import com.tiger.crm.repository.dto.TicketDto;
import com.tiger.crm.repository.dto.user.UserLoginDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class TicketValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return TicketDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TicketDto ticket = (TicketDto) target;

        //검증로직 이후 추가하시면 됩니다.

    }
}
