package com.wyaaung.rbac.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record RoleUsersDto(
  @JsonProperty("role_name") String roleName,
  @JsonProperty("users") List<String> users
) {
}
