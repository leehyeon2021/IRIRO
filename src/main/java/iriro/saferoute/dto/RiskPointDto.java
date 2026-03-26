package iriro.saferoute.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RiskPointDto {
    private String roadType; //길, 로, 대로
    private BigDecimal latitude; // 위도
    private BigDecimal longitude; // 경도
    private int riskCount; // 같은 도로의 범죄자 수(기본이 1)
}
