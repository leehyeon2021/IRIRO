package iriro.article.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class ArticleCrimeFilter {

    // ChatClient 직접 빌드
    @Value("${cloudflare.account-id}")
    private String apiKey;

    private String url;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 서버가 켜질 때 API KEY가 포함된 URL주소 생성
    @PostConstruct
    public void init() {
        this.url = "https://generativelanguage.googleapis.com/v1beta" +
                    "/models/gemini-2.5-flash:generateContent?key=" + apiKey;
    }

    private static final String[] blackList = {
            "드라마", "출연", "방송", "연예", "화보", "영화", "공개", "웹툰", "매매가격", "비트코인",
            "주가", "주식", "코스피", "증권", "부동산", "날씨", "스포츠", "박재홍의 한판승부",
            "의원", "공천", "특혜", "청탁", "압수수색", "비자금", "선거", "정치", "국회", "기업",
            "노조", "내사", "언론 탄압", "아이돌", "BTS", "NCT"
    };
    private static final String[] crimeKeywords = {
            "강도", "절도", "폭행", "성범죄", "흉기", "묻지마", "살인", "상해",
            "칼부림", "스토킹", "침입", "치안", "순찰", "우범", "안전"
    };


    // 저장할 가치 있는 기사인지 판별
    public boolean isValid(String title, String content) {

        // === AI 호출 전 키워드 체크 ===

        // 1. 필수값 체크
        if (title == null || content == null || title.isBlank() || content.isBlank()) {
            return false;
        }

        // 2. 블랙리스트 체크
        for (String word : blackList) {
            if (title.contains(word) || content.contains(word)) {
                System.out.println("[블랙리스트 탈락] " + title);
                return false;
            }
        }

        // 3. 서울 관련 기사인지 체크
        boolean hasSeoul = content.contains("서울") || title.contains("서울");
        if (!hasSeoul) {
            System.out.println("[서울 아님 탈락] " + title);
            return false;
        }

        // 4. 범죄 관련 기사인지 체크
        boolean hasCrimeKeywords = false;
        for (String keyword : crimeKeywords) {
            if (content.contains(keyword) || title.contains(keyword)) {
                hasCrimeKeywords = true;
                break;
            }
        }
        if(!hasCrimeKeywords){
            System.out.println("[범죄 키워드 없음 탈락] "+ title );
            return false;
        }

        // === AI 호출 ===

        // 본문 요약(토큰 절약 및 특수문자 제거)
        String shortContent = content.length() > 500 ? content.substring(0, 500) : content;
        shortContent = shortContent.replace("\"", "'").replace("\n", " ");
        String safeTitle = title.replace("\"", "'");

        // AI 프롬프트
        String prompt = """
                너는 서울 지역 시민 안전 뉴스 분류 AI야.
                아래 기사가 '서울 시민의 안전과 직접 관련된 범죄/치안 뉴스'인지 판단해.
                반드시 'TRUE' 또는 'FALSE' 한 단어만 대답해. 다른 말은 절대 하지 마.

                [TRUE 조건 - 하나라도 해당하면 TRUE]
                - 서울에서 발생한 폭행, 흉기, 살인, 강도, 절도, 성범죄, 스토킹, 묻지마 범죄
                - 서울 치안 강화, 순찰 확대, CCTV 설치 등 시민 안전 정책
                - 서울 우범지역, 귀갓길 위험 관련 뉴스

                [FALSE 조건 - 하나라도 해당하면 FALSE]
                - 정치인 비리, 기업 횡령, 선거 관련 뉴스
                - 연예인, 아이돌 관련 뉴스
                - 서울 외 지역 범죄 뉴스
                - 드라마/영화/웹툰 내용 소개
                - 주식, 부동산, 날씨, 스포츠

                제목: %s
                본문 요약: %s
                """.formatted(safeTitle, shortContent);
        /*String promptText = String.format(
                "너는 서울 지역 시민 안전 뉴스 분류 AI야. 아래 기사가 '서울 시민의 안전과 직접 관련된 범죄/치안 뉴스'인지 판단해. " +
                "반드시 'TRUE' 또는 'FALSE' 한 단어만 대답해. 제목: %s 본문: %s", safeTitle, shortContent
            );*/

        // AI 답변 받기
        try {
            // 구글 Gemini API 규격에 맞는 JSON 바디 생성
            Map<String, Object> body = Map.of(
                    "contents", List.of(
                            Map.of("parts", List.of(
                                    Map.of("text", prompt)
                            ))
                    )
            );
            String requestJson = objectMapper.writeValueAsString(body);

            System.out.println("[전송할 JSON]: " + requestJson);

            // 3. 제미나이 api 호출
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

            // 1분 무료 횟수 위해 대기
            System.out.println("[AI 호출 후 7초 대기...]");
            Thread.sleep(7000);

            // API 호출
            String response = restTemplate.postForObject(url, entity, String.class);

            // 결과 분석 (JSON에서 text 부분만 추출)
            if (response != null) {
                System.out.println("[AI 전체 응답 JSON]: " + response);
                JsonNode root = objectMapper.readTree(response);
                String aiAnswer = root.path("candidates").get(0)
                        .path("content").path("parts").get(0)
                        .path("text").asText().toUpperCase().trim();
                System.out.println("[AI가 판단한 최종 답변]: " + aiAnswer);
                return aiAnswer.contains("TRUE");
            }
        } catch (Exception e) {
            // 통신 오류 (무료 한도 제한: 1분 15회)
            System.err.println("[AI 통신 단계 에러]: " + e.getMessage());
            return false;
        }
        return false;
    }
}