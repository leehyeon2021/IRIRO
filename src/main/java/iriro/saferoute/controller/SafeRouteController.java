package iriro.saferoute.controller;

import iriro.saferoute.dto.RouteRequestDto;
import iriro.saferoute.service.SafeRouteService;
import iriro.saferoute.service.TmapRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api") //공통 주소 입력
public class SafeRouteController {

    private final SafeRouteService safeRouteSvc;

    // 안전 경로 반환
    @PostMapping("/saferoute")
    public ResponseEntity<?> test2(@RequestBody RouteRequestDto routeRequestDto){
        return ResponseEntity.ok( safeRouteSvc.getSafeRoute(routeRequestDto) );
    }

}
