# tigrisCRM
tigris-crm 서비스 개발

<br/><br/>

# 반영방법

- 로컬(.jar) 파일 개발서버에 반영
1. 로컬의 jar 최신화 : ``` ./gradlew clean bootJar ``` 
2. jar파일 서버에 업로드 : powerShell > ``` scp "C:/Users/user/Desktop/crm-0.0.1-SNAPSHOT.jar" crmdevuser@192.168.0.240:/home/crmdevuser ```
3. crmdevuser 로그인
4. 백그라운드 실행 명령어 ``` nohup java -jar /home/crmdevuser/crm-0.0.1-SNAPSHOT.jar > /home/crmdevuser/application.log 2>&1 & ```

<br/><br/>

# DB 수정 방법
1. mysql 콘솔 접속 : ``` sudo mysql -u root ```
2. db조회 : ``` > show databases; ```
3. db선택 : ``` > use TigerCRM; ```
4. table 조회 : ``` show tables; ```

<br/><br/>

# 개발서버 로그 확인
``` tail -f  /opt/crmProject/application.log ```

<br/><br/>

# 개발 서버 접속 방법
- 관리자 : crmadmin / 드라이브참고
- 서비스(개발 실행용) : crmdevuser / 드라이브참고
- 로컬/개발 서버 접속 방법
```
http://localhost:8080/
http://192.168.0.240:8080/
```
