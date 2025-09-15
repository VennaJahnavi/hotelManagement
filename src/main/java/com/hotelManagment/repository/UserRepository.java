package com.hotelManagment.repository;

import com.hotelManagment.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<User> findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        List<User> users = jdbcTemplate.query(sql, new UserMapper(), username, password);
        if (users.isEmpty()) return Optional.empty();
        return Optional.of(users.get(0));
    }

    private static final class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setRole(User.Role.valueOf(rs.getString("role")));
            user.setCreatedAt(rs.getTimestamp("created_at"));
            return user;
        }
    }

    public Optional<User> findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, new UserMapper(), id);
        if (users.isEmpty()) return Optional.empty();
        return Optional.of(users.get(0));
    }

    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        List<User> users = jdbcTemplate.query(sql, new UserMapper(), username);
        if (users.isEmpty()) return Optional.empty();
        return Optional.of(users.get(0));
    }

    public int save(User user) {
        String sql = "INSERT INTO users(username, password, role) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getRole().name());
    }

    public List<User> findUsersByRole(String role) {
        String sql = """
                    SELECT id, username, created_at
                    FROM users
                    WHERE role = ?
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            User u = new User();
            u.setId(rs.getInt("id"));
            u.setUsername(rs.getString("username"));
            u.setRole(User.Role.valueOf(role));
            u.setCreatedAt(rs.getTimestamp("created_at"));
            return u;
        }, role);
    }
}
