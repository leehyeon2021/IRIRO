package iriro.saferoute.service;

import iriro.saferoute.repository.LocationLogRepository;
import iriro.saferoute.repository.RoutePointLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RouteLogSaveService {
    // 사용자 로그 저장하는 서비스
    private final LocationLogRepository locationLogRepo;
    private final RoutePointLogRepository routePointLogRepo;
}
