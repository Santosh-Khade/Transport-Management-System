package com.tms.Service;

import com.tms.DTO.UserCreateRequest;
import com.tms.DTO.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponse createUser(UserCreateRequest request);
    UserResponse getUserById(Long id);
    Page<UserResponse> getAllUsers(String usernameSearch, Pageable pageable);
    void deleteUser(Long id);
}
