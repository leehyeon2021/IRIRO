package iriro.publicData.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class RefreshAllDataService {

// 저장/조회 클래스 분리했으므로 아래 수정 필요
    private final FacilitySafeFetchService ffs;
    private final CrimeRoadFetchService cfs;

    // 전체 삭제 후 새 데이터로 교체
    //@Scheduled(cron = "0 0 1 1 * *")  // 매월 1일 새벽 2시
    public boolean refreshAllData() {

//        // 전체 저장 (자동 업데이트o)
//            // sleep 같은 거 해야할 것 같음
//        ffs.fetchPoliceStation();    // 경찰서
//        ffs.fetchSafeHouse();        // 안심지킴이집
//        ffs.fetchSafeFac();          // 안전시설물(보안등,CCTV,안전벨)
//        cfs.fetchCrimeRoad();        // 위험도로명 - 가장 오래 걸림

        // 1. 결과와 상관없이 일단 4개의 업데이트 메서드를 모두 실행합니다.
        boolean isPoliceUpdated = ffs.fetchPoliceStation();
        boolean isSafeHouseUpdated = ffs.fetchSafeHouse();
        boolean isSafeFacUpdated = ffs.fetchSafeFac();
        boolean isCrimeRoadUpdated = cfs.fetchCrimeRoad();

        // 2. 4개가 모두 성공했는지(true) 확인해서 반환합니다.
        return isPoliceUpdated && isSafeHouseUpdated && isSafeFacUpdated && isCrimeRoadUpdated;

    }
}
