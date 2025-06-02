package com.user.service;

import com.user.domain.model.Role;
import com.user.domain.model.User;
import com.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    /**
     * Saves a user
     *
     * @return saved user
     */
    public User save(User user) {
        return repository.save(user);
    }

    /**
     * Creates a new user
     *
     * @return created user
     */
    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            // Replace with custom exceptions
            throw new RuntimeException("User with this username already exists");
        }

        if (repository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("User with this email already exists");
        }

        return save(user);
    }

    /**
     * Retrieves a user by username
     *
     * @return user
     */
    public User getByUsername(String username) {
        return repository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Retrieves a user by username
     * <p>
     * Required for Spring Security
     *
     * @return user details service
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Retrieves the currently authenticated user
     *
     * @return current user
     */
    public User getCurrentUser() {
        // Get username from Spring Security context
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    /**
     * Grants admin privileges to the current user
     * <p>
     * Used for demonstration purposes
     */
    @Deprecated
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }

    public User getById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new UsernameNotFoundException("User not found");
        }
        repository.deleteById(id);
    }

    public User update(Long id, User user) {
        if (!repository.existsById(id)) {
            throw new UsernameNotFoundException("User not found");
        }

        user.setId(id);
        return save(user);
    }

    public boolean isStudent(User user) {
        return user.getEmail().endsWith("edu.ua");

    }
}
