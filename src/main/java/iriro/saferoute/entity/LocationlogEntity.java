package iriro.saferoute.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

// 회원번호1번 -> 비회원

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "location_log")
public class LocationlogEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long log_id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private UserEntity userEntity;  추후에 유저엔티티가져오기

    @Column(nullable = false, precision = 10, scale = 7) // 총 10자리 중에 소수점 이하 자리는 7자리 == decimal(10,7)
    private BigDecimal start_latitude;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal start_longitude;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal end_latitude;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal end_longitude;

    @Column(nullable = false)
    private Integer safety_score;

    @Column(nullable = false)
    private Integer rating;

}
