package com.neoblacko.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="tariff", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Tariff {

    @Id
    @Column(name = "tariff_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tariffId;

    @Column(name = "tariff_name")
    private String tariffName;

    @Column(name = "tariff_limit_mb")
    private Integer tariffLimit;

}
