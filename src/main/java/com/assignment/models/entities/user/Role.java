package com.assignment.models.entities.user;

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
@Table(name = "roles")
public class Role {
    // Khai báo các trường dữ liệu
    // Khai báo trường id là khóa chính
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    // Khai báo trường code: mã của role, dùng để truy cập trên thanh URL, không chứa khoảng trắng và ký tự đặc biệt -> varchar(50)
    @Column(name = "code",
            nullable = false,
            unique = true,
            columnDefinition = "varchar(50)"
    )
    private String code;

    // Khai báo trường name : tên của role -> nvarchar(50)
    @Column(name = "name",
            nullable = false,
            unique = true,
            columnDefinition = "nvarchar(50)"
    )
    private String name;

    // Khai báo trường description
    @Column(name = "description",
            nullable = false,
            columnDefinition = "nvarchar(255)"
    )
    private String description;

    // Khai báo trường image: đường dẫn đến hình ảnh mô tả của role -> nvarchar(255)
    @Column(name = "image",
            nullable = false,
            columnDefinition = "nvarchar(255)"
    )
    private String image;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<UserRole> userRoles;

    public Role() {
    }

    public Role(long id, String code, String name, String description, String image, Set<UserRole> userRoles) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.image = image;
        this.userRoles = userRoles;
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

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

}
