package iriro.publicData.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity @Table(name = "facility_safe")
@Data @Builder
public class FacilitySafeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fac_id")
    private Integer facId;

    @Column(name = "fac_type", nullable = false, length = 50)
    private String facType;

    @Column(name = "fac_sgg", length = 50)
    private String facSgg;

    @Column(name = "fac_name", length = 50)
    private String facName;

    @Column(name = "fac_add", length = 300)
    private String facAdd;

    @Column(name = "fac_lat")
    private Double facLat;

    @Column(name = "fac_lng")
    private Double facLng;

    @Column(name = "fac_count")
    private Integer facCount;

    @Column(name = "fac_use", length = 10)
    private String facUse;

    @Column(name = "fac_tel", length = 30)
    private String facTel;
}

