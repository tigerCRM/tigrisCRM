# tigrisCRM
tigris-crm 서비스 개발

<br/><br/>

# 개발 반영방법 (crmdevuser 로만 반영 가능) 
1. 로컬(.jar) 파일 개발서버에 반영
  (1) 로컬의 jar 최신화 : ``` ./gradlew clean bootJar ``` 
  2) jar파일 서버에 업로드 : 
      powerShell > ``` scp "C:/Users/user/Desktop/crm-0.0.1-SNAPSHOT.jar" crmdevuser@192.168.0.240:/home/crmdevuser ```
  3) crmdevuser 로그인
  4) 백그라운드 실행 명령어 
      ``` nohup java -jar /home/crmdevuser/crm-0.0.1-SNAPSHOT.jar > /home/crmdevuser/application.log 2>&1 & ```
  5) 프로세스 확인
      ``` ps -ef | grep java ```
  6) 프로세스 kill
      ``` sudo kill -9 [pid] ```
  7) 로그확인
      ``` tail -f  /opt/crmProject/application.log ```

<br/><br/>

2. DB 수정 방법
  1) mysql 콘솔 접속 : ``` sudo mysql -u root ```
  2) db조회 : ``` > show databases; ```
  3) db선택 : ``` > use TigerCRM; ```
  4) table 조회 : ``` show tables; ```

<br/><br/>

3. 개발서버 url
      ``` http://192.168.0.240:8080/login ```
