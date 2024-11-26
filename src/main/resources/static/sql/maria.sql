-- T_USER_INFO 유저 테이블
CREATE TABLE T_USER_INFO (
     USER_ID VARCHAR(50) NOT NULL COMMENT '아이디',
     USER_NAME VARCHAR(50) NOT NULL COMMENT '이름',
     USER_PW VARCHAR(200) NOT NULL COMMENT '비밀번호',
     COMPANY_ID INT NOT NULL COMMENT '고객사번호',
     USER_CLASS VARCHAR(20) NOT NULL COMMENT '사용자구분',
     JOB VARCHAR(20) COMMENT '직급',
     EMAIL VARCHAR(200) NOT NULL COMMENT '이메일주소',
     PHONE VARCHAR(20) COMMENT '핸드폰번호',
     EXTENSION_PHONE VARCHAR(20) COMMENT '회사번호',
     CREATE_DT DATETIME NOT NULL COMMENT '생성일',
     CREATE_ID VARCHAR(50) NOT NULL COMMENT '생성자',
     UPDATE_DT DATETIME COMMENT '수정일',
     UPDATE_ID VARCHAR(50) COMMENT '수정자',
     USE_YN CHAR(1) NOT NULL COMMENT '사용여부',
     PRIMARY KEY (USER_ID)  -- USER_ID를 기본 키로 설정
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO T_USER_INFO (USER_ID,USER_NAME,USER_PW,COMPANY_ID,USER_CLASS,JOB,EMAIL,PHONE,EXTENSION_PHONE,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,USE_YN) VALUES
     ('sys1@test.com','sys관리자','1234',1,'SYSADMIN','사원','sys1@test.com','010-1234-5678',NULL,'2024-11-10 00:00:00','sys1@test.com',NULL,NULL,'Y'),
	 ('testAdmin01@test.com','일반관리자1','1234',1,'ADMIN','사원','testAdmin01@test.com','010-1234-5678',NULL,'2024-11-10 00:00:00','sys1@test.com',NULL,NULL,'Y'),
	 ('testUser01@test.com','고객1','1234',2,'USER','사원','testUser01@test.com','010-1234-5678',NULL,'2024-11-10 00:00:00','sys1@test.com',NULL,NULL,'Y');

-- T_ALERT 알림(종모양) 테이블
CREATE TABLE T_ALERT (
     ALERT_ID BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '알림 고유 ID',
     ALERT_TYPE VARCHAR(20) NOT NULL COMMENT '알림 종류 (예: "게시글 작성", "티켓 작성")',
     ALERT_OBJECT_ID BIGINT COMMENT '알림과 연결된 객체 ID (예: 티켓 ID, 게시글 ID)',
     CONTENT VARCHAR(200) NOT NULL COMMENT '알림 내용',
     SENDER_ID VARCHAR(20) NOT NULL COMMENT '알림 발송인',
     RECEIVER_ID VARCHAR(50) NOT NULL COMMENT '알림 수령인',
     ALERT_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '알림 생성 시간',
     READ_YN CHAR(1) DEFAULT 'N' COMMENT '알림 읽음 여부',

    -- 외래 키 제약 조건 추가: RECEIVER_ID가 user_info 테이블의 user_id와 연결됨
     FOREIGN KEY (RECEIVER_ID) REFERENCES T_USER_INFO(user_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- T_AUTH_MANAGE 테이블
CREATE TABLE T_AUTH_MANAGE (
   AUTH_URL VARCHAR(50) NOT NULL COMMENT '접근이 허용되는 페이지 URL',
   AUTH_CODE VARCHAR(10) NOT NULL COMMENT '접근 가능한 권한 코드 (예: USER, ADMIN)',
   PRIMARY KEY (AUTH_URL, AUTH_CODE)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
insert into T_AUTH_MANAGE values ('/main', 'USER'), ('/main', 'ADMIN'), ('/main', 'SYSADMIN'), ('/test', 'ADMIN');

-- T_COMMON_CODE 테이블
CREATE TABLE T_COMMON_CODE (
   CODE_TYPE VARCHAR(200) NOT NULL COMMENT '코드 타입',
   CODE_VALUE VARCHAR(20) NOT NULL COMMENT '코드 값',
   CODE_DESCRIPTION VARCHAR(200) NOT NULL COMMENT '코드 설명',
   CREATE_ID VARCHAR(200) NOT NULL COMMENT '작성자',
   CREATE_DT DATETIME NOT NULL COMMENT '작성일',
   PRIMARY KEY (CODE_TYPE, CODE_VALUE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='공통코드 테이블';

-- T_TICKET_INFO  티켓(요청사항) 정보
CREATE TABLE T_TICKET_INFO (
  TICKET_ID int(11) NOT NULL AUTO_INCREMENT COMMENT '티켓번호',
  COMPANY_ID int(11) NOT NULL COMMENT '요청 고객사 코드',
  COMPANY_NAME varchar(50) NOT NULL COMMENT '비밀번호',
  STATUS_CD varchar(20) NOT NULL COMMENT '상태코드',
  REQUEST_TYPE_CD varchar(20) NOT NULL COMMENT '작업구분코드',
  EXPECTED_COMPLETE_DT datetime DEFAULT NULL COMMENT '완료희망일',
  REAL_COMPLETE_DT datetime DEFAULT NULL COMMENT '완료일',
  MD float DEFAULT NULL COMMENT '작업공수',
  PRIORITY_YN varchar(200) DEFAULT NULL COMMENT '중요여부',
  TITLE varchar(200) NOT NULL COMMENT '제목',
  CONTENT longtext NOT NULL COMMENT '내용',
  DELETE_YN varchar(2) NOT NULL COMMENT '삭제여부',
  MANAGER_ID varchar(50) NOT NULL COMMENT '담당자아이디',
  CREATE_ID varchar(50) NOT NULL COMMENT '작성자아이디',
  CREATE_DT datetime NOT NULL COMMENT '작성일',
  UPDATE_ID varchar(50) DEFAULT NULL COMMENT '수정자 아이디',
  UPDATE_DT datetime DEFAULT NULL COMMENT '수정일',
  FILE_ID varchar(50) DEFAULT NULL COMMENT '첨부파일아이디',
  PARENT_TICKET_CD int(11) DEFAULT NULL,
  PRIMARY KEY (TICKET_ID)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

INSERT INTO T_TICKET_INFO
(TICKET_ID, COMPANY_ID, COMPANY_NAME, STATUS_CD, REQUEST_TYPE_CD, EXPECTED_COMPLETE_DT, REAL_COMPLETE_DT, MD, PRIORITY_YN, TITLE, CONTENT, DELETE_YN, MANAGER_ID, CREATE_ID, CREATE_DT, UPDATE_ID, UPDATE_DT, FILE_ID, PARENT_TICKET_CD)
VALUES(3, 1, '타이거컴퍼니', '등록', '문의', NULL, NULL, 0.0, 'N', '전자결재 문의', '전자결재 문의', 'N', 'testUser01@test.com', 'testUser01@test.com', sysdate(), NULL, NULL, NULL, NULL);


-- T_MAIL_SEND_HIST  메일 발송 히스토리
CREATE TABLE T_MAIL_SEND_HIST (
  MAIL_SEND_ID INT NOT NULL AUTO_INCREMENT COMMENT '메일 전송 ID',
  CATEGORY VARCHAR(200) NOT NULL COMMENT '이메일의 카테고리 (예: 알림, 뉴스레터 등)',
  TITLE VARCHAR(200) NOT NULL COMMENT '이메일 제목',
  CONTENT LONGTEXT NOT NULL COMMENT '이메일 본문 내용',
  STATUS_CD VARCHAR(20) NOT NULL COMMENT '상태코드',
  SENDER_ADDR VARCHAR(200) NOT NULL COMMENT '발신자 이메일 주소',
  RECEIVER_ADDR VARCHAR(200) NOT NULL COMMENT '수신자 이메일 주소',
  SEND_DT DATETIME NOT NULL COMMENT '이메일 전송 일시',
  SUCCESS_YN CHAR(1) NOT NULL DEFAULT 'N' COMMENT '전송 성공 여부',
  PRIMARY KEY (MAIL_SEND_ID)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='이메일 발송 내역 관리 테이블';

-- 2024.11.19 제예솔 / 게시판 create
CREATE TABLE T_BOARD (
  BOARD_ID int(11) NOT NULL AUTO_INCREMENT,
  CATEGORY_CD varchar(200) NOT NULL,
  OPEN_YN char(1) NOT NULL,
  TOP_YN char(1) NOT NULL,
  POPUP_YN char(1) DEFAULT NULL,
  POPUP_START_DT datetime DEFAULT NULL,
  POPUP_END_DT datetime DEFAULT NULL,
  TITLE varchar(200) NOT NULL,
  CONTENT longtext NOT NULL,
  CREATE_ID varchar(200) NOT NULL,
  CREATE_DT datetime NOT NULL,
  UPDATE_ID varchar(200) DEFAULT NULL,
  UPDATE_DT datetime DEFAULT NULL,
  DELETE_YN char(1) NOT NULL,
  FILE_ID varchar(200) DEFAULT NULL,
  PRIMARY KEY (BOARD_ID)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- 2024.11.19 제예솔 / 게시판 연관회사 테이블 create
CREATE TABLE T_BOARD_OPEN_COMPANY (
    BOARD_ID INT NOT NULL,
    COMPANY_ID INT NOT NULL,
    COMPANY_NAME VARCHAR(50) NOT NULL,
    PRIMARY KEY (BOARD_ID, COMPANY_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2024.11.19 제예솔 / 게시판 및 게시판 연관회사 테이블 data insert
INSERT INTO T_BOARD_OPEN_COMPANY (BOARD_ID,COMPANY_ID,COMPANY_NAME) VALUES (1,1,'타이거컴퍼니');
INSERT INTO T_COMPANY_INFO (COMPANY_ID, COMPANY_NAME, MANAGER_ID, UPDATE_DT, UPDATE_ID, NOTES, USE_YN) VALUES(1, '타이거컴퍼니', 'testAdmin01@test.com', NULL, NULL, NULL, 'Y');
INSERT INTO T_COMPANY_INFO (COMPANY_ID, COMPANY_NAME, MANAGER_ID, UPDATE_DT, UPDATE_ID, NOTES, USE_YN) VALUES(2, 'HPI', 'testAdmin01@test.com', NULL, NULL, NULL, 'Y');
INSERT INTO T_COMPANY_INFO (COMPANY_ID, COMPANY_NAME, MANAGER_ID, UPDATE_DT, UPDATE_ID, NOTES, USE_YN) VALUES(3, '네패스', 'testAdmin01@test.com', NULL, NULL, NULL, 'Y');

-- 2024.11.20 제예솔 / 회사 테이블 create
CREATE TABLE T_COMPANY_INFO (
  COMPANY_ID int(11) NOT NULL AUTO_INCREMENT,
  COMPANY_NAME varchar(50) NOT NULL,
  MANAGER_ID varchar(50) NOT NULL,
  UPDATE_DT datetime DEFAULT NULL,
  UPDATE_ID varchar(50) DEFAULT NULL,
  NOTES varchar(200) DEFAULT NULL,
  USE_YN char(1) NOT NULL,
  PRIMARY KEY (`COMPANY_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- 2024.11.21 제예솔 / file 첨부 테이블 create
CREATE TABLE t_file (
    FILE_ID VARCHAR(20) NOT NULL,
    SEQ INT NOT NULL,
    CATEGORY VARCHAR(20) NOT NULL,
    FILE_PATH VARCHAR(200) NOT NULL,
    FILE_NAME VARCHAR(200) NOT NULL,
    ORIGIN_FILE_NAME VARCHAR(200) NOT NULL,
    FILE_SIZE BIGINT NOT NULL,
    DELETE_YN CHAR(1) NOT NULL,
    PRIMARY KEY (FILE_ID, SEQ)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO T_BOARD
(BOARD_ID, CATEGORY_CD, OPEN_YN, TOP_YN, POPUP_YN, POPUP_START_DT, POPUP_END_DT, TITLE, CONTENT, CREATE_ID, CREATE_DT, UPDATE_ID, UPDATE_DT, DELETE_YN, FILE_ID)
VALUES(1, 'SYSTEM', '', '', '', NULL, NULL, '시스템관리테스트', '시스템관리 테스트 글내용', 'geenyou@naver.com', '2024-11-29 00:00:00.000', NULL, NULL, 'N', NULL);
INSERT INTO T_BOARD
(BOARD_ID, CATEGORY_CD, OPEN_YN, TOP_YN, POPUP_YN, POPUP_START_DT, POPUP_END_DT, TITLE, CONTENT, CREATE_ID, CREATE_DT, UPDATE_ID, UPDATE_DT, DELETE_YN, FILE_ID)
VALUES(2, 'SYSTEM', NULL, NULL, NULL, NULL, NULL, '시스템테스트1', '<h1>테스트테스트</h1>', 'geenyou@naver.com', '2024-11-20 16:55:19.000', NULL, NULL, 'N', NULL);
INSERT INTO T_BOARD
(BOARD_ID, CATEGORY_CD, OPEN_YN, TOP_YN, POPUP_YN, POPUP_START_DT, POPUP_END_DT, TITLE, CONTENT, CREATE_ID, CREATE_DT, UPDATE_ID, UPDATE_DT, DELETE_YN, FILE_ID)
VALUES(3, 'SYSTEM', NULL, NULL, NULL, NULL, NULL, '정보작성1', '<p><strong>테스트내용</strong></p>', 'geenyou@naver.com', '2024-11-20 16:58:35.000', NULL, NULL, 'N', NULL);
INSERT INTO T_BOARD
(BOARD_ID, CATEGORY_CD, OPEN_YN, TOP_YN, POPUP_YN, POPUP_START_DT, POPUP_END_DT, TITLE, CONTENT, CREATE_ID, CREATE_DT, UPDATE_ID, UPDATE_DT, DELETE_YN, FILE_ID)
VALUES(4, 'SYSTEM', NULL, NULL, NULL, NULL, NULL, '작성테스트', '<p><strong>잘되나요?</strong></p>', 'geenyou@naver.com', '2024-11-20 16:59:41.000', NULL, NULL, 'N', NULL);