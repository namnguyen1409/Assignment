package com.assignment.models.entities.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "permissons")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "code",
            nullable = false,
            unique = true,
            columnDefinition = "varchar(50)"
    )
    private String code;

    @Column(name = "name",
            nullable = false,
            unique = true,
            columnDefinition = "nvarchar(50)"
    )
    private String name;

    @Column(name = "description",
            nullable = false,
            columnDefinition = "nvarchar(255)"
    )
    private String description;

    @Column(name = "image",
            nullable = false,
            columnDefinition = "nvarchar(255)"
    )
    private String image;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public Permission() {
    }

    public Permission(long id, String code, String name, String description, String image, Role role) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.image = image;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }



}
