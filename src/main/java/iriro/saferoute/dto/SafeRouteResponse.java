package iriro.saferoute.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SafeRouteResponse {
    private double startLat;
    private double startLng;
    private double endLat;
    private double endLng;
    private int totalTime;
    private int totalDistance;
    private int safeScore;

    private List<RoutePointDto> path;
}
