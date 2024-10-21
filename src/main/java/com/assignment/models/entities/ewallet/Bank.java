package com.assignment.models.entities.ewallet;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bank")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "nvarchar(255)")
    private String name;

    @Column(name = "code", nullable = false, columnDefinition = "nvarchar(50)")
    private String code;

    @Column(name = "short_name", nullable = false, columnDefinition = "nvarchar(50)")
    private String shortName;

    @Column(name = "logo", nullable = false, columnDefinition = "nvarchar(255)")
    private String logo;

    @OneToMany(mappedBy = "bank", cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BankEwallet> bankEwallets = new ArrayList<>();
}
