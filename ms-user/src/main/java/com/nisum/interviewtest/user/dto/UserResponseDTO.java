package com.nisum.interviewtest.user.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
  @JsonProperty("id")
  private Integer id;
  @JsonProperty("isactive")
  private boolean active;
  @JsonProperty("created")
  private LocalDateTime createdTime;
  @JsonProperty("modified")
  private LocalDateTime updatedTime;
  @JsonProperty("last_login")
  private LocalDateTime loginTime;
  @JsonProperty("token")
  private String token;
}
