package com.wyaaung.rbac.repository;

import com.wyaaung.rbac.domain.Role;
import com.wyaaung.rbac.repository.mapper.RoleRowMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepository {
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private final RoleRowMapper roleRowMapper;

  public RoleRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, RoleRowMapper roleRowMapper) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    this.roleRowMapper = roleRowMapper;
  }

  public boolean roleExists(final String roleName) {
    final String sql = """
        SELECT 1
        WHERE EXISTS (
          SELECT 1
          FROM role
          WHERE name = :role_name
        )
      """;

    SqlParameterSource paramSource = new MapSqlParameterSource()
      .addValue("role_name", roleName);

    return !namedParameterJdbcTemplate.queryForList(sql, paramSource, Integer.class).isEmpty();
  }

  public List<Role> getRoles() {
    final String sql = """
      SELECT name, description, display_name
      FROM role
      """;

    return namedParameterJdbcTemplate.query(sql, resultSet -> {
      final List<Role> result = new ArrayList<>();

      while (resultSet.next()) {
        result.add(
          new Role(
            resultSet.getString("name"),
            resultSet.getString("description"),
            resultSet.getString("display_name")
          )
        );
      }
      return result;
    });
  }

  public Optional<Role> getRole(final String roleName) {
    final String sql = """
        SELECT name, description, display_name
        FROM role
        WHERE name = :role_name
      """;

    final SqlParameterSource parameters = new MapSqlParameterSource().addValue("role_name", roleName);

    try {
      return Optional.of(namedParameterJdbcTemplate.queryForObject(sql, parameters, roleRowMapper));
    } catch (EmptyResultDataAccessException ignored) {
      return Optional.empty();
    }
  }

  public void createRole(final Role role) {
    final String sql = """
        INSERT INTO role (name, description, display_name)
        VALUES
        (:name, :description, :display_name)
      """;

    final SqlParameterSource paramSource = new MapSqlParameterSource()
      .addValue("name", role.name())
      .addValue("description", role.description())
      .addValue("display_name", role.displayName());

    namedParameterJdbcTemplate.update(sql, paramSource);
  }

  public void deleteRole(final String roleName) {
    final String sql = """
        DELETE FROM role
        WHERE name = :role_name
      """;
    final SqlParameterSource paramSource = new MapSqlParameterSource()
      .addValue("role_name", roleName);

    namedParameterJdbcTemplate.update(sql, paramSource);
  }
}
