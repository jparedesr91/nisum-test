package com.nisum.interviewtest.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ResponseDTO {
    @JsonProperty("mensaje")
    private String mensaje;
    @JsonProperty("data")
    private UserResponseDTO data;
}
