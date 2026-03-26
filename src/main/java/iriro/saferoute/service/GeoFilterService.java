package iriro.saferoute.service;

import iriro.saferoute.dto.BboxDto;
import iriro.saferoute.dto.RiskPointDto;
import iriro.saferoute.dto.RoutePointDto;
import org.springframework.stereotype.Service;

import java.util.List;

// 역할: bbox생성, bbox포함 여부, 두 좌표 거리 계산, 경로와 점 사이 최소 거리 계산, sequence 찾기
@Service
public class GeoFilterService {
    private static final double EARTH_RADIUS = 6371000; // 지구 반지름(상수)

    // Bbox안에 있는지 체크하는 함수 .. 1차 필터링
    public boolean isInsideBbox(double lat, double lng, BboxDto bbox){
        return lat >= bbox.getMinLat()
                && lat <= bbox.getMaxLat()
                && lng >= bbox.getMinLng()
                && lng <= bbox.getMaxLng();
    }

    // boundingBox를 만드는 함수 .. 1차 필터링
    public BboxDto createBox(List<RoutePointDto> routePoints){

        // 만약 리스트가 비어있다면
        if( routePoints == null || routePoints.isEmpty()){
            System.out.println("경로 좌표가 비어있습니다.");
            return null;
        }

        double minLat = routePoints.get(0).getLatitude().doubleValue();
        double maxLat = routePoints.get(0).getLatitude().doubleValue();
        double minLng = routePoints.get(0).getLatitude().doubleValue();
        double maxLng = routePoints.get(0).getLatitude().doubleValue();

        // 가장 크고 작은 위 경도 값 구하기
        for(RoutePointDto point : routePoints){
            minLat = Math.min(minLat, point.getLatitude().doubleValue() );
            minLng = Math.min(minLng, point.getLongitude().doubleValue() );
            maxLat = Math.max(maxLat, point.getLatitude().doubleValue() );
            maxLng = Math.max(maxLng, point.getLongitude().doubleValue() );
        }

        // 50m 정도 margin
        double latMargin = 50.0 / 111000.0;
        double centerLat = (minLat + maxLat) / 2.0;
        double lngMargin = 50.0 / (111000.0 * Math.cos(Math.toRadians(centerLat)));

        // BboxDto 반환(위험구역)
        return new BboxDto(
                minLat - latMargin,
                maxLat + latMargin,
                minLng - lngMargin,
                maxLng + lngMargin
        );
    }

    // 좌표 1개당 경로 좌표들 중 최소거리를 구하는 함수
    public double getMinDistance(List<RoutePointDto> routePoints, double latitude, double longitude){
        double minDistance = Double.MAX_VALUE;

        for(RoutePointDto point : routePoints){
            double pointLat = point.getLatitude().doubleValue();
            double pointLng = point.getLongitude().doubleValue();

            double distance = distanceMeter(pointLat, pointLng, latitude, longitude);

            minDistance = Math.min(minDistance, distance);
        }

        return minDistance;
    }

    // 위험지역에 들어간 최단거리 경로 좌표의 순서를 구하는 함수
    public int getSequence(List<RoutePointDto> routePoints, RiskPointDto riskZone){
        double minDistance = Double.MAX_VALUE;
        int sequence = 0;

        double riskLat = riskZone.getLatitude().doubleValue();
        double riskLng = riskZone.getLongitude().doubleValue();

        for(RoutePointDto point : routePoints){
            double pointLat = point.getLatitude().doubleValue();
            double pointLng = point.getLongitude().doubleValue();

            double distance = distanceMeter(pointLat, pointLng, riskLat, riskLng);

            if(minDistance > distance){
                minDistance = distance;
                sequence = point.getSequence();
            }
        }

        return sequence;
    }

    // 거리를 구하는 함수 ( m단위로 반환 ) -> Haversine 공식
    public double distanceMeter(double lat1, double lng1, double lat2, double lng2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

}
