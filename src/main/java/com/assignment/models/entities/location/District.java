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
}
