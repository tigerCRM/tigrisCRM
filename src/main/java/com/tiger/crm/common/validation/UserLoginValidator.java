package com.tiger.crm.common.validation;

import com.tiger.crm.repository.dto.user.UserLoginDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/*UserLoginValidator
* 작성자 : 제예솔
* 설명 : client 에서 받은 값에 대한 검증을 수행한다
* */
@Component
public class UserLoginValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserLoginDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserLoginDto user = (UserLoginDto) target;

        //bindingResult 는 Errors 의 자식 객체이므로 동일하게 사용할 수 있다
        //검증로직 : id 또는 password 가 공백일 경우
		/*if(user.getUserId() == null || user.getUserId().trim().isEmpty()){
			bindingResult.rejectValue("userId","error.required");
			//bindingResult.rejectValue("userId","error.required" ,new Object[]{"sample","test"},null); 에러 코드에 입력해 주고 싶은 값이 있는 경우
		}
		if(user.getUserPw() == null|| user.getUserPw().trim().isEmpty()){
			bindingResult.rejectValue("userPw","error.required");
		}*/

        //단순 공백검사일 경우
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"userId","error.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"userPw","error.required");


    }
}
