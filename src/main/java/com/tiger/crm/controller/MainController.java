package com.tiger.crm.controller;

import com.tiger.crm.common.context.ConfigProperties;
import com.tiger.crm.common.util.AESUtils;
import com.tiger.crm.repository.dto.board.NoticeBoardDto;
import com.tiger.crm.repository.dto.client.ClientManageDto;
import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;
import com.tiger.crm.repository.mail.MailService;
import com.tiger.crm.service.board.NoticeBoardService;
import com.tiger.crm.service.client.ClientManageService;
import com.tiger.crm.service.common.CommonService;
import com.tiger.crm.service.main.MainService;
import com.tiger.crm.repository.dto.user.UserLoginDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Controller
public class MainController {
	@Autowired
	private CommonService commonService;
	@Autowired
	private ConfigProperties config;
	@Autowired
	private MainService mainService;
	@Autowired
	private MailService mailService;
	@Autowired
	private NoticeBoardService noticeBoardService;
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	@Autowired
	private ClientManageService clientManageService;
	@Autowired
	private ConfigProperties configProperties;
	@Value("${file.logindir}")
	private String uploadDir;
	@Autowired
	PasswordEncoder passwordEncoder;
	/*
	 * 랜딩페이지
	 * 설명 : 세션이 살아 있으면 메인페이지로, 세션이 없으면 로그인으로 리다이렉트
	 * */
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public String intro(@ModelAttribute("user") UserLoginDto user, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			LOGGER.info("세션 없음");
			return "redirect:login";
		}
		return "redirect:main";
	}

	/*
	 * 메인페이지
	 * 설명 : 세션이 살아있으면 세션 정보 화면으로 보내줌, 세션 없으면 로그인 페이지로
	 * */
	@RequestMapping(value = {"main"}, method = RequestMethod.GET)
	public String mainPage(@ModelAttribute PagingRequest pagingRequest, @ModelAttribute("user") UserLoginDto user, HttpServletRequest request, Model model) {

		HttpSession session = request.getSession(false);

		if (session == null) {
			LOGGER.info("세션 없음");
			return "redirect:login";
		}

		// 유저정보
		user = (UserLoginDto) session.getAttribute("loginUser");
		LOGGER.info("세션정보 : " + user.toString());
		model.addAttribute("user", user);
		String userClass = String.valueOf(user.getUserClass());
		model.addAttribute("userClass", userClass);
		// 메인 페이지 요청내역 조회
		List<Map<String, Object>> ticketList = mainService.getMainTicketList(user);
		// 메인 페이지 완료내역 조회
		List<Map<String, Object>> ticketList2 = mainService.getCloseList(user);
		// 공지사항 목록 조회
		List<Map<String, Object>> noticeList = mainService.getNoticeList(user);

		model.addAttribute("ticketList", ticketList);
		model.addAttribute("ticketList2", ticketList2);
		model.addAttribute("noticeList", noticeList);

		return "main";
	}

	/*
	 * 1. 메일발송내역 페이지(초기 접속 시)
	 */
	@GetMapping(value = {"mailHistory"})
	public String mailHistory(@ModelAttribute PagingRequest pagingRequest, Model model) {
		try {
			// 메일 발송 내역 조회
			PagingResponse<Map<String, Object>> pageResponse = mailService.getMailHistList(pagingRequest);
			model.addAttribute("mailHistList", pageResponse);
			model.addAttribute("searchOptions", commonService.getSelectOptions("mh_search"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "mailHistory";
	}

	/*
	 * 2. 메일발송내역 페이지(상세 검색 시)
	 */
	@PostMapping(value = {"mailHistory"})
	public String searchMailHistory(@ModelAttribute PagingRequest pagingRequest, Model model) {

		try {
			// 메일 발송 내역 조회
			PagingResponse<Map<String, Object>> pageResponse = mailService.getMailHistList(pagingRequest);
			model.addAttribute("mailHistList", pageResponse);

			// 부분 뷰 렌더링 (리스트 부분만 갱신)
			return "mailHistory :: mailHistListFragment";

		} catch (IllegalArgumentException e) {
			// 입력 값에 대한 오류 처리 (예: 유효하지 않은 파라미터)
			model.addAttribute("error", "잘못된 입력 값이 있습니다. 다시 확인해 주세요.");
			e.printStackTrace();
		} catch (Exception e) {
			// 데이터 조회 중 발생한 일반적인 오류 처리
			model.addAttribute("error", "데이터를 불러오는 중 오류가 발생했습니다. 다시 시도해주세요.");
			e.printStackTrace();
		}

		return "mailHistory";
	}

	/*
	 * getPopup
	 * 작성자 : 제예솔
	 * 설명 : 팝업공지 내용 가져옴
	 * */
	@PostMapping(value = {"/loadPopup"})
	public ResponseEntity<List<NoticeBoardDto>> getPopup(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession(false);
		UserLoginDto loginUser = (UserLoginDto) session.getAttribute("loginUser");

		List<NoticeBoardDto> noticeBoardDtoList = noticeBoardService.getPopupNoticeBoardList(loginUser);
		if (noticeBoardDtoList == null || noticeBoardDtoList.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(noticeBoardDtoList);
	}

	@GetMapping("/contacts")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> showEmergencyContacts() {
		Map<String, Object> response = new HashMap<>();
		try {
			// 1. 연락처 목록 조회
			List<ClientManageDto> contactDetail = clientManageService.getContacts();

			// 2. 리스트 내 모든 데이터의 phone 값 디코딩
			for (ClientManageDto contact : contactDetail) {
				try {
					String decodedString = AESUtils.decrypt(
							contact.getPhone(),
							AESUtils.decodeKey(configProperties.getAesSecretKey())
					);
					contact.setPhone(decodedString); // 디코딩된 전화번호를 다시 설정
				} catch (Exception e) {
					LOGGER.error("전화번호 복호화 중 오류 발생: " + contact.getPhone(), e);
					contact.setPhone(""); // 오류 발생 시 기본값 설정
				}
			}

			// 3. JSON 응답 데이터 구성
			response.put("status", "success");
			response.put("data", contactDetail);

			return ResponseEntity.ok(response); // HTTP 200 OK 반환
		} catch (Exception e) {
			LOGGER.error("연락처 조회 중 오류 발생", e);
			response.put("status", "error");
			response.put("message", "연락처 조회 중 오류가 발생했습니다.");

			// HTTP 500 INTERNAL_SERVER_ERROR 반환
			return ResponseEntity.ok(response);
		}
	}

	@GetMapping("/managePage")
	public String managePage(@ModelAttribute PagingRequest pagingRequest, Model model) {
		try {
			// 메일 발송 내역 조회
			//PagingResponse<Map<String, Object>> pageResponse = mailService.getMailHistList(pagingRequest);
			//model.addAttribute("mailHistList", pageResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "managePage";
	}

	// 로그인 이미지 업로드 처리
	@PostMapping("/uploadLoginImage")
	public ResponseEntity<Map<String, Object>> uploadLoginImage(@RequestParam("imageFile") MultipartFile imageFile) {
		Map<String, Object> response = new HashMap<>();

		try {
			// 파일 저장 경로 지정
			File targetFile = new File(uploadDir + "/login-bg.png");
			Path path = Paths.get(uploadDir);
			// 경로가 존재하지 않으면 폴더 생성
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}
			// 이미지 파일 저장
			imageFile.transferTo(targetFile);

			response.put("status", "success");
			response.put("message", "이미지 업로드 성공");
		} catch (Exception e) {
			LOGGER.error("파일 저장 중 오류가 발생했습니다", e);
			response.put("status", "error");
			response.put("message", "파일 저장 중 오류가 발생했습니다");
		}

		return ResponseEntity.ok(response);
	}

	/*
	 * 비밀번호변경
	 */
	@PostMapping("/changePassword")
	@ResponseBody
	public Map<String, Object> changePassword(@RequestBody ClientManageDto clientManageDto, HttpServletRequest request) {
		Map<String, Object> response = new HashMap<>();
		try {
			UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");
			clientManageDto.setUserId(loginUser.getUserId()); // 등록/수정자 용도

			clientManageDto.setUserPw(passwordEncoder.encode(clientManageDto.getUserPw()));
			clientManageService.changePassword(clientManageDto);

			response.put("status", "success");

		}catch (Exception e){
			response.put("status", "error");
			LOGGER.error("비밀번호변경 중 오류가 발생했습니다.", e);
		}
		return response;
	}

}

