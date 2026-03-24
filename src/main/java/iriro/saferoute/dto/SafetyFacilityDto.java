package iriro.saferoute.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor @NoArgsConstructor @Data @Builder
public class SafetyFacilityDto {

    private String facilityName; // 안전 시설물 명
    private BigDecimal latitude; // 위도
    private BigDecimal longitude; // 경도

}
