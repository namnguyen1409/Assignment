package com.assignment.models.entities.location;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
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

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    private Set<Ward> wards;

    public District() {
    }

    public District(long id, String name, Province province, double latitude, double longitude, Set<Ward> wards) {
        this.id = id;
        this.name = name;
        this.province = province;
        this.latitude = latitude;
        this.longitude = longitude;
        this.wards = wards;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Set<Ward> getWards() {
        return wards;
    }

    public void setWards(Set<Ward> wards) {
        this.wards = wards;
    }

}
