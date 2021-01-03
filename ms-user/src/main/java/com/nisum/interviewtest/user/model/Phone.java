package com.nisum.interviewtest.user.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "phone")
@Builder
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
