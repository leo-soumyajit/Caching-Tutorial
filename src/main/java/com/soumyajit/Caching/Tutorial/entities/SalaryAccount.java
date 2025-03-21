package com.soumyajit.Caching.Tutorial.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SalaryAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private BigDecimal balance;

//    @Version
//    private Long version;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Employee employee;
}
