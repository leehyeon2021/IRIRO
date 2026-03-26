package iriro.saferoute.test;

import iriro.saferoute.dto.RiskPointDto;
import iriro.saferoute.dto.RouteRequestDto;
import iriro.saferoute.dto.SafetyFacPointDto;

import java.math.BigDecimal;
import java.util.List;

// 가상 DB
public class TestSampleCode {

    // 1. 테스트요청: 출발지와 목적지 위/경도
    public static final RouteRequestDto testRouteRequest =
            new RouteRequestDto( 37.3895300, 126.959400, 37.3976478, 126.9312600);


    // 2. 가상 위험 테이블의 위/경도를 뽑아 온 값 ( 실제로는 위험테이블에서 조회해와서 리스트화 시켜야함 )
    public static final List<RiskPointDto> dangerRoutePoints = List.of(
            // ===== 경로 근처 위험점 =====
            new RiskPointDto("길", new BigDecimal("37.389413796907185"), new BigDecimal("126.95896112442831"), 1, 1),
            new RiskPointDto("길", new BigDecimal("37.39098303954915"), new BigDecimal("126.95785840291721"), 2, 2),
            new RiskPointDto("로", new BigDecimal("37.390335859209024"), new BigDecimal("126.95599747917913"), 1, 3),
            new RiskPointDto("길", new BigDecimal("37.389222039983025"), new BigDecimal("126.95265892511556"), 3, 4),
            new RiskPointDto("대로", new BigDecimal("37.38860541319803"), new BigDecimal("126.95088410379881"), 1, 5),

            // ===== bbox로 걸러져야 할 먼 위험점 =====
            new RiskPointDto("길", new BigDecimal("37.4005000"), new BigDecimal("126.9705000"), 1, 6),
            new RiskPointDto("로", new BigDecimal("37.4050000"), new BigDecimal("126.9650000"), 2, 7),
            new RiskPointDto("대로", new BigDecimal("37.3820000"), new BigDecimal("126.9800000"), 1, 8),
            new RiskPointDto("길", new BigDecimal("37.4100000"), new BigDecimal("126.9400000"), 4, 9),
            new RiskPointDto("로", new BigDecimal("37.3700000"), new BigDecimal("126.9300000"), 1, 10)
    );

    // 3. 가상의 안전한 시설의 위치 테이블
    public static final List<SafetyFacPointDto> safeRoutePoint = List.of(
            new SafetyFacPointDto("보안등", 3, new BigDecimal("37.3894200"), new BigDecimal("126.9590500")),
            new SafetyFacPointDto("CCTV", 1, new BigDecimal("37.3909800"), new BigDecimal("126.9578600")),
            new SafetyFacPointDto("안심지킴이집", 1, new BigDecimal("37.3900100"), new BigDecimal("126.9550100")),
            new SafetyFacPointDto("보안등", 2, new BigDecimal("37.3897200"), new BigDecimal("126.9541300")),
            new SafetyFacPointDto("CCTV", 1, new BigDecimal("37.3889500"), new BigDecimal("126.9519600")),
            new SafetyFacPointDto("안전벨", 1, new BigDecimal("37.3890800"), new BigDecimal("126.9522600")),
            new SafetyFacPointDto("보안등", 2, new BigDecimal("37.3890500"), new BigDecimal("126.9502300")),
            new SafetyFacPointDto("경찰서", 1, new BigDecimal("37.3896800"), new BigDecimal("126.9494000")),
            new SafetyFacPointDto("안심지킴이집", 1, new BigDecimal("37.3922400"), new BigDecimal("126.9445300")),
            new SafetyFacPointDto("CCTV", 1, new BigDecimal("37.3932700"), new BigDecimal("126.9405000"))
    );
}
