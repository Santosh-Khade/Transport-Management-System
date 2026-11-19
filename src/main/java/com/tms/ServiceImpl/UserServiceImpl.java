package com.tms.ServiceImpl;


import com.tms.DTO.UserCreateRequest;
import com.tms.DTO.UserResponse;
import com.tms.Entity.User;
import com.tms.Repository.UserRepository;
import com.tms.Service.SecurityConfig;
import com.tms.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SecurityConfig.PasswordHasher passwordHasher;

    @Override
    @Transactional
    public UserResponse createUser(UserCreateRequest request) {
        String normalizedUsername = request.username().trim();
        if (userRepository.existsByUsername(normalizedUsername)) {
            throw new IllegalArgumentException("Username already exists: " + normalizedUsername);
        }

        User user = User.builder()
                .username(normalizedUsername)
                .password(passwordHasher.hash(request.password()))
                .build();

        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        return toResponse(user);
    }

    @Override
    public Page<UserResponse> getAllUsers(String usernameSearch, Pageable pageable) {
        Specification<User> spec = (root, query, cb) -> {
            if (!StringUtils.hasText(usernameSearch)) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("username")), "%" + usernameSearch.trim().toLowerCase() + "%");
        };
        return userRepository.findAll(spec, pageable).map(this::toResponse);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getCreatedAt());
    }
}