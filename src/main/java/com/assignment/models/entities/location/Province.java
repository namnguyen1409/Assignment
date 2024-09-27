package com.assignment.models.entities.location;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "provinces")
public class Province {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name",
            nullable = false,
            unique = true,
            columnDefinition = "nvarchar(50)"
    )
    private String name;

    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
    private Set<District> districts;

    public Province() {
    }

    public Province(long id, String name, Set<District> districts) {
        this.id = id;
        this.name = name;
        this.districts = districts;
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

    public Set<District> getDistricts() {
        return districts;
    }

    public void setDistricts(Set<District> districts) {
        this.districts = districts;
    }

}
