package iriro.publicData.service;

import iriro.publicData.repository.CrimeRoadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Objects;

@Service @RequiredArgsConstructor
public class CrimeRoadFetchService{

    @Value("${api.pub.service-key}")
    private String serviceKey;

    @Value("${api.pub.crime-road.url}")
    private String crimeRoadUrl;

    private final WebClient webClient = WebClient.builder().build();
    private final CrimeRoadRepository cr;

    // (공공데이터 수집) 범죄자도로명
//    public Map<String , Objects> fetchCrimeRoad(){
//        // * 한 번에 1000번 만 된대
//        int numOfRows = 1000;
//        int totalCount = 0;
//        int totalPages = 1;
//
//        // 반복
//        for(int page=1;page <= total)
//    }


}
