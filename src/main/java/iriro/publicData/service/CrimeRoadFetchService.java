package iriro.publicData.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service @RequiredArgsConstructor
public class CrimeRoadFetchService{

    // API 인증키
    String serviceKey = "L59Y0SCX-L59Y-L59Y-L59Y-L59Y0SCX4Q";
    // web 요청 객체
    private final WebClient webClient = WebClient.builder().build();

    // (공공데이터 수집) 범죄자도로명
    public Map<String,Object> fetchCrimeRoad(){ // 원래 boolean. test 중
        String uri = "https://apis.data.go.kr/1383000/sais/SexualAbuseNoticeAddrService";
        uri+= "?serviceKey="+serviceKey;
        for(int i=1;i<) {
            uri +=
        }
    }


}
