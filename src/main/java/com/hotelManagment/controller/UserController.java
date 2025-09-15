package com.hotelManagment.controller;

import com.hotelManagment.model.User;
import com.hotelManagment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        boolean created = userService.createUser(user);
        if (created) return ResponseEntity.ok("User created successfully");
        return ResponseEntity.badRequest().body("User creation failed");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        Optional<User> user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(401).body(null));
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsersByRole(@RequestParam(required = false) String role) {
        if (role != null) {
            List<User> users = userService.findUsersByRole(role);
            return ResponseEntity.ok(users);
        }
        return ResponseEntity.badRequest().build(); // or return all users if needed
    }
}
