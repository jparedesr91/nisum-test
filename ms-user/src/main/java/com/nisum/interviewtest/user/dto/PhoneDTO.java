package com.nisum.interviewtest.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDTO {
    @NotBlank(message = "Password is mandatory")
    @JsonProperty("number")
    private String number;
    @NotBlank(message = "CityCode is mandatory")
    @JsonProperty("citycode")
    private String cityCode;
    @NotBlank(message = "CountryCode is mandatory")
    @JsonProperty("countrycode")
    private String countryCode;
}
