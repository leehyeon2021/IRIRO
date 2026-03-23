package iriro.saferoute.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RouteResponseDto {
    private BigDecimal start_latitude;
    private BigDecimal start_longitude;
    private BigDecimal end_latitude;
    private BigDecimal end_longitude;

    private Integer totalTime;
    private Integer totalDistance;

    private List<RoutePointDto> routePoints; // 경로가 들어가 있는 리스트
}
