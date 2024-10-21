package com.assignment.models.entities.location;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "districts")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(
        name = "name",
        nullable = false,
        columnDefinition = "nvarchar(50)"
    )
    private String name;

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

    // Vĩ độ: dùng để xác định vị trí địa lý của quận/huyện
    @Column(
        name = "latitude",
        nullable = false,
        columnDefinition = "decimal(18, 8)"
    )
    private double latitude;

    // Kinh độ: dùng để xác định vị trí địa lý của quận/huyện
    @Column(
        name = "longitude",
        nullable = false,
        columnDefinition = "decimal(18, 8)"
    )
    private double longitude;
    
    @JsonIgnore
    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    private List<Ward> wards = new ArrayList<>();

    public Double calculateDistance(District district) {
        // tính khoảng cách giữa 2 quận/huyện
        double lat1 = Math.toRadians(this.latitude);
        double lon1 = Math.toRadians(this.longitude);
        double lat2 = Math.toRadians(district.getLatitude());
        double lon2 = Math.toRadians(district.getLongitude());
    
        // Bán kính trái đất (kilometre)
        final double R = 6371.01;

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;
    
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(lat1) * Math.cos(lat2) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
    
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    
        return R * c;
        /*
         *  công thức haversine
         *  a = sin²(Δlat/2) + cos(lat1).cos(lat2).sin²(Δlong/2)
         *  c = 2 * atan2(√a, √(1−a))
         *  d = R * c
         */
    }



}
