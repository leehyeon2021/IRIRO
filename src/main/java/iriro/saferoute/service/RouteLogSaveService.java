package iriro.saferoute.service;

import iriro.saferoute.dto.RoutePointDto;
import iriro.saferoute.dto.SaveLogDto;
import iriro.saferoute.repository.LocationLogRepository;
import iriro.saferoute.repository.RoutePointLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RouteLogSaveService {
    // 사용자 로그 저장하는 서비스
    private final LocationLogRepository locationLogRepo;
    private final RoutePointLogRepository routePointLogRepo;

    // 경로 추천 시 일단 경로 제외 로그에 저장
    public Long createRouteLog(SaveLogDto saveLogDto){

        return 0L; //로그Id 반환
    }

    // 경로 추천 시 경로 저장
    public boolean saveLogRoute(Long logId, List<RoutePointDto> routePoints){
        // 경로 저장
        return true;
    }

    // 후기 받으면 추가로 저장 update에 가깝다.
    public boolean updateLogReview(Long logId, Integer rating){
        // 후기 저장
        return true;
    }

}
