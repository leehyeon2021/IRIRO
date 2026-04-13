package iriro.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeocodingService {

    // Kakao 지오코딩
    @Value("${kakao.api-key}")
    private String kakaoApiKey;
    @Value("${kakao.geocoding.url}")
    private String kakaoGeocodingUrl;

    private final WebClient webClient;

    // 카카오 지오코딩 (CrimeRoad)
    public double[] getCoordsKakao(String address) {
        try {
            String uri = kakaoGeocodingUrl
                    + "?query=" + URLEncoder.encode(address, "UTF-8")
                    + "&analyze_type=similar";

            Map<String, Object> response = webClient.get()
                    .uri(java.net.URI.create(uri))
                    .header("Authorization", "KakaoAK " + kakaoApiKey)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response == null) return null;

            List<Map<String, Object>> documents = (List<Map<String, Object>>) response.get("documents");
            if (documents == null || documents.isEmpty()) return null;

            // 위도경도
            String lat = (String) documents.get(0).get("y");
            String lon = (String) documents.get(0).get("x");
            if (lat == null || lat.isEmpty()) return null;

            return new double[]{Double.parseDouble(lat), Double.parseDouble(lon)};

        } catch (Exception e) {
            System.out.println("카카오 지오코딩 실패: " + address + " / " + e.getMessage());
            return null;
        }
    }


}