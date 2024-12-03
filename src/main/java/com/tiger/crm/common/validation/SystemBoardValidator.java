package com.tiger.crm.common.validation;

import com.tiger.crm.repository.dto.board.SystemBoardDto;
import com.tiger.crm.repository.dto.user.UserLoginDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

/*SystemBoardValidator
* 작성자 : 제예솔
* 설명 :
* */
@Component
public class SystemBoardValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return SystemBoardDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        SystemBoardDto systemBoard = (SystemBoardDto) target;


        //회사선택 없을 경우
        if(systemBoard.getCompanyId() == null || systemBoard.getCompanyId().trim().isEmpty() || Integer.parseInt(systemBoard.getCompanyId()) == 0 ){
            errors.rejectValue("companyId","error.required.systemBoard.companyId");
        }
        
        //제목 미기입시
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"title","error.required.systemBoard.title");

        //첨부파일 총 용량이 10MB 를 초과할 경우
        final long MAX_TOTAL_FILE_SIZE = 10 * 1024 * 1024;

        long totalFileSize = systemBoard.getAttachFiles().stream()
                .filter(file -> !file.isEmpty()) // 빈 파일 제외
                .mapToLong(MultipartFile::getSize) // 파일 크기 가져오기
                .sum();

        // 첨부파일 용량 초과 체크
        if (totalFileSize > MAX_TOTAL_FILE_SIZE) {
            errors.rejectValue("attachFiles", "error.fileMaxSize");
        }


    }
}
