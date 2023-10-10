package kr.pe.chani.simplemsa.service1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/*
## DIscoveryservice-유레카서버에 등록된 서비스목록을 가져온다. ##
DiscoveryClient는 유레카클라이언트에서 제공 객체로 서비스의 정보를 찾아 제공한다.

<HTTP 통신으로 총 3가지 방식으로 통신이 있음>

1. 일반 RestTemplate을 이용한 통신
RestTemplateClientCommunicator에서 discoveryClient를 불러와 사용함 - 부하분산 불가능

2. Ribbon을 탑재한 RestTemplate을 이용한 통신
@EnableDiscoveryClient붙여서 DiscoveryClient활성화 
RestTemplate에 @LoadBalanced붙여서 Ribbon탑재시킴
RibbonClientCommunicator에서 RestTemplate주입후 Ribbon이 동작됨

3. Feign을 이용한 통신
@EnableFeignClients - Feign Client임을 선언 
FeignClientCommunicator에서 서비스2로 호출함을 명시
*/
@SpringBootApplication
@EnableDiscoveryClient 
@EnableFeignClients 
@EnableCircuitBreaker
public class Service1Application {

	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(Service1Application.class, args);
	}

}
