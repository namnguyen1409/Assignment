package com.assignment.models.entities.auth;


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

@EqualsAndHashCode
@Data
@AllArgsConstructor
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

    @Column(name = "self_register",
            nullable = false,
            columnDefinition = "bit"
    )
    private Boolean selfRegister;

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

    public Permission(String code, String name, String description, Boolean selfRegister, String image, Role role) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.selfRegister = selfRegister;
        this.image = image;
        this.role = role;
    }

}
