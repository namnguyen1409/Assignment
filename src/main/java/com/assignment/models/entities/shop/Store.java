package com.assignment.models.entities.shop;

import com.assignment.models.entities.location.District;
import com.assignment.models.entities.location.Province;
import com.assignment.models.entities.location.Ward;
import com.assignment.models.entities.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stores")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "nvarchar(255)")
    private String name;

    @Column(name = "avatar", nullable = false, columnDefinition = "nvarchar(255)")
    private String avatar = "demo.jpg";

    @Column(name = "cover", nullable = false, columnDefinition = "nvarchar(255)")
    private String cover = "demo.jpg";

    @Column(name = "description", nullable = false, columnDefinition = "nvarchar(500)")
    private String description;

    @Column(name = "home", nullable = false, columnDefinition = "nvarchar(max)")
    private String home;

    @ManyToOne
    @JoinColumn(name = "province_id", nullable = false)
    private Province province;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @ManyToOne
    @JoinColumn(name = "ward_id", nullable = false)
    private Ward ward;

    @Column(name = "detail_address", nullable = false, columnDefinition = "nvarchar(255)")
    private String detailAddress;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
