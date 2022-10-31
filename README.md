밥은 먹고 다니니
=======
2022 모바일프로그래밍 2분반 팀10 API
-----

환경  
------
Java 18  
Spring Boot 2.6.13  

실행방법(debug)  
com.MoP2022.Team10.Team10ApiApplication - main() 실행

구조
------
mapping : http url 매칭

db : DB 연결 및 query 사용  
 - db.service : query 사용    
 - db.model : select시 데이터를 담을 객체    

process : 처리를 위한 기타 코드

설정파일 위치
--
~/src/main/resources/application.yml

샘플
--
http://localhost:8081/  
http://localhost:8081/dbTest  
http://localhost:8081/processTest  
http://localhost:8081/processDBTest
