package com.assignment.models.entities.auth;


import com.assignment.models.entities.location.District;
import com.assignment.models.entities.location.Province;
import com.assignment.models.entities.location.Ward;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_locations")
public class UserLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "ward_id")
    private Ward ward;

    @Column(
        name = "detail_address",
        nullable = false,
        columnDefinition = "nvarchar(255)"
    )
    private String detailAddress;

    public boolean isSame(UserLocation userLocation) {
        return this.user.getId() == userLocation.getUser().getId()
            && this.province.getId() == userLocation.getProvince().getId()
            && this.district.getId() == userLocation.getDistrict().getId()
            && this.ward.getId() == userLocation.getWard().getId()
            && this.detailAddress.equals(userLocation.getDetailAddress());
    }


}
