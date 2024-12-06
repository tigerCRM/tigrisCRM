package com.tiger.crm.controller;

import com.tiger.crm.repository.dto.alert.AlertDto;
import com.tiger.crm.repository.dto.user.UserLoginDto;
import com.tiger.crm.service.alert.AlertService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/alert") // 공통 경로 설정
public class AlertController {

    @Autowired
    private AlertService alertService;

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    /*
     * 알림뱃지 조회
     * 설명 : 헤더 상단 종아이콘의 알림목록 조회
     * */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAlertList(HttpServletRequest request) {
        try {
            // 유저 정보 조회
            UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");

            // 알림 목록 조회
            List<AlertDto> alerts = alertService.getAlertList(loginUser);

            // 알림 갯수 조회
            int alertsCnt = alertService.getAlertCnt(loginUser);

            // Map에 알림 목록과 알림 개수 담기
            Map<String, Object> response = new HashMap<>();
            response.put("alerts", alerts);
            response.put("alertCount", alertsCnt);

            // 알림 목록 반환
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * 알림뱃지 읽음 처리
     * 설명 : 헤더 상단 종아이콘에 알림을 읽음 처리
     * */
    @PutMapping
    public ResponseEntity<Void> updateAlertReadStatus(@RequestParam("alertId") String alertId) {
        try {

            // 알림 읽음 처리
            alertService.updateAlertReadStatus(alertId);

            // 성공 시 상태값(200)만 전달
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); //HTTP 500 상태
        }
    }

}
