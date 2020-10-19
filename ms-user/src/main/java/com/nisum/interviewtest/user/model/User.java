package com.nisum.interviewtest.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

  @Id
  @Column(columnDefinition = "BINARY(16)")
  private UUID id = UUID.randomUUID();

  @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
  @Column(name = "username", unique = true, nullable = false)
  private String username;

  @Column(name = "name", nullable = false)
  private String name;

  @Size(min = 8, message = "Minimum password length: 8 characters")
  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "active", nullable = false)
  private boolean active;

  @Column(name = "created_time", insertable=true, updatable=false)
  @ApiModelProperty(notes = "The database generated user, token and session mapping created time.")
  private LocalDateTime createdTime;

  @Column(name = "updated_time", insertable=true, updatable=true)
  @ApiModelProperty(notes = "The database generated user, token and session mapping updated time.")
  private LocalDateTime updatedTime;

  @Column(name = "login_time", insertable=true, updatable=true)
  @ApiModelProperty(notes = "The database generated user, token and session mapping updated time.")
  private LocalDateTime loginTime;

  @OneToMany(
          cascade = CascadeType.ALL,
          orphanRemoval = true,
          fetch = FetchType.LAZY
  )
  private List<Phone> phones;

  @ElementCollection(fetch = FetchType.EAGER)
  List<Role> roles;

  @PrePersist
  protected void onCreate() {
    createdTime = LocalDateTime.now();
    updatedTime = LocalDateTime.now();
    loginTime = LocalDateTime.now();
    active = true;
  }

  @PreUpdate
  protected void onUpdate() {
    updatedTime = LocalDateTime.now();
  }

}
