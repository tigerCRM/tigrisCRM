package com.tiger.crm.controller;

import com.tiger.crm.common.context.ConfigProperties;
import com.tiger.crm.common.exception.CustomException;
import com.tiger.crm.common.util.AESUtils;
import com.tiger.crm.repository.dto.client.ClientManageDto;
import com.tiger.crm.repository.dto.company.CompanyOptionDto;
import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;
import com.tiger.crm.repository.dto.ticket.CommentDto;
import com.tiger.crm.repository.dto.user.UserLoginDto;
import com.tiger.crm.repository.mail.MailService;
import com.tiger.crm.repository.mapper.LoginMapper;
import com.tiger.crm.service.client.ClientManageService;
import com.tiger.crm.service.common.CommonService;
import com.tiger.crm.service.login.LoginService;
import com.tiger.crm.service.login.LoginServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.*;

@Controller
public class ClientManageController {

    @Autowired
    private ClientManageService clientManageService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private SpringTemplateEngine templateEngine;
    @Autowired
    private ConfigProperties configProperties;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired
    private MailService mailService;
    @Autowired
    private LoginServiceImpl LoginServiceImpl;
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Value("${app.base-url}")
    private String baseUrl;
    /*
     * 고객사 목록 페이지(최초 접속 시)
     */
    @GetMapping("/clientManage")
    public String getclientManage(@ModelAttribute PagingRequest pagingRequest, ClientManageDto clientManageDto, Model model) {
        try {
            // 최초 접속 파라미터
            clientManageDto.setCompanyId(1);

            // 1. 고객사 목록 조회
            PagingResponse<Map<String, Object>> pageResponse = clientManageService.getCompanyList(pagingRequest);
            model.addAttribute("companyList", pageResponse);
            List<CompanyOptionDto> companyOptions = commonService.getCompanyOption();
            model.addAttribute("companyOptions", companyOptions);
            // 2. 고객사 상세 조회
            PagingResponse<Map<String, Object>> groupOptions = clientManageService.getGroupList(pagingRequest);
            model.addAttribute("groupOptions", groupOptions.getDataList());
            List<ClientManageDto> companyDetail = clientManageService.getCompanyDetail(clientManageDto);
            model.addAttribute("companyDetail", companyDetail);

            return "clientManage";

        } catch (Exception e) {
            throw new CustomException("고객사 초기 데이터를 불러오는 중 오류가 발생했습니다.", e);
        }
    }

    /*
     * 고객사 목록 조회(상세검색 시)
     */
    @PostMapping("/companyListData")
    @ResponseBody
    public Map<String, Object> getCompanyListData(@ModelAttribute PagingRequest pagingRequest, Model model) {
        try {

            // 1. 고객사 목록 조회
            PagingResponse<Map<String, Object>> pageResponse = clientManageService.getCompanyList(pagingRequest);
            model.addAttribute("companyList", pageResponse);

            // 2. Model을 Context로 변환
            Context context = new Context();
            context.setVariable("list", pageResponse);  // PagingResponse 객체를 "list"로 설정
            context.setVariable("paginationUrl", "companyListData");  // 페이징 URL을 설정

            // 3. 페이징 HTML 생성
            String paginationHtml = templateEngine.process("fragments/pagination", context);

            // 응답 데이터 구성
            Map<String, Object> response = new HashMap<>();
            response.put("companyList", pageResponse.getDataList());  // 고객 목록
            response.put("paginationHtml", paginationHtml);          // 페이징 HTML

            return response;

        } catch (Exception e) {
            throw new CustomException("고객사 목록(상세검색) 데이터를 불러오는 중 오류가 발생했습니다.", e);
        }
    }
    //그룹권한 코드
    @GetMapping("/groupManage")
    public String getgroupManage(@ModelAttribute PagingRequest pagingRequest, Model model) {
        try {
            //그룹정보 호출
            PagingResponse<Map<String, Object>> pageResponse = clientManageService.getGroupList(pagingRequest);
            model.addAttribute("groupList", pageResponse);
            List<ClientManageDto> groupAuthList = new ArrayList<>();
            model.addAttribute("authList", groupAuthList);
            return "groupManage";

        } catch (Exception e) {
            throw new CustomException("그룹관리 초기 데이터를 불러오는 중 오류가 발생했습니다.", e);
        }
    }
    /*
     * 그룹권한 상세
     */
    @GetMapping("/groupDetailData")
    public String groupDetailData(ClientManageDto clientManageDto, Model model) {
        try {
            // 고객사 상세 정보 조회
            List<ClientManageDto> companyDetail = clientManageService.getGroupDetail(clientManageDto);
            if (!companyDetail.isEmpty()) {
                ClientManageDto detail = companyDetail.get(0); // 첫 번째 고객사 정보 가져오기
                model.addAttribute("groupCode", detail.getGroupCode());
                model.addAttribute("groupName", detail.getGroupName());
                model.addAttribute("groupNotes", detail.getGroupNotes());
                model.addAttribute("groupuseYn", detail.getGroupuseYn());
            }

            // 권한 관리자 목록 추가
            List<ClientManageDto> groupAuthList = clientManageService.getGroupAuthList(clientManageDto);
            model.addAttribute("authList", groupAuthList);
            return "groupManage :: groupDetailFragment";

        } catch (Exception e) {
            throw new CustomException("고객사의 상세 데이터를 불러오는 중 오류가 발생했습니다.", e);
        }
    }
    /*
     * 고객사 상세 조회
     */
    @GetMapping("/companyDetailData")
    @ResponseBody
    public  Map<String, Object> getCompanyDetailData(ClientManageDto clientManageDto) {
        try {

            // 1. 고객사 상세 조회
            List<ClientManageDto> companyDetail = clientManageService.getCompanyDetail(clientManageDto);

            // 2. 데이터를 Map에 담기
            Map<String, Object> response = new HashMap<>();
            if (!companyDetail.isEmpty()) {
                ClientManageDto detail = companyDetail.get(0); // 첫 번째 고객사 정보 가져오기
                response.put("companyId", detail.getCompanyId());
                response.put("companyName", detail.getCompanyName());
                response.put("managerName", detail.getManagerName());
                response.put("managerId", detail.getManagerId());
                response.put("notes", detail.getNotes());
                response.put("companyuseYn", detail.getCompanyuseYn());
                response.put("groupCode",detail.getGroupCode());
            }

            return response;

        } catch (Exception e) {
            throw new CustomException("고객사의 상세 데이터를 불러오는 중 오류가 발생했습니다.", e);
        }
    }

    /*
     * 고객 목록 조회
     */
    @PostMapping("/clientListData")
    @ResponseBody
    public Map<String, Object> getClientListData(@ModelAttribute PagingRequest pagingRequest, Model model) {
        try {

            // 1. 고객 목록 조회 (페이징된 데이터)
            PagingResponse<Map<String, Object>> pageResponse = clientManageService.getClientList(pagingRequest);
            model.addAttribute("clientList", pageResponse);

            // 2. Model을 Context로 변환
            Context context = new Context();
            context.setVariable("list", pageResponse);  // PagingResponse 객체를 "list"로 설정
            context.setVariable("paginationUrl", "clientListData");  // 페이징 URL을 설정

            // 3. Thymeleaf 템플릿을 HTML로 렌더링
            String paginationHtml = templateEngine.process("fragments/pagination", context);

            // 4. JSON 응답 생성
            Map<String, Object> response = new HashMap<>();
            response.put("clientList", pageResponse.getDataList()); // 데이터 목록
            response.put("paginationHtml", paginationHtml);         // 렌더링된 페이징 HTML

            return response;

        } catch (Exception e) {
            throw new CustomException("고객의 목록 데이터를 불러오는 중 오류가 발생했습니다.", e);
        }
    }

    /*
     * 고객사 상세 조회
     */
    @GetMapping("/clientDetailData")
    @ResponseBody
    public  Map<String, Object> getClientDetailData(ClientManageDto clientManageDto) {
        try {

            // 1. 고객사 상세 조회
            List<ClientManageDto> clientDetail = clientManageService.getClientDetail(clientManageDto);

            // 2. 데이터를 Map에 담기
            Map<String, Object> response = new HashMap<>();
            if (!clientDetail.isEmpty()) {
                ClientManageDto detail = clientDetail.get(0); // 첫 번째 고객사 정보 가져오기
                response.put("userName", detail.getUserName());
                response.put("userId", detail.getUserId());
                response.put("userClass", detail.getUserClass());
                response.put("email", detail.getEmail());
                String decodedString = "";
                if (detail.getPhone()!=null){
                    decodedString = AESUtils.decrypt(detail.getPhone(), AESUtils.decodeKey(configProperties.getAesSecretKey()));
                }
                response.put("phone", decodedString);
                response.put("useyn", detail.getUseruseYn());
                response.put("clientcompanyId", detail.getCompanyId());
            }

            return response;

        } catch (Exception e) {
            throw new CustomException("고객의 상세 데이터를 불러오는 중 오류가 발생했습니다.", e);
        }
    }
    
    /*
     * 신규 고객사 등록
     */
    @PostMapping("/createCompany")
    @ResponseBody
    public Map<String, Object> createCompany(@RequestBody ClientManageDto clientManageDto, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 1. 사용자값 추출
            UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");
            clientManageDto.setUserId(loginUser.getUserId()); // 등록/수정자 용도
            
            // 2. 신규 고객사 등록
            clientManageService.createCompany(clientManageDto);
            response.put("status", "success");
        }catch (Exception e) {
            response.put("status", "error");
            LOGGER.error("고객사 등록 중 오류가 발생했습니다.", e);
        }
        return response;
    }

    @PostMapping("/updateCompany")
    @ResponseBody
    public Map<String, Object> updateCompany(@RequestBody ClientManageDto clientManageDto, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 1. 사용자값 추출
            UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");
            clientManageDto.setUserId(loginUser.getUserId()); // 등록/수정자 용도

            // 2. 고객사 수정
            clientManageService.updateCompany(clientManageDto);
            response.put("status", "success");
        }catch (Exception e) {
            response.put("status", "error");
            LOGGER.error("고객사 수정 중 오류가 발생했습니다.", e);
        }
        return response;
    }

    /*
     * 신규 고객 등록
     */
    @PostMapping("/createClient")
    @ResponseBody
    public Map<String, Object> createClient(@RequestBody ClientManageDto clientManageDto, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 1. 사용자값 추출
            UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");
            clientManageDto.setCreateId(loginUser.getUserId()); // 등록/수정자 용도
            String encryptedString = AESUtils.encrypt(clientManageDto.getPhone(), AESUtils.decodeKey(configProperties.getAesSecretKey()));
            clientManageDto.setPhone(encryptedString);
            String tempPassword = LoginServiceImpl.generateTempPassword();
            clientManageDto.setUserPw(passwordEncoder.encode(tempPassword));
            // 2. 신규 고객 등록
            clientManageService.createClient(clientManageDto);
            // 3. 사용자 권한 등록
           // clientManageService.createAuth(clientManageDto);

            Map<String, Object> model = new HashMap<>();
            model.put("userName", clientManageDto.getUserName());
            model.put("userId", clientManageDto.getUserId());
            model.put("password", tempPassword);
            model.put("loginUrl", baseUrl+ "/login");
            mailService.sendEmail(clientManageDto.getEmail(),"가입안내", "info-email", model);
            response.put("status", "success");
        }catch (Exception e) {
            response.put("status", "error");
            LOGGER.error("신규 사용자 등록 중 오류가 발생했습니다.", e);
        }
        return response;
    }

    @PostMapping("/updateClient")
    @ResponseBody
    public Map<String, Object> updateClient(@RequestBody ClientManageDto clientManageDto, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 1. 사용자 값 추출
            UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");
            clientManageDto.setUpdateId(loginUser.getUserId()); // 등록/수정자 설정

            // 2. 전화번호 암호화 (null 체크)
            if (clientManageDto.getPhone() != null) {
                String secretKey = configProperties.getAesSecretKey();
                if (secretKey == null || secretKey.isEmpty()) {
                    throw new CustomException("AES 암호화 키가 설정되지 않았습니다.");
                }
                String encryptedString = AESUtils.encrypt(clientManageDto.getPhone(), AESUtils.decodeKey(secretKey));
                clientManageDto.setPhone(encryptedString);
            }

            // 3. 고객사 수정
            clientManageService.updateClient(clientManageDto);
            // 4. 사용자 권한 등록
          //  clientManageService.updateAuth(clientManageDto);
            response.put("status", "success");
        } catch (Exception e) {
            response.put("status", "error");
            LOGGER.error("사용자 수정 중 오류가 발생했습니다.", e);
        }
        return response;
    }

    /*
     * 신규 그룹 권한 추가
     */
    @PostMapping("/createGroup")
    @ResponseBody
    public Map<String, Object> createGroup(@RequestBody ClientManageDto clientManageDto, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");
            clientManageDto.setUserId(loginUser.getUserId()); // 등록/수정자 용도

            clientManageService.createGroup(clientManageDto);
            response.put("status", "success");
        }catch (Exception e) {
            response.put("status", "error");
            LOGGER.error("그룹 권한 등록 중 오류가 발생했습니다.", e);
        }
        return response;
    }

    @PostMapping("/updateGroup")
    @ResponseBody
    public Map<String, Object> updateGroup(@RequestBody ClientManageDto clientManageDto, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");
            clientManageDto.setUserId(loginUser.getUserId()); // 등록/수정자 용도

            clientManageService.updateGroup(clientManageDto);
            response.put("status", "success");
        }catch (Exception e) {
            response.put("status", "error");
            LOGGER.error("그룹 권한 수정 중 오류가 발생했습니다.", e);
        }
        return response;
    }

    /*
     * 가입 메일 일괄 발송
     */
    @GetMapping ("/clientInfoMailAll")
    @ResponseBody
    public  Map<String, Object> clientInfoMailAll(ClientManageDto clientManageDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            //등록된 고객 호출
            List<ClientManageDto> clientList = clientManageService.getClientListAll();
            for (ClientManageDto client : clientList) {
                //임시 비밀번호 생성 비밀번호 업데이트
                String tempPassword = LoginServiceImpl.generateTempPassword();
                // User 객체 생성
                UserLoginDto user = new UserLoginDto();
                user.setUserId(client.getUserId());
                user.setUserPw(passwordEncoder.encode(tempPassword)); // 암호화 후 저장
                loginMapper.resetPassword(user);
                //가입 메일 일괄 발송
                Map<String, Object> model = new HashMap<>();
                model.put("userName", client.getUserName());
                model.put("userId", client.getUserId());
                model.put("password", tempPassword);
                model.put("loginUrl", baseUrl+ "/login");
                mailService.sendEmail(client.getEmail(),"가입안내", "info-email", model);
            }

            response.put("status", "success");
        }catch (Exception e) {
            response.put("status", "error");
            LOGGER.error("가입 메일 일괄 발송 중 오류가 발생했습니다.", e);
        }
        return response;
    }
}
