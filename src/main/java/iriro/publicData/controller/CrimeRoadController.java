package iriro.publicData.controller;

import iriro.publicData.service.CrimeRoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin( value = "http://localhost:5173" , exposedHeaders = "Authorization")
public class CrimeRoadController {

    private final CrimeRoadService cs;

    // 범죄자도로명 조회
    @GetMapping("/crimeroad")
    public ResponseEntity<?> getCrimeRoad(){
        return ResponseEntity.ok(cs.getCrimeRoad());
    }
}
