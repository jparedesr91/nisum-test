package com.nisum.interviewtest.user.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nisum.interviewtest.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDataDTO {
  @JsonProperty("name")
  @NotBlank(message = "Name is mandatory")
  private String name;

  @JsonProperty("email")
  @NotBlank(message = "Email is mandatory")
  @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Not a valid email")
  private String username;

  @NotBlank(message = "Password is mandatory")
  @Pattern(regexp = "^[A-Z][a-z]*[0-9][0-9]", message = "Not a valid password")
  @JsonProperty("password")
  private String password;

  @Valid
  @NotEmpty(message = "Phones are mandatory")
  @JsonProperty("phones")
  private List<PhoneDTO> phones;

  @Valid
  @NotEmpty(message = "Roles are mandatory")
  List<Role> roles;
}
