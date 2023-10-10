### 프로젝트 구조
- service1은 ID를 서비스2의 "get/name/{id}"로 API요청하고 이름을 받아옴

- service2의 NameController - 서비스2에서는 고객의 id를  받으며 해당하는 이름을 반환하는 기능 구현
  
--------------------------
### 서비스디스커버리 구현
- ServiceDiscoveryApplication에 @EnableEurekaServer활성화

- Service1Application에 @EnableDiscoveryClient 명시

- service1의 Discoveryservice -유레카서버에 등록된 서비스목록을 가져온다. 

- DiscoveryClient는 유레카클라이언트에서 제공하는 객체로 서비스의 정보를 찾아 제공한다.

build.gradle에 추가 / application.yml설정 / 스프링 유레카 대시보드에서 service1,2 등록된것 확인

--------------------------
### HTTP 통신 구현
- service1 > Service1Application 에 @EnableDiscoveryClient (Ribbon) / @EnableFeignClients (Feign)
- 1. RestTemplateClientCommunicator / 2. RibbonClientCommunicator / 3. FeignClientCommunicator
 
<HTTP 통신으로 총 3가지 방식으로 통신이 있음>

1) 일반 RestTemplate을 이용한 통신

RestTemplateClientCommunicator에서 discoveryClient를 불러와 사용함 - 부하분산 불가능

2) Ribbon을 탑재한 RestTemplate을 이용한 통신
   
@EnableDiscoveryClient붙여서 DiscoveryClient활성화 

RestTemplate에 @LoadBalanced붙여서 Ribbon탑재시킴

RibbonClientCommunicator에서 RestTemplate주입후 Ribbon이 동작됨

3) Feign을 이용한 통신
   
@EnableFeignClients - Feign Client임을 선언 

service1 > rest > FeignClientCommunicator에서 서비스2로 호출함을 명시
 
--------------------------
### Zuul 구현 (API Gateway)
- PreFilter로 필터구현 - 요청을 로깅하고 인증  / application.yml에서 설정 (:zuul)

-로깅구현

 RequestContext객체에서 HttpServletRequest를 가져와
 
 로깅에 필요한 HttpBody, 요청Uri, 인증에 필요한 HttpHeader정보 추출
 
-인증구현

 API요청시 HttpHeader의 Authorzation에 키값전송한것
 
 Zuul에서 추출하여 유효하지않으면 401 에러반환
 
-서비스1에서 커스텀HttpHeader 확인

 @RequestHeader("foo") String foo

--------------------------
### Hystrix구현
- HystrixTestController - GetMapping ("/hystrix1", "/hystrix2")

- HystrixTestService -  @HystrixCommand

- HystrixDAO
 
-써킷브레이커 - 일정비율 에러발생시 서킷오픈하여 기능차단

-타임아웃 - 특정시간동안 기능완료되지않으면 예외발생 

-폴백 - 에러대비하여 미리준비된 응답 반환

-벌크헤드패턴 - 각기능이 사용가능한 자원을 제한하여 기능장애가 전체시스템에 영향 못미치도록 차단
 
--------------------------
##### 참조
- https://enjoy-dev.tistory.com/12
- https://techblog.lotteon.com/%EB%89%B4%EC%98%A8%EC%9D%B4%EB%93%A4%EC%9D%98-%EC%B2%AB-msa-%EC%84%9C%EB%B9%84%EC%8A%A4-%EB%8F%84%EC%A0%84%EA%B8%B0-d336186a7e31






















