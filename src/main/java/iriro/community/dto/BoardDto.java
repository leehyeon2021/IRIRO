package iriro.community.dto;

import iriro.community.entity.BoardEntity;
import iriro.saferoute.entity.LocationlogEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data @Builder @AllArgsConstructor @NoArgsConstructor // 롬복
public class BoardDto {
    private Integer boardId;
    private Integer userId;
    private Long logId;
    private String nickname;
    private String boardTitle;
    private String boardContent;
    private Integer recommendCount;
    private String createdAt;

    // 화면에 보여주기 위해 추가할 정보
    private BigDecimal startLatitude;
    private BigDecimal startLongitude;
    private BigDecimal endLatitude;
    private BigDecimal endLongitude;
    private Integer total_distance;


    // + Dto --> Entity 변환 // 사용자가 직접 입력한 데이터만. 플레이팅화
    // 사용자가 출발지 위도나 경도를 직접 입력하지 않음. 그러니 로그번호만 선택해서 보내는 것.
    public BoardEntity toEntity(){
        LocationlogEntity locationdummy = LocationlogEntity.builder() // logId만 있는 객체 생성
                .logId(this.logId)
                .build();

        return BoardEntity.builder()
                .locationlogEntity(locationdummy) // logId만 들어있는 객체
                .boardTitle(this.boardTitle)
                .boardContent(this.boardContent)
                .recommendCount(0) // 등록시 기본값

                .build();
    }
}
