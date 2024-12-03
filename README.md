# tigrisCRM
tigris-crm 서비스 개발 

<br/><br/>

# 개발 반영방법 (crmdevuser 로만 반영 가능) 
1. 로컬(.jar) 파일 개발서버에 반영
- 1. 로컬의 jar 최신화 : ``` ./gradlew clean bootJar ``` 
- 2. jar파일 서버에 업로드 : 
      powerShell > ``` scp "C:/Users/user/Desktop/crm-0.0.1-SNAPSHOT.jar" crmdevuser@192.168.0.240:/home/crmdevuser ```
- 3. crmdevuser 로그인
- 4. 백그라운드 실행 명령어 
      ``` nohup java -jar /home/crmdevuser/crm-0.0.1-SNAPSHOT.jar > /home/crmdevuser/application.log 2>&1 & ```
- 5. 프로세스 확인
      ``` ps -ef | grep java ```
- 6. 프로세스 kill
      ``` sudo kill -9 [pid] ```

<br/><br/>

2. DB 수정 방법
  - mysql 콘솔 접속 : ``` sudo mysql -u root ```
  - db조회 : ``` > show databases; ```
  - db선택 : ``` > use TigerCRM; ```
  - table 조회 : ``` show tables; ```

<br/><br/>

3. 개발서버 URL
      ``` http://192.168.0.240:8081/login ```



<br/><br/>


4. 젠킨스 관련
   - 서버 : ``` http://192.168.0.240:8080/ ```
   - 로그확인 : ``` sudo tail -f /var/log/crm/application.log ```

<br/><br/>


5. webHook 쓰려면 ngrok써야함. 
ngrok 사용이유 > 아이피 제한을 걸어뒀기에,  우회하는 시스템을 써야지 push시 반응함.
- ngrok 서버접속 : ```https://ngrok.com/```
- ngrok 다운로드 후 exe실행
- 명령어 실행 : ``` ngrok http 192.168.0.240:8081 ```
> 실행 안될 시
- 작업관리자 > 기존 ngrok 프로세스 삭제 후 위 과정 실행
        
