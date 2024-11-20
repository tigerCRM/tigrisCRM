package com.tiger.crm.service.common;

import com.tiger.crm.repository.dto.company.CompanyOptionDto;
import com.tiger.crm.repository.mapper.CompanyOptionMapper;
import com.tiger.crm.repository.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*공통 서비스
 * 설명 : 공통서비스 모음집
 * 1. selectbox
 * 2. 회사 불러오기(getCompanyOption)
 * 3.
 * */

@Service
@PropertySource("classpath:setting.properties")
@ConfigurationProperties(prefix = "code")
public class CommonService {
    @Autowired
    CompanyOptionMapper companyOptionMapper;
    private Map<String, String> select = new LinkedHashMap<>();

    public Map<String, String> getSelectOptions(String id) {
        Map<String, String> options = new LinkedHashMap<>();
        String selectedOptions = select.get(id);  // id로 옵션 문자열 가져오기
        if (selectedOptions != null) {
            String[] optionsArray = selectedOptions.split(",");
            for (String option : optionsArray) {
                String[] parts = option.split(":");
                options.put(parts[0], parts[1]);
            }
        }
        return options;
    }

    public Map<String, String> getSelect() {
        return select;
    }

    public void setSelect(Map<String, String> select) {
        this.select = select;
    }
    
    /*
    * 회사 이름 불러오기
    * 작성자 : 제예솔
    * 설명 : select box 에서 사용하기 위해 회사 이름, 회사 아이디를 불러옴
    * */
    public List<CompanyOptionDto> getCompanyOption() {
        return companyOptionMapper.getAllCompany();
    }

}
