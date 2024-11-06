# tigrisCRM
tigris-crm 서비스 개발

<br />

# 개발방법

- 로컬(.jar) 파일 개발서버에 반영
1. powerShell > scp "C:/Users/user/Desktop/crm-0.0.1-SNAPSHOT.jar" crmdevuser@192.168.0.240:/opt/crmProject 로 파일 업로드
2. crmdevuser 로그인
3. 백그라운드 실행 명령어
```
nohup java -jar /opt/crmProject/crm-0.0.1-SNAPSHOT.jar > /opt/crmProject/application.log 2>&1 &
```

<br/><br/>

  - 개발 서버 접속 방법
      - 관리자 : crmadmin / 드라이브참고
      - 서비스 : crmdevuser / 드라이브참고
    

  <br/><br/>

    
  - 로컬서버 접속 방법

        http://localhost:8080/?userEmpNo=1Zknall4JttgJGDZOAshmg==

  <br/><br/>

  
  - 개발서버 접속 방법

        http://192.168.0.240:8080/?userEmpNo=1Zknall4JttgJGDZOAshmg==


  <br/><br/>


  - 개발서버 로그 확인

        tail -f  /opt/crmProject/application.log
  
  
  <br/><br/>

  

# 개발기간
........................


<br/><br/>
