package com.nisum.interviewtest.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "phone")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "number", unique = true, nullable = false)
    private String number;
    @Column(name = "city_code", nullable = false)
    private String cityCode;
    @Column(name = "country_code", nullable = false)
    private String countryCode;
}
