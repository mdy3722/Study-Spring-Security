# ✍️ Study-Spring-Security
공부 방식 : [인프런 강의 - 스프링부트 시큐리티 & JWT 강의 (최주호 강사님)](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0/dashboard) 를 참고한 실습

### 학습 내용
- 스프링 시큐리티의 동작 원리
  - 시큐리티 설정 및 권한 처리
- 스프링 시큐리티 OAuth2.0
  - 구글/페이스북/네이버 로그인 구현
- 스프링 시큐리티 JWT 서버 구축

---

### 사전 준비
**1. MySQL(혹은 다른 DBMS) DB 및 계정 생성**
```sql
create user 'mdy'@'%' identified by 'mdy1234';   -- mdy라는 ID, mdy1234라는 비밀번호의 계정 생성 (@'%'는 어디서든 접속 허용 (IP 제한 없음)을 의미)
GRANT ALL PRIVILEGES ON *.* TO 'mdy'@'%';  -- 생성한 mdy 사용자에게 모든 DB, 모든 테이블에 대한 권한 부여
create database studySpringSecurity;  -- 데이터베이스 studySpringSecurity 생성
use studySpringSecurity;    -- 방금 만든 studySpringSecurity 데이터베이스를 사용하겠다 선언
```

**2. 프로젝트 세팅 (의존성)**
- Lombok: Getter/Setter, 생성자 등 반복 코드를 줄여주는 코드 자동 생성 라이브러리
- Spring Boot DevTools: 서버 재시작 없이 수정 사항을 바로 반영해주는 개발 편의 도구
- Spring Web: REST API 및 웹 애플리케이션 개발을 위한 필수 의존성 (Spring MVC 포함)
- Mustache: 서버 사이드 템플릿 엔진, HTML 페이지를 동적으로 렌더링
- Spring Data JPA: 자바 ORM(Object Relational Mapping) 기반 데이터베이스 처리 라이브러리
- MySQL Driver: MySQL 데이터베이스 연결을 위한 JDBC 드라이버
- Spring Security: 인증 및 권한 관리 등 보안 관련 기능 제공

---

### ⭐ Spring Security란?
Spring 기반 애플리케이션의 인증(Authentication)과 인가(Authorization)을 담당하는 보안 프레임워크

### ⭐ Spring Security 동작 원리
1️⃣ 인증(Authentication)
- 사용자의 요청 → Security 필터 체인 진입
- UsernamePasswordAuthenticationFilter에서 Authentication 객체 생성
- AuthenticationManager → AuthenticationProvider → UserDetailsService로 사용자 정보 검증
- 인증 성공 시 SecurityContextHolder에 Authentication 저장

2️⃣ 인가(Authorization)
- 인증된 사용자가 리소스 접근 시 → FilterSecurityInterceptor 작동
- SecurityContextHolder에서 인증 정보 조회
- AccessDecisionManager가 권한 체크 → 접근 허용 또는 403 반환

---

### 기타 정리
[Spring Security 정리한 나의 벨로그](https://velog.io/@mdy3722/Spring-Security-%EC%A0%95%EB%A6%AC)
