package iriro.publicData.entity;

import jakarta.persistence.*;

@Entity @Table(name = "facility_safe")
public class FacilitySafeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fac_id;
    private String fac_type;
    private String fac_sgg;
    private String fac_name;
    private String fac_add;
    private Double fac_lat;
    private Double fac_lng;
    private Integer fac_count;
    private String fac_use;
    private String fac_tel;
}
