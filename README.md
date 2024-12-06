# tigrisCRM
tigris-crm 서비스 개발 


<br/><br/>


> 로컬(.jar)파일을 개발서버에 반영방법 (crmdevuser 로만 반영 가능)(젠킨스 사용시 불필요) 
1. 로컬의 jar 최신화 : ``` ./gradlew clean bootJar ``` 
2. jar파일 서버에 업로드 : 
   powerShell > ``` scp "C:/Users/user/Desktop/crm-0.0.1-SNAPSHOT.jar" crmdevuser@192.168.0.240:/home/crmdevuser ```
3. crmdevuser 로그인
4. 백그라운드 실행 명령어 
     ``` nohup java -jar /home/crmdevuser/crm-0.0.1-SNAPSHOT.jar > /home/crmdevuser/application.log 2>&1 & ```
5. 프로세스 확인
   ``` ps -ef | grep java ```
6. 프로세스 kill
   ``` sudo kill -9 [pid] ```


<br/><br/>


> DB 수정 방법
1. mysql 콘솔 접속 : ``` sudo mysql -u root ```
2. db조회 : ``` > show databases; ```
3. db선택 : ``` > use TigerCRM; ```
4. table 조회 : ``` show tables; ```


<br/><br/>


> 개발서버 URL
     ``` http://192.168.0.240:8081/login ```


<br/><br/>


> 젠킨스 관련
   - 서버 : ``` http://192.168.0.240:8080/ ```
   - 로그확인 : ``` sudo tail -f /var/log/crm/application.log ```

<br/><br/>


5. webHook(jenkins) 쓰려면 ngrok써야함. 
   ngrok 사용이유 : 개발서버에 아이피 제한을 걸어뒀기에,  우회하는 시스템을 써야지 push시 반응함.
      > ngrok 실행방법
      - ngrok 서버접속 후 로그인: ```https://ngrok.com/```
      - ngrok 설치 후 exe실행
      - 명령어 실행 : ``` ngrok http 192.168.0.240:8080(프로젝트 포트 8081아닌, 젠킨스 8080 포트 등록) ``` 
      - 깃허브 웹훅에 등록 : settings -> webhooks -> ``` https://f4d3-61-74-221-49.ngrok-free.app/github-webhook/ ```
      <br/>
      
      > 실행 안될 시
      - 작업관리자 -> 기존 ngrok 프로세스 삭제 후 위 과정 실행
