package com.shumkar.helpdesksystem.user.service;

import com.shumkar.helpdesksystem.user.dto.UserCreateRequest;
import com.shumkar.helpdesksystem.user.dto.UserUpdateRequest;
import com.shumkar.helpdesksystem.user.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {

	UserResponse createUser(UserCreateRequest request);

	UserResponse getUserById(UUID userId);

	UserResponse getUserByEmail(String email);

	UserResponse getCurrentUser();

	Page<UserResponse> getAllUsers(Pageable pageable);

	UserResponse updateUser(
			UUID userId,
			UserUpdateRequest request
	);

	UserResponse updateCurrentUser(UserUpdateRequest request);

	void changePassword(
			UUID userId,
			String currentPassword,
			String newPassword
	);

	void verifyEmail(UUID userId);

	void recordSuccessfulLogin(UUID userId);

	void deactivateUser(UUID userId);

	void reactivateUser(UUID userId);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);
}