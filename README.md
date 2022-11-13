### 설계 및 구현
1. 크게 유저서비스, 커뮤니티서비스, gateway-server 각각의 micro-service로 구성했습니다.
   1. [user-service](user-service)
      1. swagger: http://localhost:8100/swagger-ui/index.html
   2. [community-service](community-service)
      1. swagger: http://localhost:8200/swagger-ui/index.html
   3. [gateway-server](gateway)
      1. gateway-route: http://localhost:8300/actuator/gateway/routes
2. 인증, 인가에 처리는 gateway를 통하여 jwt valid-check 하도록 구현했습니다.
3. 마이크로서비스간 내부 통신은 gateway를 통하지 않고 open-feign을 사용해 direct로 통신하게끔 구현했습니다.
4. 로그인과 유저등록(회원가입) API는 인증,인가 없이 접근토록했습니다.
5. 로그아웃시 header token은 redis에 저장해 invaild 하게끔 구현했습니다.
   1. redis expired time은 (토큰 유효시간 - 현재시간)으로 저장
6. 통합테스트시 내부통신 테스트는 mocking으로 대용했습니다.