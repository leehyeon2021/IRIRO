package iriro.saferoute.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SaveRoutePointsLogDto {
    Long point_id;
    Long log_id;
    Double latitude;
    Double longitude;
    Long sequence;
}
