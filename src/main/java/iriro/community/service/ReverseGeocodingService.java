package iriro.community.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service

public class ReverseGeocodingService {

    // ㅇㅍㄹㅋㅇㅅㅍㄹㅍㅌㅅ에 적힌 변수명이랑 똑같이 ${} 안에 적으면 스프링이 실행될 때 알아서 카카오키값을 채워준다고 함!
    @Value("${kakao.api-key}")
    private String kakaoApikey;

    // WebClient 선언
    private final WebClient webClient = WebClient.builder().build();

    public Map<String,Object> findAddress(String x , String y){
        String uri = "https://dapi.kakao.com/v2/local/geo/coord2address.json";
        uri += "?x=" + x;
        uri += "&y=" + y;

        return webClient.get()
                .uri(uri)
                .header("Authorization","KakaoAK " + kakaoApikey )
                .retrieve() // 반환/통신/응답 결과 수신 함수
                .bodyToMono(Map.class) // 반환 값을 자바 타입으로 변환 , 즉] 반환타입이 JSON 이면 MAP 받는다.
                .block(); // 동기(처리가 끝날 때까지 대기상태) 방식으로 결과 반환
    }
}

